package com.target.trak.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.target.trak.system.entity.Company;
import com.target.trak.system.entity.criteria.CompanySearchCriteria;
import com.target.trak.system.persistence.CompanyDao;
import com.target.trak.system.service.BaseTargetTrakService;
import com.target.trak.system.service.CompanyService;
import com.target.trak.system.service.dto.common.TargetTrakErrorTypeEnum;
import com.target.trak.system.service.dto.common.TargetTrakRequestTypeEnum;
import com.target.trak.system.service.dto.company.CompanyApiRequest;
import com.target.trak.system.service.dto.company.CompanyApiResponse;
import com.target.trak.system.service.dto.company.CompanyDto;
import com.target.trak.system.service.exception.TargetTrakException;
import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.service.validations.TargetTrakValidator;

@Transactional(value = "dwTransactionManager", propagation = Propagation.NEVER)
public class CompanyServiceImpl extends BaseTargetTrakService implements CompanyService {

	private static Logger logger = Logger.getLogger(CompanyServiceImpl.class);

	private CompanyDao companyDao;
	private ConversionService conversionService;
	private TargetTrakValidator<CompanyApiRequest> validator;

	@Transactional(value = "dwTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public CompanyApiResponse createCompany(final CompanyApiRequest request) {
		CompanyApiResponse response = new CompanyApiResponse();
		request.setRequestType(TargetTrakRequestTypeEnum.CREATE);
		List<TargetTrakValidationError> validationErrors = null;

		try {
			validationErrors = validator.validate(request);

			if (!validationErrors.isEmpty()) {
				response.setSuccess(Boolean.FALSE);
				response.setErrorType(TargetTrakErrorTypeEnum.VALIDATION);
				response.setErrors(validationErrors);
				response.setMessage("A validation error has occurred. Please fix the errors below");
				return response;
			}

			Company entity = companyDao.insertCompany(conversionService.convert(request.getCompany(), Company.class));
			response.setCompany(conversionService.convert(entity, CompanyDto.class));
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			TargetTrakException exception = generateServiceException(response, validationErrors, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to create a company. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Override
	public CompanyApiResponse getCompaniesByCriteria(final CompanyApiRequest request) {
		CompanyApiResponse response = new CompanyApiResponse();
		CompanySearchCriteria criteria = conversionService.convert(request.getCompanySearchCriteria(), CompanySearchCriteria.class);

		try {

			int totalSize = companyDao.selectPaginatedCompaniesByCriteriaCount(criteria);
			if (totalSize > 0) {
				List<Company> data = companyDao.selectPaginatedCompaniesByCriteria(criteria);
				List<CompanyDto> dtos = convertEntityList(data);
				response.setCompanies(dtos);
			} else {
				response.setCompanies(new ArrayList<CompanyDto>());
			}
			response.setTotalSize(totalSize);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			TargetTrakException exception = generateServiceException(response, null, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to search Companies. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Transactional(value = "dwTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public CompanyApiResponse updateCompany(final CompanyApiRequest request) {
		CompanyApiResponse response = new CompanyApiResponse();
		request.setRequestType(TargetTrakRequestTypeEnum.UPDATE);
		List<TargetTrakValidationError> validationErrors = validator.validate(request);

		if (!validationErrors.isEmpty()) {
			response.setSuccess(Boolean.FALSE);
			response.setErrorType(TargetTrakErrorTypeEnum.VALIDATION);
			response.setErrors(validationErrors);
			response.setMessage("A validation error has occurred. Please fix the errors below");
			return response;
		}

		try {
			Company convertedDomain = conversionService.convert(request.getCompany(), Company.class);
			Company domain = companyDao.updateCompany(convertedDomain);
			CompanyDto dto = conversionService.convert(domain, CompanyDto.class);
			response.setCompany(dto);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setSuccess(Boolean.FALSE);
			response.setErrorType(TargetTrakErrorTypeEnum.ERROR);
			response.setMessage("An error has occurred trying to update a Company. <br /> If the error still occurs, contact your administrator");
		}
		return response;
	}

	@Override
	public CompanyApiResponse getCompanyNames() {
		CompanyApiResponse response = new CompanyApiResponse();
		try {
			List<Company> data = companyDao.selectAllCompanyNames();
			List<CompanyDto> companies = convertEntityList(data);
			response.setCompanies(companies);
			response.setSuccess(Boolean.TRUE);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			TargetTrakException exception = generateServiceException(response, null, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to search companies. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Override
	public CompanyApiResponse getCompanyById(final CompanyApiRequest request) {
		CompanyApiResponse response = new CompanyApiResponse();
		try {
			Company entity = companyDao.selectCompanyById(request.getCompany().getId());
			response.setCompany(conversionService.convert(entity, CompanyDto.class));
			response.setSuccess(Boolean.TRUE);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			TargetTrakException exception = generateServiceException(response, null, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to search companies. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	private List<CompanyDto> convertEntityList(final List<Company> companies) {
		List<CompanyDto> dtos = new ArrayList<CompanyDto>();
		if (companies == null || companies.isEmpty()) {
			return dtos;
		}

		for (Company company : companies) {
			dtos.add(conversionService.convert(company, CompanyDto.class));
		}
		return dtos;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	public void setValidator(TargetTrakValidator<CompanyApiRequest> validator) {
		this.validator = validator;
	}
}