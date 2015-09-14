package com.target.trak.system.persistence;

import com.target.trak.system.entity.CourtFilingService;

public interface CourtFilingServiceDao {

	public CourtFilingService insertCourtFiling(final CourtFilingService courtFiling);
	
	public CourtFilingService getCourtFilingById(final Long id);
	
	public CourtFilingService updateCourtFiling(final CourtFilingService courtFiling);
}
