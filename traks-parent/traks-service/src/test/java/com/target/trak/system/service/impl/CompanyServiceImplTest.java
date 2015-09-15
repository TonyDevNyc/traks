package com.target.trak.system.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;

import com.target.trak.system.dto.common.TargetTrakValidationError;
import com.target.trak.system.dto.company.CompanyApiRequest;
import com.target.trak.system.dto.company.CompanyApiResponse;
import com.target.trak.system.dto.company.CompanyDto;
import com.target.trak.system.dto.company.CompanySearchCriteriaDto;
import com.target.trak.system.entity.Company;
import com.target.trak.system.entity.criteria.CompanySearchCriteria;
import com.target.trak.system.persistence.CompanyDao;
import com.target.trak.system.service.exception.TargetTrakException;
import com.target.trak.system.validations.TargetTrakValidator;

public class CompanyServiceImplTest {

	private Mockery mockery;
	private CompanyServiceImpl companyService;
	private CompanyDao companyDaoMock;
	private ConversionService conversionServiceMock;
	private TargetTrakValidator<CompanyApiRequest> validatorMock;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		mockery = new JUnit4Mockery();
		companyService = new CompanyServiceImpl();
		companyDaoMock = mockery.mock(CompanyDao.class);
		conversionServiceMock = mockery.mock(ConversionService.class);
		validatorMock = mockery.mock(TargetTrakValidator.class);

