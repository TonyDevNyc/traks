package com.target.trak.system.batch.referencedataimport.mappers;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.target.trak.system.entity.ReferenceData;

public class ReferenceDataFieldSetMapper implements FieldSetMapper<ReferenceData> {
	
	private static final String JOB_NAME = "reference-data-import-job";
	private static final String TYPE = "TYPE";
	private static final String LABEL = "LABEL";
	private static final String VALUE = "VALUE";
	private static final String STATUS = "STATUS";

	@Override
	public ReferenceData mapFieldSet(FieldSet fieldSet) throws BindException {
		ReferenceData referenceData = new ReferenceData();
		referenceData.setReferenceDataType(fieldSet.readString(TYPE));
		referenceData.setReferenceDataLabel(fieldSet.readString(LABEL));
		referenceData.setReferenceDataValue(fieldSet.readString(VALUE));
		referenceData.setStatus(fieldSet.readString(STATUS));
		referenceData.setCreatedBy(JOB_NAME);
		referenceData.setLastUpdatedBy(JOB_NAME);
		Calendar c = Calendar.getInstance();
		Timestamp currentTs = new Timestamp(c.getTimeInMillis());
		referenceData.setCreatedTimestamp(currentTs);
		referenceData.setLastUpdatedTimestamp(currentTs);
		return referenceData;
	}
}
