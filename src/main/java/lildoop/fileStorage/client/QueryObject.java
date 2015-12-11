package lildoop.fileStorage.client;

import lildoop.mapReduce.enums.ConditionOperator;

public class QueryObject {
	public String functionColumn;
	public String field;
	public ConditionOperator condition;
	public String data;
	public String conditionValue;
	public String conditionColumn;

	public boolean IsCondition()
	{
		return true;
	}
	
}
