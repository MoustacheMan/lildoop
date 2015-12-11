package lildoop.mapReduce.client;

import lildoop.mapReduce.enums.ConditionOperator;

public interface IQuery {
	LilDoopQueryString count(String columnName);
	LilDoopQueryString sum(String columnName);
	LilDoopQueryString avg(String columnName);
	void where(String columnName, ConditionOperator operation, String value);
	LilDoopQueryString from(String Table);
}
