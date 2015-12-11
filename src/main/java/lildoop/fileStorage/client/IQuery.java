package lildoop.fileStorage.client;

public interface IQuery {
	LilDoopQueryString count(String columnName);
	LilDoopQueryString sum(String columnName);
	LilDoopQueryString avg(String columnName);
	void where(String columnName, Condition operation, String value);
	LilDoopQueryString from(String Table);
}
