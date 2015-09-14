package com.target.trak.system.service.dto.referencedata;

import org.springframework.core.convert.converter.Converter;

import com.target.trak.system.entity.ReferenceData;
import com.target.trak.system.service.util.DateUtil;

public class ReferenceDataEntityToDtoConverter implements Converter<ReferenceData, ReferenceDataDto> {

	@Override
	public ReferenceDataDto convert(ReferenceData entity) {
		ReferenceDataDto dto = new ReferenceDataDto();
		dto.setId(entity.getId());
		dto.setType(entity.getReferenceDataType());
		dto.setLabel(entity.getReferenceDataLabel());
		dto.setValue(entity.getReferenceDataValue());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setStatus(entity.getStatus());
		dto.setCreatedDateTime(DateUtil.convertTimestampToCalendar(entity.getCreatedTimestamp()));
		dto.setLastUpdatedBy(entity.getLastUpdatedBy());
		dto.setLastUpdatedDateTime(DateUtil.convertTimestampToCalendar(entity.getLastUpdatedTimestamp()));
		dto.setVersion(entity.getVersion());
		return dto;
	}

}
