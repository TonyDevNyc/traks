package com.target.trak.system.web.ui.helpers.impl;

import java.util.ArrayList;
import java.util.List;

import com.target.trak.system.dto.referencedata.ReferenceDataDto;
import com.target.trak.system.dto.util.DateUtil;
import com.target.trak.system.web.ui.LabelValuePair;
import com.target.trak.system.web.ui.ReferenceDataItem;
import com.target.trak.system.web.ui.helpers.ReferenceDataHelper;

public class ReferenceDataHelperImpl implements ReferenceDataHelper {

	@Override
	public List<LabelValuePair> buildReferenceDataList(List<ReferenceDataDto> referenceDataList) {
		List<LabelValuePair> list = new ArrayList<LabelValuePair>();
		if (referenceDataList != null && !referenceDataList.isEmpty()) {
			for (ReferenceDataDto dto : referenceDataList) {
				list.add(new LabelValuePair(dto.getLabel(), dto.getValue()));
			}
		}
		return list;
	}

	@Override
	public ReferenceDataItem convertReferenceDataItem(ReferenceDataDto dto) {
		ReferenceDataItem item = new ReferenceDataItem();
		item.setId(dto.getId());
		item.setType(dto.getType());
		item.setLabel(dto.getLabel());
		item.setValue(dto.getValue());
		item.setStatus(dto.getStatus());
		item.setVersion(dto.getVersion());
		item.setCreatedBy(dto.getCreatedBy());
		item.setCreatedDateTime(DateUtil.convertDateToIso8601(dto.getCreatedDateTime()));
		item.setLastUpdatedBy(dto.getLastUpdatedBy());
		item.setLastUpdatedDateTime(DateUtil.convertDateToIso8601(dto.getLastUpdatedDateTime()));
		return item;
	}

}
