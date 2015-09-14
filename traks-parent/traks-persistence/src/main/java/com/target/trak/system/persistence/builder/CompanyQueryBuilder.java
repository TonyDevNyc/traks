package com.target.trak.system.persistence.builder;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.StringUtils;

import com.target.trak.system.entity.criteria.CompanySearchCriteria;

public class CompanyQueryBuilder {

	private static final String NAME_COLUMN = "name";
	private static final String STATE_COLUMN = "state";
	private static final String COUNTRY_COLUMN = "country";
	private static final String CITY_COLUMN = "city";
	private static final String CREATED_BY_COLUMN = "created_by";
	private static final String LAST_UPDATED_BY_COLUMN = "last_updated_by";
	private static final String DEFAULT_SORT_ORDER = " ORDER BY name ASC ";

	private Logger logger = Logger.getLogger(getClass());

	private Properties companyQueries;

	public String buildPagingQueryCountByCriteria(final CompanySearchCriteria criteria, final MapSqlParameterSource params) {
		StringBuilder builder = new StringBuilder();
		builder.append(companyQueries.get("selectBaseCountCompanySql"));
		builder.append(QueryConstantsEnum.WHERE_CLAUSE.value).append(QueryConstantsEnum.EMPTY_SPACE.value);

		if (!StringUtils.isEmpty(criteria.getText())) {
			builder.append(QueryConstantsEnum.AND.value);
			
			builder.append(QueryConstantsEnum.OPEN_PARENTHESIS.value);
			
			// name 
			builder.append(NAME_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":name");
			params.addValue("name", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			// state
			builder.append(STATE_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":state");
			params.addValue("state", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			//country
			builder.append(COUNTRY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":country");
			params.addValue("country", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			// city
			builder.append(CITY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":city");
			params.addValue("city", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			// createdBy
			builder.append(CREATED_BY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":createdBy");
			params.addValue("createdBy", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			// lastUpdatedBy
			builder.append(LAST_UPDATED_BY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":lastUpdatedBy");
			params.addValue("lastUpdatedBy", buildWildcardParameter(criteria.getText()));
			
			builder.append(QueryConstantsEnum.CLOSE_PARENTHESIS.value);
		}
		logger.info("Company Paging Count Query Built: " + builder.toString());
		return builder.toString();
	}

	public String buildPaginatedQueryByCriteria(final CompanySearchCriteria criteria, final MapSqlParameterSource params) {
		StringBuilder builder = new StringBuilder();
		builder.append(companyQueries.get("baseCompanySql"));
		builder.append(QueryConstantsEnum.WHERE_CLAUSE.value).append(QueryConstantsEnum.EMPTY_SPACE.value);

		if (!StringUtils.isEmpty(criteria.getText())) {
			builder.append(QueryConstantsEnum.AND.value);
			
			builder.append(QueryConstantsEnum.OPEN_PARENTHESIS.value);
			
			// name 
			builder.append(NAME_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":name");
			params.addValue("name", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			// state
			builder.append(STATE_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":state");
			params.addValue("state", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			//country
			builder.append(COUNTRY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":country");
			params.addValue("country", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			// city
			builder.append(CITY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":city");
			params.addValue("city", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			// createdBy
			builder.append(CREATED_BY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":createdBy");
			params.addValue("createdBy", buildWildcardParameter(criteria.getText()));
			builder.append(QueryConstantsEnum.OR.value);
			
			// lastUpdatedBy
			builder.append(LAST_UPDATED_BY_COLUMN).append(QueryConstantsEnum.LIKE.value).append(":lastUpdatedBy");
			params.addValue("lastUpdatedBy", buildWildcardParameter(criteria.getText()));
			
			builder.append(QueryConstantsEnum.CLOSE_PARENTHESIS.value);
		}

		if (StringUtils.isEmpty(criteria.getSortField())) {
			builder.append(DEFAULT_SORT_ORDER);
		} else {
			builder.append(QueryConstantsEnum.ORDER_BY_CLAUSE.value).append(":sortField").append(QueryConstantsEnum.EMPTY_SPACE.value).append(QueryConstantsEnum.ASCENDING_ORDER.value);
			params.addValue("sortField", criteria.getSortField());
		}

		builder.append(QueryConstantsEnum.LIMIT_CLAUSE.value).append(":pageStart").append(QueryConstantsEnum.COMMA.value).append(":pageEnd");
		params.addValue("pageStart", criteria.getStart());
		params.addValue("pageEnd", criteria.getEnd());

		logger.info("Company Paging Query Built: " + builder.toString());
		return builder.toString();
	}

	private String buildWildcardParameter(final String parameterValue) {
		StringBuilder builder = new StringBuilder();
		builder.append(QueryConstantsEnum.WILDCARD.value)
			.append(parameterValue)
			.append(QueryConstantsEnum.WILDCARD.value);
		return builder.toString();
	}
	
	public void setCompanyQueries(Properties companyQueries) {
		this.companyQueries = companyQueries;
	}
}