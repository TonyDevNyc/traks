package com.target.trak.system.persistence.builder;

public enum QueryConstantsEnum {

	WHERE_CLAUSE(" WHERE 1 = 1"),
	AND (" AND "),
	EQUALS(" = "),
	LIKE(" LIKE "),
	WILDCARD("%"),
	ORDER_BY_CLAUSE(" ORDER BY "),
	ASCENDING_ORDER(" ASC "),
	DESCENDING_ORDER(" DESC "),
	LIMIT_CLAUSE(" LIMIT "),
	COMMA(","),
	EMPTY_SPACE(" "),
	OPEN_PARENTHESIS(" ( "),
	CLOSE_PARENTHESIS(" ) "),
	OR (" OR ");
	
	public String value;

	public String getValue() {
		return value;
	}

	private QueryConstantsEnum(String value) {
		this.value = value;
	}
	
	public static String buildWildcardParameter(final String parameterValue) {
		StringBuilder builder = new StringBuilder();
		builder.append(QueryConstantsEnum.WILDCARD.value)
			.append(parameterValue)
			.append(QueryConstantsEnum.WILDCARD.value);
		return builder.toString();
	}
}
