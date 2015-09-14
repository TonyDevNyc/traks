package com.target.trak.system.persistence;

import java.util.List;

import com.target.trak.system.entity.Contact;
import com.target.trak.system.entity.criteria.ContactSearchCriteria;

public interface ContactDao {

	public Contact insertContact(final Contact contact);
	
	public Contact selectContactById(final Long id);
	
	public List<Contact> selectContactsByCriteria(final ContactSearchCriteria criteria);
	
	public Contact updateContact(final Contact contact);
	
	public int selectContactsByCriteriaCount(final ContactSearchCriteria criteria);
}