		companyService.setCompanyDao(companyDaoMock);
		companyService.setConversionService(conversionServiceMock);
		companyService.setValidator(validatorMock);
	}

	@Test(expected = NullPointerException.class)
	public void createCompanyForNullRequest() {
		final CompanyApiRequest request = null;
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new NullPointerException("API request is null")));
			}
		});
		companyService.createCompany(request);
		Assert.fail("Company Api Request is null, so test should of failed");
	}

	@Test(expected = TargetTrakException.class)
	public void createCompanyForNullCompanyDto() {
		final CompanyApiRequest request = new CompanyApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new IllegalArgumentException("Company is null")));
			}
		});
		companyService.createCompany(request);
		Assert.fail("Company Api Request is null, so test should of failed");
	}

	@Test
	public void createCompanyForValidationErrors() {
		final CompanyApiRequest request = new CompanyApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(buildValidationErrors()));
			}
		});
		CompanyApiResponse response = companyService.createCompany(request);
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Validation Errors are not empty", !response.getErrors().isEmpty());
	}

	@Test
	public void createCompanySuccessful() {
		final CompanyApiRequest request = new CompanyApiRequest();
		request.setCompany(buildCompanyDto());

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getCompany())), with(Company.class));
				will(returnValue(buildCompany()));

				oneOf(companyDaoMock).insertCompany(with(any(Company.class)));
				will(returnValue(buildCompany()));

				oneOf(conversionServiceMock).convert(with(any(Company.class)), with(CompanyDto.class));
				will(returnValue(buildCompanyDto()));
			}
		});
		CompanyApiResponse response = companyService.createCompany(request);
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertNull("Validation Errors is null", response.getErrors());
		Assert.assertNotNull("Company Dto is not null", response.getCompany());
	}

	@Test(expected = TargetTrakException.class)
	public void getCompaniesByCriteriaForException() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(conversionServiceMock).convert(with(any(CompanySearchCriteriaDto.class)), with(CompanySearchCriteria.class));
				will(returnValue(new CompanySearchCriteria()));

				oneOf(companyDaoMock).selectPaginatedCompaniesByCriteriaCount(with(any(CompanySearchCriteria.class)));
				will(throwException(new EmptyResultDataAccessException(1)));
			}
		});
		companyService.getCompaniesByCriteria(new CompanyApiRequest());
		Assert.fail("Empty Result Set thrown, so test should fail!");
	}

	@Test
	public void getCompaniesByCriteriaForEmptyResult() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(conversionServiceMock).convert(with(any(CompanySearchCriteriaDto.class)), with(CompanySearchCriteria.class));
				will(returnValue(new CompanySearchCriteria()));

				oneOf(companyDaoMock).selectPaginatedCompaniesByCriteriaCount(with(any(CompanySearchCriteria.class)));
				will(returnValue(0));
			}
		});
		CompanyApiResponse response = companyService.getCompaniesByCriteria(new CompanyApiRequest());
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Total size is 0", (0 == response.getTotalSize()));
		Assert.assertFalse(!response.isSuccess());
	}

	@Test
	public void getCompaniesByCriteriaForSuccess() {
		final CompanyApiRequest request = new CompanyApiRequest();
		CompanySearchCriteriaDto companySearchCriteria = new CompanySearchCriteriaDto();
		companySearchCriteria.setText("Wilson");
		request.setCompanySearchCriteria(companySearchCriteria);

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(conversionServiceMock).convert(with(equal(request.getCompanySearchCriteria())), with(CompanySearchCriteria.class));
				will(returnValue(new CompanySearchCriteria()));

				oneOf(companyDaoMock).selectPaginatedCompaniesByCriteriaCount(with(any(CompanySearchCriteria.class)));
				will(returnValue(1020));

				oneOf(companyDaoMock).selectPaginatedCompaniesByCriteria(with(any(CompanySearchCriteria.class)));
				will(returnValue(buildCompaniesList()));

				atLeast(1).of(conversionServiceMock).convert(with(equal(buildCompany())), with(CompanyDto.class));
				will(returnValue(buildCompanyDto()));
			}
		});
		CompanyApiResponse response = companyService.getCompaniesByCriteria(request);
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Total size is 1020", (1020 == response.getTotalSize()));
	}

	@Test(expected = NullPointerException.class)
	public void updateCompanyForNullRequest() {
		final CompanyApiRequest request = null;
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new NullPointerException("API request is null")));
			}
		});
		companyService.createCompany(request);
		Assert.fail("Company Api Request is null, so test should of failed");
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateCompanyForNullCompanyDto() {
		final CompanyApiRequest request = new CompanyApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new IllegalArgumentException("Company Data is null")));
			}
		});
		companyService.updateCompany(request);
		Assert.fail("Company Api Request is null, so test should of failed");
	}

	@Test
	public void updateCompanyForValidationErrors() {
		final CompanyApiRequest request = new CompanyApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(buildValidationErrors()));
			}
		});
		CompanyApiResponse response = companyService.updateCompany(request);
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Validation Errors are not empty", !response.getErrors().isEmpty());
	}

	@Test
	public void updateCompanyForDataAccessException() {
		final CompanyApiRequest request = new CompanyApiRequest();
		request.setCompany(buildCompanyDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getCompany())), with(Company.class));
				will(returnValue(new Company()));

				oneOf(companyDaoMock).updateCompany(with(any(Company.class)));
				will(throwException(new BadSqlGrammarException("updat", "update..", new SQLException())));
			}
		});
		CompanyApiResponse response = companyService.updateCompany(request);
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertFalse("Company Api Response is unsuccessful", response.isSuccess());
	}

	@Test
	public void updateCompanySuccessful() {
		final CompanyApiRequest request = new CompanyApiRequest();
		request.setCompany(buildCompanyDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getCompany())), with(Company.class));
				will(returnValue(new Company()));

				oneOf(companyDaoMock).updateCompany(with(any(Company.class)));
				will(returnValue(buildCompany()));

				oneOf(conversionServiceMock).convert(with(equal(buildCompany())), with(CompanyDto.class));
				will(returnValue(buildCompanyDto()));
			}
		});
		CompanyApiResponse response = companyService.updateCompany(request);
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Company Api Response is successful", response.isSuccess());
	}

	@Test(expected = TargetTrakException.class)
	public void getCompanyNamesForDataAccessException() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(companyDaoMock).selectAllCompanyNames();
				will(throwException(new BadSqlGrammarException("select", "select..", new SQLException())));
			}
		});
		companyService.getCompanyNames();
	}
	
	@Test
	public void getCompanyNamesForEmptyList() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(companyDaoMock).selectAllCompanyNames();
				will(returnValue(new ArrayList<Company>()));
			}
		});
		CompanyApiResponse response = companyService.getCompanyNames();
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Company Api Response is successful", response.isSuccess());
		Assert.assertTrue("Size of data return is 0", (0 == response.getCompanies().size()));
	}
	
	@Test
	public void getCompanyNamesForNullList() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(companyDaoMock).selectAllCompanyNames();
				will(returnValue(null));
			}
		});
		CompanyApiResponse response = companyService.getCompanyNames();
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Company Api Response is successful", response.isSuccess());
		Assert.assertTrue("Size of data return is 0", (0 == response.getCompanies().size()));
	}

	@Test
	public void getCompanyNamesSuccessful() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(companyDaoMock).selectAllCompanyNames();
				will(returnValue(buildCompaniesList()));
				
				atLeast(1).of(conversionServiceMock).convert(with(equal(buildCompany())), with(CompanyDto.class));
				will(returnValue(buildCompanyDto()));
			}
		});
		CompanyApiResponse response = companyService.getCompanyNames();
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Company Api Response is successful", response.isSuccess());
		Assert.assertTrue("Size of data return is 1", (1 == response.getCompanies().size()));
	}
	
	@Test(expected = TargetTrakException.class)
	public void getCompanyByIdForDataAccessException() {
		final CompanyApiRequest request = new CompanyApiRequest();
		request.setCompany(buildCompanyDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(companyDaoMock).selectCompanyById(with(any(Long.class)));
				will(throwException(new BadSqlGrammarException("select", "select..", new SQLException())));
			}
		});
		companyService.getCompanyById(request);
		Assert.fail("Unit test should of failed due to DataAccessException");
	}
	
	@Test
	public void getCompanyByIdSuccessful() {
		final CompanyApiRequest request = new CompanyApiRequest();
		request.setCompany(buildCompanyDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(companyDaoMock).selectCompanyById(with(any(Long.class)));
				will(returnValue(buildCompany()));
				
				oneOf(conversionServiceMock).convert(with(equal(buildCompany())), with(CompanyDto.class));
				will(returnValue(buildCompanyDto()));
			}
		});
		CompanyApiResponse response = companyService.getCompanyById(request);
		Assert.assertNotNull("Company Api Response is not null", response);
		Assert.assertTrue("Company Api Response is successful", response.isSuccess());
	}
	
	private List<Company> buildCompaniesList() {
		return Collections.singletonList(buildCompany());
	}

	private CompanyDto buildCompanyDto() {
		CompanyDto dto = new CompanyDto();
		dto.setId(1L);
		dto.setName("Wilson & Thompson LLP");
		return dto;
	}

	private Company buildCompany() {
		Company entity = new Company();
		entity.setId(1L);
		entity.setName("Wilson & Thompson LLP");
		return entity;
	}

	private List<TargetTrakValidationError> buildValidationErrors() {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		errors.add(new TargetTrakValidationError("label", "LABEL_ERROR_001"));
		return errors;
	}
}
