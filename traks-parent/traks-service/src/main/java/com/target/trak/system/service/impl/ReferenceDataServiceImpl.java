package com.target.trak.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.target.trak.system.entity.ReferenceData;
import com.target.trak.system.entity.criteria.TextSearchCriteria;
import com.target.trak.system.persistence.ReferenceDataDao;
import com.target.trak.system.service.BaseTargetTrakService;
import com.target.trak.system.service.ReferenceDataService;
import com.target.trak.system.service.dto.common.TargetTrakErrorTypeEnum;
import com.target.trak.system.service.dto.common.TargetTrakRequestTypeEnum;
import com.target.trak.system.service.dto.common.TextSearchCriteriaDto;
import com.target.trak.system.service.dto.referencedata.ReferenceDataApiRequest;
import com.target.trak.system.service.dto.referencedata.ReferenceDataApiResponse;
import com.target.trak.system.service.dto.referencedata.ReferenceDataDto;
import com.target.trak.system.service.exception.TargetTrakException;
import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.service.validations.TargetTrakValidator;

@Transactional(value = "dwTransactionManager", propagation = Propagation.NEVER)
public class ReferenceDataServiceImpl extends BaseTargetTrakService implements ReferenceDataService {

	private static Logger logger = Logger.getLogger(ReferenceDataServiceImpl.class);

	private ReferenceDataDao referenceDataDao;

	private ConversionService conversionService;

	private TargetTrakValidator<ReferenceDataApiRequest> validator;

