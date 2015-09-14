package com.target.trak.system.persistence.builder;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.target.trak.system.entity.criteria.ContactSearchCriteria;

public class ContactQueryBuilder {

	private static final String TITLE_COLUMN = "ct.title";
	private static final String FIRST_NAME_COLUMN = "ct.first_name";
	private static final String LAST_NAME_COLUMN = "ct.last_name";
	private static final String SUFFIX_COLUMN = "ct.suffix";
	private static final String CONTACT_TYPE_COLUMN = "ct.contact_type";
	private static final String COMPANY_NAME_COLUMN = "com.name";
	private static final String CITY_COLUMN = "com.city";
	private static final String STATE_COLUMN = "com.state";
	private static final String CREATED_BY_COLUMN = "ct.created_by";
	private static final String LAST_UPDATED_BY_COLUMN = "ct.last_updated_by";

	private static final String DEFAULT_SORT_ORDER = " ORDER BY last_name ASC ";

	private Logger logger = Logger.getLogger(getClass());

	private Properties contactQueries;

	public String buildContactQueryByCriteriaCount(final ContactSearchCriteria criteria, final MapSqlParameterSource params) {
		StringBuilder builder = new StringBuilder();
		String baseCountSql = contactQueries.getProperty("baseSelectContactsByCriteriaCountSql");
		builder.append(baseCountSql);
		builder.append(buildCriteriaQuery(criteria, params));
		return builder.toString();
	}

	public String buildPaginatedContactQueryByCriteria(final ContactSearchCriteria criteria, final MapSqlParameterSource params) {
		StringBuilder builder = new StringBuilder();
		String baseCountSql = contactQueries.getProperty("baseSelectContactsByCriteriaSql");
		builder.append(baseCountSql);
		builder.append(buildCriteriaQuery(criteria, params));

		if (StringUtils.isEmpty(criteria.getSortField())) {
			builder.append(DEFAULT_SORT_ORDER);
		} else {
			builder.append(QueryConstantsEnum.ORDER_BY_CLAUSE.value).append(":sortField").append(QueryConstantsEnum.EMPTY_SPACE.value).append(QueryConstantsEnum.ASCENDING_ORDER.value);
			params.addValue("sortField", criteria.getSortField());
		}

		builder.append(QueryConstantsEnum.LIMIT_CLAUSE.value).append(":pageStart").append(QueryConstantsEnum.COMMA.value).append(":pageEnd");
		params.addValue("pageStart", criteria.getStart());
		params.addValue("pageEnd", criteria.getEnd());
		logger.info("Query built: " + builder.toString());
		return builder.toString();
	}

	private String buildCriteriaQuery(final ContactSearchCriteria criteria, final MapSqlParameterSource params) {
		StringBuilder builder = new StringBuilder();

		if (!StringUtils.isEmpty(criteria.getText())) {
			builder.append(QueryConstantsEnum.AND.value);
			builder.append(QueryConstantsEnum.OPEN_PARENTHESIS.value);

			// title
			builder.append(TITLE_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":title");
			params.addValue("title", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// firstName
			builder.append(FIRST_NAME_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":firstName");
			params.addValue("firstName", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// lastName
			builder.append(LAST_NAME_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":lastName");
			params.addValue("lastName", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// suffix
			builder.append(SUFFIX_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":suffix");
			params.addValue("suffix", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// contactType
			builder.append(CONTACT_TYPE_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":contactType");
			params.addValue("contactType", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// companyName
			builder.append(COMPANY_NAME_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":companyName");
			params.addValue("companyName", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// city
			builder.append(CITY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":city");
			params.addValue("city", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);

			// state
			builder.append(STATE_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":state");
			params.addValue("state", QueryConstantsEnum.buildWildcardParameter(criteria.getText()));
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

		logger.info("Query built: " + builder.toString());
		return builder.toString();
	}

	public void setContactQueries(Properties contactQueries) {
		this.contactQueries = contactQueries;
	}
}