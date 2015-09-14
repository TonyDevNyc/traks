package com.target.trak.system.service;

import com.target.trak.system.service.dto.contact.ContactApiRequest;
import com.target.trak.system.service.dto.contact.ContactApiResponse;

public interface ContactService {
	
	public ContactApiResponse createContact(final ContactApiRequest request);
	
	public ContactApiResponse getContactsByCriteria(final ContactApiRequest request);
	
	public ContactApiResponse getContactById(final ContactApiRequest request);
	
	public ContactApiResponse updateContact(final ContactApiRequest request);
	
}
