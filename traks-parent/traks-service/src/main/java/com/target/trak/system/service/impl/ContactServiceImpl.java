package com.target.trak.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.target.trak.system.dto.common.TargetTrakErrorTypeEnum;
import com.target.trak.system.dto.common.TargetTrakRequestTypeEnum;
import com.target.trak.system.dto.common.TargetTrakValidationError;
import com.target.trak.system.dto.contact.ContactApiRequest;
import com.target.trak.system.dto.contact.ContactApiResponse;
import com.target.trak.system.dto.contact.ContactDto;
import com.target.trak.system.entity.Contact;
import com.target.trak.system.entity.criteria.ContactSearchCriteria;
import com.target.trak.system.persistence.ContactDao;
import com.target.trak.system.service.BaseTargetTrakService;
import com.target.trak.system.service.ContactService;
import com.target.trak.system.validations.TargetTrakValidator;

@Transactional(value = "dwTransactionManager", propagation = Propagation.NEVER)
public class ContactServiceImpl extends BaseTargetTrakService implements ContactService {

	private static Logger logger = Logger.getLogger(ContactServiceImpl.class);

	private ContactDao contactDao;
	private ConversionService conversionService;
	private TargetTrakValidator<ContactApiRequest> validator;

	@Transactional(value = "dwTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public ContactApiResponse createContact(final ContactApiRequest request) {
		ContactApiResponse response = new ContactApiResponse();
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

			Contact entity = contactDao.insertContact(conversionService.convert(request.getContact(), Contact.class));
			response.setContact(conversionService.convert(entity, ContactDto.class));
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setSuccess(Boolean.FALSE);
			response.setErrorType(TargetTrakErrorTypeEnum.ERROR);
			response.setMessage("An error has occurred trying to create a contact. <br /> If the error still occurs, contact your administrator");
		}
		return response;
	}

	@Override
	public ContactApiResponse getContactsByCriteria(final ContactApiRequest request) {
		ContactApiResponse response = new ContactApiResponse();
		ContactSearchCriteria criteria = conversionService.convert(request.getSearchCriteria(), ContactSearchCriteria.class);

		try {
			int totalSize = contactDao.selectContactsByCriteriaCount(criteria);
			List<ContactDto> dtos = null;
			List<Contact> contacts = null;
			if (totalSize > 0) {
				logger.info("Contact Search Criteria count found at least 1 contact");
				contacts = contactDao.selectContactsByCriteria(criteria);
			}
			dtos = convertEntitiesToDtoList(contacts);
			response.setContacts(dtos);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setSuccess(Boolean.FALSE);
			response.setErrorType(TargetTrakErrorTypeEnum.ERROR);
			response.setMessage("An error has occurred trying to get contacts. <br /> If the error still occurs, contact your administrator");
		}
		return response;
	}

	@Override
	public ContactApiResponse getContactById(final ContactApiRequest request) {
		ContactApiResponse response = new ContactApiResponse();
		try {
			Contact entity = contactDao.selectContactById(request.getContact().getId());
			response.setContact(conversionService.convert(entity, ContactDto.class));
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setSuccess(Boolean.FALSE);
			response.setErrorType(TargetTrakErrorTypeEnum.ERROR);
			response.setMessage("An error has occurred trying to get contact by id. <br /> If the error still occurs, contact your administrator");
		}
		return response;
	}

	@Transactional(value = "dwTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public ContactApiResponse updateContact(final ContactApiRequest request) {
		ContactApiResponse response = new ContactApiResponse();
		request.setRequestType(TargetTrakRequestTypeEnum.UPDATE);
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

			Contact entity = contactDao.updateContact(conversionService.convert(request.getContact(), Contact.class));
			response.setContact(conversionService.convert(entity, ContactDto.class));
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setSuccess(Boolean.FALSE);
			response.setErrorType(TargetTrakErrorTypeEnum.ERROR);
			response.setMessage("An error has occurred trying to update a contact by id. <br /> If the error still occurs, contact your administrator");
		}
		return response;
	}

	private List<ContactDto> convertEntitiesToDtoList(final List<Contact> contacts) {
		List<ContactDto> dtos = new ArrayList<ContactDto>();
		if (contacts == null || contacts.isEmpty()) {
			return dtos;
		}
		
		for (Contact contact : contacts) {
			dtos.add(conversionService.convert(contact, ContactDto.class));
		}
		return dtos;
	}

	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}

	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	public void setValidator(TargetTrakValidator<ContactApiRequest> validator) {
		this.validator = validator;
	}
}