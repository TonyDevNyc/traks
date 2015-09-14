package com.target.trak.system.persistence;

import java.util.List;

import com.target.trak.system.entity.Court;
import com.target.trak.system.entity.criteria.TextSearchCriteria;

public interface CourtDao {

	public Court insertCourt(final Court court);
	
	public Court selectCourtById(final Long id);
	
	public List<Court> selectListOfAllCourts();
	
	public Court updateCourt(final Court court);
	
	public List<Court> selectCourtsByCriteria(final TextSearchCriteria criteria);
}
