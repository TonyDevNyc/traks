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

import com.target.trak.system.entity.Company;
import com.target.trak.system.entity.Contact;
import com.target.trak.system.entity.criteria.ContactSearchCriteria;
import com.target.trak.system.persistence.ContactDao;
import com.target.trak.system.service.dto.contact.ContactApiRequest;
import com.target.trak.system.service.dto.contact.ContactApiResponse;
import com.target.trak.system.service.dto.contact.ContactDto;
import com.target.trak.system.service.dto.contact.ContactSearchCriteriaDto;
import com.target.trak.system.service.validations.TargetTrakValidationError;
import com.target.trak.system.service.validations.TargetTrakValidator;

public class ContactServiceImplTest {

	private Mockery mockery;
	private ContactDao contactDaoMock;
	private ConversionService conversionServiceMock;
	private TargetTrakValidator<ContactApiRequest> validatorMock;
	private ContactServiceImpl contactService;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		mockery = new JUnit4Mockery();
		contactDaoMock = mockery.mock(ContactDao.class);
		conversionServiceMock = mockery.mock(ConversionService.class);
		validatorMock = mockery.mock(TargetTrakValidator.class);

		contactService = new ContactServiceImpl();
		contactService.setContactDao(contactDaoMock);
		contactService.setConversionService(conversionServiceMock);
		contactService.setValidator(validatorMock);
	}

	@Test(expected = NullPointerException.class)
	public void createContactForNullRequest() {
		final ContactApiRequest request = null;
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new IllegalArgumentException("API request is null")));
			}
		});
		contactService.createContact(request);
	}

	@Test
	public void createContactForValidationErrors() {
		final ContactApiRequest request = new ContactApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(buildValidationErrors()));
			}
		});
		ContactApiResponse response = contactService.createContact(request);
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertTrue("Validation Errors are not empty", !response.getErrors().isEmpty());
	}

	@Test
	public void createContactDataAccessExcepton() {
		final ContactApiRequest request = new ContactApiRequest();
		request.setContact(buildContactDto());

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getContact())), with(Contact.class));
				will(returnValue(buildContact()));

				oneOf(contactDaoMock).insertContact(with(any(Contact.class)));
				will(throwException(new BadSqlGrammarException("create", "create..", new SQLException())));
			}
		});
		ContactApiResponse response = contactService.createContact(request);
		Assert.assertFalse("Contact Api Response is unsuccessful", response.isSuccess());
	}

	@Test
	public void createContactSuccessful() {
		final ContactApiRequest request = new ContactApiRequest();
		request.setContact(buildContactDto());

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getContact())), with(Contact.class));
				will(returnValue(buildContact()));

				oneOf(contactDaoMock).insertContact(with(any(Contact.class)));
				will(returnValue(buildContact()));

				oneOf(conversionServiceMock).convert(with(any(Contact.class)), with(ContactDto.class));
				will(returnValue(buildContactDto()));
			}
		});
		ContactApiResponse response = contactService.createContact(request);
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertNull("Validation Errors is null", response.getErrors());
	}

	@Test
	public void getContactsByCriteriaForException() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(conversionServiceMock).convert(with(any(ContactSearchCriteriaDto.class)), with(ContactSearchCriteria.class));
				will(returnValue(new ContactSearchCriteria()));

				oneOf(contactDaoMock).selectContactsByCriteriaCount(with(any(ContactSearchCriteria.class)));
				will(throwException(new EmptyResultDataAccessException(1)));
			}
		});
		ContactApiResponse response = contactService.getContactsByCriteria(new ContactApiRequest());
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertNull("Validation Errors is null", response.getErrors());
	}

	@Test
	public void getContactsByCriteriaForEmptyResult() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(conversionServiceMock).convert(with(any(ContactSearchCriteriaDto.class)), with(ContactSearchCriteria.class));
				will(returnValue(new ContactSearchCriteria()));

				oneOf(contactDaoMock).selectContactsByCriteriaCount(with(any(ContactSearchCriteria.class)));
				will(returnValue(1));

				oneOf(contactDaoMock).selectContactsByCriteria(with(any(ContactSearchCriteria.class)));
				will(returnValue(new ArrayList<Contact>()));
			}
		});
		ContactApiResponse response = contactService.getContactsByCriteria(new ContactApiRequest());
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertNull("Validation Errors is null", response.getErrors());
	}
	
	@Test
	public void getContactsByCriteriaForNullResults() {
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(conversionServiceMock).convert(with(any(ContactSearchCriteriaDto.class)), with(ContactSearchCriteria.class));
				will(returnValue(new ContactSearchCriteria()));

				oneOf(contactDaoMock).selectContactsByCriteriaCount(with(any(ContactSearchCriteria.class)));
				will(returnValue(1));

				oneOf(contactDaoMock).selectContactsByCriteria(with(any(ContactSearchCriteria.class)));
				will(returnValue(null));
			}
		});
		ContactApiResponse response = contactService.getContactsByCriteria(new ContactApiRequest());
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertNull("Validation Errors is null", response.getErrors());
	}

	@Test
	public void getContactsByCriteriaForSuccess() {
		final ContactApiRequest request = new ContactApiRequest();
		ContactSearchCriteriaDto contactSearchCriteria = new ContactSearchCriteriaDto();
		contactSearchCriteria.setText("Wilson");
		request.setSearchCriteria(contactSearchCriteria);

		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(conversionServiceMock).convert(with(equal(request.getSearchCriteria())), with(ContactSearchCriteria.class));
				will(returnValue(new ContactSearchCriteria()));

				oneOf(contactDaoMock).selectContactsByCriteriaCount(with(any(ContactSearchCriteria.class)));
				will(returnValue(1020));

				oneOf(contactDaoMock).selectContactsByCriteria(with(any(ContactSearchCriteria.class)));
				will(returnValue(buildContactsList()));

				atLeast(1).of(conversionServiceMock).convert(with(equal(buildContact())), with(ContactDto.class));
				will(returnValue(buildContactDto()));
			}
		});
		ContactApiResponse response = contactService.getContactsByCriteria(request);
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertNull("Validation Errors is null", response.getErrors());
	}

	@Test
	public void getContactByIdForDataAccessException() {
		final ContactApiRequest request = new ContactApiRequest();
		request.setContact(buildContactDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(contactDaoMock).selectContactById(with(any(Long.class)));
				will(throwException(new BadSqlGrammarException("select", "select..", new SQLException())));
			}
		});
		ContactApiResponse response = contactService.getContactById(request);
		Assert.assertFalse("Contact Api Response is unsuccessful", response.isSuccess());
	}

	@Test
	public void getContactByIdSuccessful() {
		final ContactApiRequest request = new ContactApiRequest();
		request.setContact(buildContactDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(contactDaoMock).selectContactById(with(any(Long.class)));
				will(returnValue(buildContact()));

				oneOf(conversionServiceMock).convert(with(equal(buildContact())), with(ContactDto.class));
				will(returnValue(buildContactDto()));
			}
		});
		ContactApiResponse response = contactService.getContactById(request);
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertTrue("Contact Api Response is successful", response.isSuccess());
	}

	@Test
	public void updateContactForNullContactDto() {
		final ContactApiRequest request = new ContactApiRequest();
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(throwException(new IllegalArgumentException("Contact is null")));
			}
		});
		ContactApiResponse response = contactService.updateContact(request);
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertFalse("Contact Api Response is unsuccessful", response.isSuccess());
	}

	@Test
	public void updateContactForValidationErrors() {
		final ContactApiRequest request = new ContactApiRequest();
		request.setContact(buildContactDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(buildValidationErrors()));
			}
		});
		ContactApiResponse response = contactService.updateContact(request);
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertFalse("Contact Api Response is unsuccessful", response.isSuccess());
		Assert.assertFalse("Contact validation errors is not empty", response.getErrors().isEmpty());
	}

	@Test
	public void updateContactForDataAccessException() {
		final ContactApiRequest request = new ContactApiRequest();
		request.setContact(buildContactDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getContact())), with(Contact.class));
				will(returnValue(new Company()));

				oneOf(contactDaoMock).updateContact(with(any(Contact.class)));
				will(throwException(new BadSqlGrammarException("updat", "update..", new SQLException())));
			}
		});
		ContactApiResponse response = contactService.updateContact(request);
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertFalse("Contact Api Response is unsuccessful", response.isSuccess());
	}

	@Test
	public void updateContactSuccessful() {
		final ContactApiRequest request = new ContactApiRequest();
		request.setContact(buildContactDto());
		mockery.checking(new org.jmock.Expectations() {
			{
				oneOf(validatorMock).validate(with(request));
				will(returnValue(new ArrayList<TargetTrakValidationError>()));

				oneOf(conversionServiceMock).convert(with(equal(request.getContact())), with(Contact.class));
				will(returnValue(buildContact()));

				oneOf(contactDaoMock).updateContact(with(any(Contact.class)));
				will(returnValue(buildContact()));

				oneOf(conversionServiceMock).convert(with(equal(buildContact())), with(ContactDto.class));
				will(returnValue(buildContactDto()));
			}
		});
		ContactApiResponse response = contactService.updateContact(request);
		Assert.assertNotNull("Contact Api Response is not null", response);
		Assert.assertTrue("Contact Api Response is successful", response.isSuccess());
	}

	private List<Contact> buildContactsList() {
		return Collections.singletonList(buildContact());
	}

	private ContactDto buildContactDto() {
		ContactDto dto = new ContactDto();
		dto.setId(1L);
		dto.setFirstName("Wilson");
		dto.setLastName("Thompson");
		return dto;
	}

	private Contact buildContact() {
		Contact contact = new Contact();
		contact.setId(1L);
		contact.setFirstName("Wilson");
		contact.setLastName("Thompson");
		return contact;
	}

	private List<TargetTrakValidationError> buildValidationErrors() {
		List<TargetTrakValidationError> errors = new ArrayList<TargetTrakValidationError>();
		errors.add(new TargetTrakValidationError("label", "LABEL_ERROR_001"));
		return errors;
	}
}