	@Transactional(value = "dwTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public ReferenceDataApiResponse createReferenceData(final ReferenceDataApiRequest request) {
		ReferenceDataApiResponse response = new ReferenceDataApiResponse();
		request.setRequestType(TargetTrakRequestTypeEnum.CREATE);
		List<TargetTrakValidationError> validationErrors = validator.validate(request);

		if (!validationErrors.isEmpty()) {
			throw generateServiceException(response, validationErrors, TargetTrakErrorTypeEnum.VALIDATION, "A validation error has occurred. Please fix the errors below");
		}
		
		try {
			ReferenceData domain = referenceDataDao.insertReferenceData(conversionService.convert(request.getReferenceDataDto(), ReferenceData.class));
			response.setReferenceData(conversionService.convert(domain, ReferenceDataDto.class));
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			TargetTrakException exception = generateServiceException(response, validationErrors, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to create Reference Data. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Transactional(value = "dwTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public ReferenceDataApiResponse deleteReferenceData(final ReferenceDataApiRequest request) {
		ReferenceDataApiResponse response = new ReferenceDataApiResponse();
		request.setRequestType(TargetTrakRequestTypeEnum.DELETE);
		List<TargetTrakValidationError> validationErrors = validator.validate(request);

		if (!validationErrors.isEmpty()) {
			response.setSuccess(Boolean.FALSE);
			response.setErrorType(TargetTrakErrorTypeEnum.VALIDATION);
			response.setErrors(validationErrors);
			response.setMessage("A validation error has occurred. Please fix the errors below");
			return response;
		}
		
		try {
			ReferenceDataDto requestDto = request.getReferenceDataDto();
			referenceDataDao.deleteReferenceData(conversionService.convert(requestDto, ReferenceData.class));
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			TargetTrakException exception = generateServiceException(response, validationErrors, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to delete Reference Data. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Override
	public ReferenceDataApiResponse getReferenceDataTypes(final ReferenceDataApiRequest request) {
		ReferenceDataApiResponse response = new ReferenceDataApiResponse();
		try {
			List<ReferenceData> data = referenceDataDao.getReferenceDataTypes();
			List<ReferenceDataDto> dtos = convertListOfDomains(data);
			response.setReferenceDataList(dtos);
			response.setSuccess(Boolean.TRUE);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			TargetTrakException exception = generateServiceException(response, null, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to process your request. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Override
	public ReferenceDataApiResponse getReferenceDataByCriteria(final ReferenceDataApiRequest request) {
		ReferenceDataApiResponse response = new ReferenceDataApiResponse();
		TextSearchCriteria criteria = buildTextSearchCriteria(request.getTextSearchCriteria());

		try {
			int totalSize = referenceDataDao.selectReferenceDataBySearchCriteriaCount(criteria);
			if (totalSize > 0) {
				List<ReferenceData> data = referenceDataDao.selectPaginatedReferenceDataBySearchCriteria(criteria);
				List<ReferenceDataDto> dtos = convertListOfDomains(data);
				response.setReferenceDataList(dtos);
			} else {
				response.setReferenceDataList(new ArrayList<ReferenceDataDto>());
			}
			response.setTotalSize(totalSize);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			TargetTrakException exception = generateServiceException(response, null, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to search Reference Data. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Override
	public ReferenceDataApiResponse getReferenceDataItemById(final ReferenceDataApiRequest request) {
		ReferenceDataApiResponse response = new ReferenceDataApiResponse();
		try {
			ReferenceData entity = referenceDataDao.selectReferenceDataById(request.getReferenceDataDto().getId());
			response.setReferenceData(conversionService.convert(entity, ReferenceDataDto.class));
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			TargetTrakException exception = generateServiceException(response, null, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to search Reference Data. <br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Override
	public ReferenceDataApiResponse getReferenceDataByType(final ReferenceDataApiRequest request) {
		ReferenceDataApiResponse response = new ReferenceDataApiResponse();
		String type = request.getReferenceDataDto().getType();
		try {
			List<ReferenceData> data = referenceDataDao.selectReferenceDataByType(type);
			List<ReferenceDataDto> dtos = convertListOfDomains(data);
			response.setReferenceDataList(dtos);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception t) {
			logger.error(t.getMessage(), t);
			TargetTrakException exception = generateServiceException(response, null, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying retrieve reference data type: " + type + ".<br /> If the error still occurs, contact your administrator");
			throw exception;
		}
		return response;
	}

	@Transactional(value = "dwTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public ReferenceDataApiResponse updateReferenceDataItem(final ReferenceDataApiRequest request) throws TargetTrakException {
		ReferenceDataApiResponse response = new ReferenceDataApiResponse();
		request.setRequestType(TargetTrakRequestTypeEnum.UPDATE);
		List<TargetTrakValidationError> validationErrors = validator.validate(request);

		if (!validationErrors.isEmpty()) {
			throw generateServiceException(response, validationErrors, TargetTrakErrorTypeEnum.VALIDATION, "A validation error has occurred. Please fix the errors below");
		}
		
		try {
			ReferenceData convertedDomain = conversionService.convert(request.getReferenceDataDto(), ReferenceData.class);
			ReferenceData domain = referenceDataDao.updateReferenceData(convertedDomain);
			ReferenceDataDto dto = conversionService.convert(domain, ReferenceDataDto.class);
			response.setReferenceData(dto);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			generateServiceException(response, validationErrors, TargetTrakErrorTypeEnum.ERROR, "An error has occurred trying to update Reference Data. <br /> If the error still occurs, contact your administrator");
		}
		return response;
	}

	private List<ReferenceDataDto> convertListOfDomains(final List<ReferenceData> domains) {
		List<ReferenceDataDto> dtos = new ArrayList<ReferenceDataDto>();
		for (ReferenceData domain : domains) {
			dtos.add(conversionService.convert(domain, ReferenceDataDto.class));
		}
		return dtos;
	}

	private TextSearchCriteria buildTextSearchCriteria(TextSearchCriteriaDto criteria) {
		TextSearchCriteria searchCriteria = new TextSearchCriteria();
		searchCriteria.setText(criteria.getText());
		searchCriteria.setPage(criteria.getPage());
		searchCriteria.setStart(criteria.getStart());
		searchCriteria.setEnd(criteria.getEnd());
		searchCriteria.setSortDirection(criteria.getSortDirection());
		searchCriteria.setSortField(criteria.getSortField());
		return searchCriteria;
	}

	public void setReferenceDataDao(ReferenceDataDao referenceDataDao) {
		this.referenceDataDao = referenceDataDao;
	}

	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	public void setValidator(TargetTrakValidator<ReferenceDataApiRequest> validator) {
		this.validator = validator;
	}
}