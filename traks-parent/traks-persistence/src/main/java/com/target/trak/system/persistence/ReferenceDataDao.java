package com.target.trak.system.persistence;

import java.util.List;

import com.target.trak.system.entity.ReferenceData;
import com.target.trak.system.entity.criteria.TextSearchCriteria;

public interface ReferenceDataDao {

	public ReferenceData insertReferenceData(final ReferenceData referenceData);
	
	//public List<ReferenceData> selectDefaultPaginatedReferenceData(final TextSearchCriteria criteria);
	
	public int selectDefaultReferenceDataCount(final TextSearchCriteria criteria);
	
	public List<ReferenceData> selectPaginatedReferenceDataBySearchCriteria(final TextSearchCriteria criteria);
	
	public int selectReferenceDataBySearchCriteriaCount(final TextSearchCriteria criteria);
	
	public boolean referenceDataAlreadyExists(final String type, final String label, final String value);
	
	public List<ReferenceData> getReferenceDataTypes();
	
	public ReferenceData updateReferenceData(final ReferenceData referenceData);
	
	public void deleteReferenceData(final ReferenceData referenceData);
	
	public List<ReferenceData> selectReferenceDataByType(final String referenceDataType);
	
	public ReferenceData selectReferenceDataByFields(final String type, final String label, final String value);
	
	public ReferenceData selectReferenceDataById(final Long id);
}
