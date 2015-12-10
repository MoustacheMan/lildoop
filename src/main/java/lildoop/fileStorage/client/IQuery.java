package lildoop.fileStorage.client;

public interface IQuery {
	void count(String columnName);
	void sum(String columnName);
	void avg(String columnName);
	void where(String columnName, String operation, Condition value);
	void from(String Table);
}
