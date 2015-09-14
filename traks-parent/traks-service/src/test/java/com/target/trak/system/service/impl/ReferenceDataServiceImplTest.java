package com.target.trak.system.service.impl;

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
import org.springframework.dao.QueryTimeoutException;

import com.target.trak.system.entity.ReferenceData;
import com.target.trak.system.persistence.ReferenceDataDao;
import com.target.trak.system.service.dto.referencedata.ReferenceDataApiRequest;
import com.target.trak.system.service.dto.referencedata.ReferenceDataApiResponse;
import com.target.trak.system.service.dto.referencedata.ReferenceDataDto;
import com.target.trak.system.service.exception.TargetTrakException;
import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.service.validations.TargetTrakValidator;

public class ReferenceDataServiceImplTest {

	private Mockery mockery;
	private ReferenceDataServiceImpl referenceDataService;
	private ReferenceDataDao referenceDataDaoMock;
	private ConversionService conversionServiceMock;
	private TargetTrakValidator<ReferenceDataApiRequest> validatorMock;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		referenceDataService = new ReferenceDataServiceImpl();
		mockery = new JUnit4Mockery();
		referenceDataDaoMock = mockery.mock(ReferenceDataDao.class);
		conversionServiceMock = mockery.mock(ConversionService.class);
		validatorMock = mockery.mock(TargetTrakValidator.class);

		referenceDataService.setConversionService(conversionServiceMock);
		referenceDataService.setReferenceDataDao(referenceDataDaoMock);
		referenceDataService.setValidator(validatorMock);
	}

	@Test(expected = NullPointerException.class)
	public void createReferenceDataForNullRequest() {
		final ReferenceDataApiRequest request = null;
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new NullPointerException("API request is null")));
			}
		});

		referenceDataService.createReferenceData(request);
		Assert.fail("Reference Data Api Request is null, so test should of failed");
	}

	@Test(expected = TargetTrakException.class)
	public void createReferenceDataForNullReferenceDataDto() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new IllegalArgumentException("Reference Data is null")));
			}
		});

		referenceDataService.createReferenceData(request);
		Assert.fail("Reference Data Api Request is null, so test should of failed");
	}

	@Test
	public void createReferenceDataWithValidationErrors() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(buildValidationErrors()));
			}
		});

		ReferenceDataApiResponse response = referenceDataService.createReferenceData(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertTrue("Validation Errors are not empty", !response.getErrors().isEmpty());
	}

	@Test
	public void createReferenceDataSuccessful() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		request.setReferenceDataDto(buildReferenceDataDto("test", "test", "test", "test"));

		final ReferenceData entity = new ReferenceData();

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getReferenceDataDto())), with(ReferenceData.class));
				will(returnValue(entity));

				oneOf(referenceDataDaoMock).insertReferenceData(with(equal(entity)));
				will(returnValue(entity));

				oneOf(conversionServiceMock).convert(with(equal(entity)), with(ReferenceDataDto.class));
				will(returnValue(request.getReferenceDataDto()));
			}
		});

		ReferenceDataApiResponse response = referenceDataService.createReferenceData(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertNull("Validation Errors are null", response.getErrors());
	}

	@Test(expected = NullPointerException.class)
	public void deleteReferenceDataForNullRequest() {
		final ReferenceDataApiRequest request = null;
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new NullPointerException("API request is null")));
			}
		});

		referenceDataService.deleteReferenceData(request);
		Assert.fail("Reference Data Api Request is null, so test should of failed");
	}

	@Test(expected = TargetTrakException.class)
	public void deleteReferenceDataForNullReferenceDataDto() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new IllegalArgumentException("Reference Data is null")));
			}
		});

		referenceDataService.deleteReferenceData(request);
		Assert.fail("Reference Data Api Request is null, so test should of failed");
	}

	@Test
	public void deleteReferenceDataWithValidationErrors() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(buildValidationErrors()));
			}
		});

		ReferenceDataApiResponse response = referenceDataService.deleteReferenceData(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertTrue("Validation Errors are not empty", !response.getErrors().isEmpty());
	}

	@Test
	public void deleteReferenceDataSuccessful() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		request.setReferenceDataDto(buildReferenceDataDto("test", "test", "test", "test"));

		final ReferenceData entity = new ReferenceData();

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getReferenceDataDto())), with(ReferenceData.class));
				will(returnValue(entity));

				oneOf(referenceDataDaoMock).deleteReferenceData(with(equal(entity)));
			}
		});

		ReferenceDataApiResponse response = referenceDataService.deleteReferenceData(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertNull("Validation Errors are null", response.getErrors());
	}

	@Test(expected = TargetTrakException.class)
	public void getReferenceDataTypesEmptyResultSet() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(referenceDataDaoMock).getReferenceDataTypes();
				will(throwException(new EmptyResultDataAccessException(1)));
			}
		});

		referenceDataService.getReferenceDataTypes(new ReferenceDataApiRequest());
		Assert.fail("Empty Result Set thrown, so test should fail!");
	}

	@Test
	public void getReferenceDataTypesSuccessful() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(referenceDataDaoMock).getReferenceDataTypes();
				will(returnValue(buildReferenceDataList()));

				atLeast(1).of(conversionServiceMock).convert(with(any(ReferenceData.class)), with(ReferenceDataDto.class));
				will(returnValue(buildReferenceDataDto("test", "test", "test", "test")));
			}
		});

		ReferenceDataApiResponse response = referenceDataService.getReferenceDataTypes(new ReferenceDataApiRequest());
		Assert.assertNotNull("Reference Data Api Response is not null", response);
	}

