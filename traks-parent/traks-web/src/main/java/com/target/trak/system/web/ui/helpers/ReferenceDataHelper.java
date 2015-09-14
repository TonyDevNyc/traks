package com.target.trak.system.web.ui.helpers;

import java.util.List;

import com.target.trak.system.dto.referencedata.ReferenceDataDto;
import com.target.trak.system.web.ui.LabelValuePair;
import com.target.trak.system.web.ui.ReferenceDataItem;

public interface ReferenceDataHelper {

	public List<LabelValuePair> buildReferenceDataList(List<ReferenceDataDto> referenceDataList);
	
	public ReferenceDataItem convertReferenceDataItem(ReferenceDataDto dto);
	
}
