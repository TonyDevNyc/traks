package com.target.trak.system.persistence.builder;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.StringUtils;

import com.target.trak.system.entity.criteria.TextSearchCriteria;

public class ReferenceDataQueryBuilder {

	private static final String DEFAULT_SORT_ORDER = " ORDER BY reference_data_type, reference_data_label ASC ";
	private static final String REFERENCE_DATA_TYPE_COLUMN = "reference_data_type";
	private static final String REFERENCE_DATA_LABEL_COLUMN = "reference_data_label";
	private static final String STATUS_COLUMN = "status";
	private static final String CREATED_BY_COLUMN = "created_by";
	private static final String LAST_UPDATED_BY_COLUMN = "last_updated_by";

	private Logger logger = Logger.getLogger(getClass());

	private Properties referenceDataQueries;

	public String buildCountQueryByCriteria(final TextSearchCriteria criteria, final MapSqlParameterSource params) {
		StringBuilder builder = new StringBuilder();
		builder.append(referenceDataQueries.get("selectCountOfAllReferenceDataSql"));
		builder.append(QueryConstantsEnum.WHERE_CLAUSE.value).append(QueryConstantsEnum.EMPTY_SPACE.value);
		builder.append(buildCriteriaQuery(criteria, params));
		logger.info("Reference Data Count Query Built: " + builder.toString());
		return builder.toString();
	}

	public String buildPaginatedQueryByCriteria(final TextSearchCriteria criteria, final MapSqlParameterSource params) {
		StringBuilder builder = new StringBuilder();
		builder.append(referenceDataQueries.getProperty("baseReferenceDataPagingSql"));
		builder.append(QueryConstantsEnum.WHERE_CLAUSE.value).append(QueryConstantsEnum.EMPTY_SPACE.value);
		builder.append(buildCriteriaQuery(criteria, params));

		if (StringUtils.isEmpty(criteria.getSortField())) {
			builder.append(DEFAULT_SORT_ORDER);
		} else {
			builder.append(QueryConstantsEnum.ORDER_BY_CLAUSE.value).append(":sortField").append(QueryConstantsEnum.EMPTY_SPACE.value).append(QueryConstantsEnum.DESCENDING_ORDER.value);
			params.addValue("sortField", criteria.getSortField());
		}

		builder.append(QueryConstantsEnum.LIMIT_CLAUSE.value).append(":pageStart").append(QueryConstantsEnum.COMMA.value).append(":pageEnd");
		params.addValue("pageStart", criteria.getStart());
		params.addValue("pageEnd", criteria.getEnd());

		logger.info("buildPaginatedQueryByCriteria - Reference Data Query Built: " + builder.toString());
		return builder.toString();
	}

	private String buildCriteriaQuery(final TextSearchCriteria criteria, final MapSqlParameterSource params) {
		StringBuilder builder = new StringBuilder();

		if (!StringUtils.isEmpty(criteria.getText())) {
			builder.append(QueryConstantsEnum.AND.value);
			builder.append(QueryConstantsEnum.OPEN_PARENTHESIS.value);

			// referenceDataType
			builder.append(REFERENCE_DATA_TYPE_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":referenceDataType");
			params.addValue("referenceDataType", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// referenceDataLabel
			builder.append(REFERENCE_DATA_LABEL_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":referenceDataLabel");
			params.addValue("referenceDataLabel", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// status
			builder.append(STATUS_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":status");
			params.addValue("status", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// createdBy
			builder.append(CREATED_BY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":createdBy");
			params.addValue("createdBy", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// lastUpdatedBy
			builder.append(LAST_UPDATED_BY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":lastUpdatedBy");
			params.addValue("lastUpdatedBy", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));

			builder.append(QueryConstantsEnum.CLOSE_PARENTHESIS.value);

		}
		logger.info("Reference Data Query Built: " + builder.toString());
		return builder.toString();
	}

	public void setReferenceDataQueries(Properties referenceDataQueries) {
		this.referenceDataQueries = referenceDataQueries;
	}
}