//	@Test(expected = TargetTrakException.class)
//	public void getReferenceDataByCriteriaForException() {
//		mockery.checking(new org.jmock.Expectations() {
//			{
//				oneOf(conversionServiceMock).convert(with(any(ReferenceDataSearchCriteriaDto.class)), with(ReferenceDataSearchCriteria.class));
//				will(returnValue(new ReferenceDataSearchCriteria()));
//
//				oneOf(referenceDataDaoMock).selectReferenceDataBySearchCriteriaCount(with(any(ReferenceDataSearchCriteria.class)));
//				will(throwException(new EmptyResultDataAccessException(1)));
//			}
//		});
//
//		referenceDataService.getReferenceDataByCriteria(new ReferenceDataApiRequest());
//		Assert.fail("Empty Result Set thrown, so test should fail!");
//	}

//	@Test
//	public void getReferenceDataByCriteriaForEmptyResult() {
//		mockery.checking(new org.jmock.Expectations() {
//			{
//				oneOf(conversionServiceMock).convert(with(any(ReferenceDataSearchCriteriaDto.class)), with(ReferenceDataSearchCriteria.class));
//				will(returnValue(new ReferenceDataSearchCriteria()));
//
//				oneOf(referenceDataDaoMock).selectReferenceDataBySearchCriteriaCount(with(any(ReferenceDataSearchCriteria.class)));
//				will(returnValue(0));
//			}
//		});
//
//		ReferenceDataApiResponse response = referenceDataService.getReferenceDataByCriteria(new ReferenceDataApiRequest());
//		Assert.assertNotNull("Reference Data Api Response is not null", response);
//		Assert.assertTrue("Total size is 0", (0 == response.getTotalSize()));
//		Assert.assertFalse(!response.isSuccess());
//	}
//
//	@Test
//	public void getReferenceDataByCriteriaForSuccess() {
//		mockery.checking(new org.jmock.Expectations() {
//			{
//				oneOf(conversionServiceMock).convert(with(any(ReferenceDataSearchCriteriaDto.class)), with(ReferenceDataSearchCriteria.class));
//				will(returnValue(new ReferenceDataSearchCriteria()));
//
//				oneOf(referenceDataDaoMock).selectReferenceDataBySearchCriteriaCount(with(any(ReferenceDataSearchCriteria.class)));
//				will(returnValue(10));
//
//				oneOf(referenceDataDaoMock).selectPaginatedReferenceDataBySearchCriteria(with(any(ReferenceDataSearchCriteria.class)));
//				will(returnValue(buildReferenceDataList()));
//
//				atLeast(1).of(conversionServiceMock).convert(with(any(ReferenceData.class)), with(ReferenceDataDto.class));
//				will(returnValue(buildReferenceDataDto("test", "test", "test", "test")));
//			}
//		});
//
//		ReferenceDataApiResponse response = referenceDataService.getReferenceDataByCriteria(new ReferenceDataApiRequest());
//		Assert.assertNotNull("Reference Data Api Response is not null", response);
//		Assert.assertTrue("Total size is 10", (10 == response.getTotalSize()));
//	}

	@Test(expected = TargetTrakException.class)
	public void getReferenceDataByIdForEmptyResultSet() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(referenceDataDaoMock).selectReferenceDataById(with(any(Long.class)));
				will(throwException(new EmptyResultDataAccessException(1)));
			}
		});

		referenceDataService.getReferenceDataItemById(new ReferenceDataApiRequest());
		Assert.fail("Empty Result Set thrown, so test should fail!");
	}

	@Test
	public void getReferenceDataByIdForSuccess() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setId(1020L);
		request.setReferenceDataDto(dto);

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(referenceDataDaoMock).selectReferenceDataById(with(any(Long.class)));
				will(returnValue(buildReferenceData()));

				oneOf(conversionServiceMock).convert(with(equal(buildReferenceData())), with(ReferenceDataDto.class));
				will(returnValue(buildReferenceDataDto("test", "test", "test", "test")));
			}
		});

		ReferenceDataApiResponse response = referenceDataService.getReferenceDataItemById(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertTrue("Reference Data Api Response is successful", response.isSuccess());
	}

	@Test(expected = TargetTrakException.class)
	public void getReferenceDataByTypeForDataAccessException() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setType("example");
		request.setReferenceDataDto(dto);

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(referenceDataDaoMock).selectReferenceDataByType(with(equal(request.getReferenceDataDto().getType())));
				will(throwException(new EmptyResultDataAccessException(1)));
			}
		});
		referenceDataService.getReferenceDataByType(request);
		Assert.fail("Test should fail");
	}

	@Test
	public void getReferenceDataByTypeSuccessful() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setType("example");
		request.setReferenceDataDto(dto);

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(referenceDataDaoMock).selectReferenceDataByType(with(equal(request.getReferenceDataDto().getType())));
				will(returnValue(buildReferenceDataList()));
				
				atLeast(1).of(conversionServiceMock).convert(with(any(ReferenceData.class)), with(ReferenceDataDto.class));
				will(returnValue(buildReferenceDataDto("test", "test", "test", "test")));
			}
		});
		ReferenceDataApiResponse response = referenceDataService.getReferenceDataByType(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertTrue("Reference Data Api Response is successful", response.isSuccess());
	}
	
	@Test
	public void updateReferenceDataItemForValidationErrors() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setValue("example");
		request.setReferenceDataDto(dto);
		
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(buildValidationErrors()));
			}
		});
		ReferenceDataApiResponse response = referenceDataService.updateReferenceDataItem(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertFalse("Reference Data Api Response is successful", response.isSuccess());
	}
	
	@Test
	public void updateReferenceDataItemForDataAccessException() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setValue("example");
		request.setReferenceDataDto(dto);
		
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));
				
				oneOf(conversionServiceMock).convert(with(equal(request.getReferenceDataDto())), with(ReferenceData.class));
				will(returnValue(buildReferenceData()));
				
				oneOf(referenceDataDaoMock).updateReferenceData(with(any(ReferenceData.class)));
				will(throwException(new QueryTimeoutException("Query timed out")));
			}
		});
		ReferenceDataApiResponse response = referenceDataService.updateReferenceDataItem(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertFalse("Reference Data Api Response is successful", response.isSuccess());
	}
	
	@Test
	public void updateReferenceDataItemSuccessful() {
		final ReferenceDataApiRequest request = new ReferenceDataApiRequest();
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setValue("example");
		request.setReferenceDataDto(dto);
		
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));
				
				oneOf(conversionServiceMock).convert(with(equal(request.getReferenceDataDto())), with(ReferenceData.class));
				will(returnValue(buildReferenceData()));
				
				oneOf(referenceDataDaoMock).updateReferenceData(with(any(ReferenceData.class)));
				will(returnValue(buildReferenceData()));
				
				oneOf(conversionServiceMock).convert(with(any(ReferenceData.class)), with(ReferenceDataDto.class));
				will(returnValue(buildReferenceDataDto("test", "test", "test", "test")));
			}
		});
		ReferenceDataApiResponse response = referenceDataService.updateReferenceDataItem(request);
		Assert.assertNotNull("Reference Data Api Response is not null", response);
		Assert.assertTrue("Reference Data Api Response is successful", response.isSuccess());
	}

	private List<TargetTrakValidationError> buildValidationErrors() {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		errors.add(new TargetTrakValidationError("label", "LABEL_ERROR_001"));
		return errors;
	}

	private List<ReferenceData> buildReferenceDataList() {
		return Collections.singletonList(buildReferenceData());
	}

	private ReferenceData buildReferenceData() {
		ReferenceData data = new ReferenceData();
		data.setId(1L);
		return data;
	}

	private ReferenceDataDto buildReferenceDataDto(String type, String label, String value, String createdBy) {
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setType(type);
		dto.setLabel(label);
		dto.setValue(value);
		dto.setCreatedBy("user123");
		dto.setCreatedDateTime(null);
		return dto;

	}
}