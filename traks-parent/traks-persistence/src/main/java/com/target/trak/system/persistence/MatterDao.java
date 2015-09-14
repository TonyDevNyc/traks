package com.target.trak.system.persistence;

import java.util.List;

import com.target.trak.system.entity.Matter;
import com.target.trak.system.entity.criteria.TextSearchCriteria;

public interface MatterDao {

	public Matter insertMatter(final Matter matter);
	
	public Matter selectMatterById(final Long id);
	
	public List<Matter> selectListOfMattersByCriteria(final TextSearchCriteria criteria);
	
	public Matter updateMatter(final Matter matter);
}
