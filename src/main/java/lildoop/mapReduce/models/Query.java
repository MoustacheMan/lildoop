package lildoop.mapReduce.models;

import org.json.JSONObject;

import lildoop.mapReduce.enums.ConditionOperator;
import lildoop.mapReduce.enums.Function;

public class Query {
	
	private Function func;
	private String fileName;
	private ConditionOperator condition;
	private String conditionValue;
	private JSONObject queryJson;
	
	public Query(JSONObject json) {
		this.queryJson = json;
		// Verify the validty of the data like the fileName
		validateValues();
	}

	private void validateValues() throws IllegalArgumentException {
		this.func = Function.valueOf(queryJson.getString("field").toUpperCase());
		this.fileName = queryJson.getString("data");
		this.condition = ConditionOperator.valueOf(queryJson.getString("condition"));
		this.conditionValue = queryJson.getString("conditionValue");
	}

	public Function getFunc() {
		return func;
	}

	public String getFileName() {
		return fileName;
	}

	public ConditionOperator getCondition() {
		return condition;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public JSONObject getQueryJson() {
		return queryJson;
	}
	
	public String getJsonString() {
		if (queryJson == null) {
			throw new IllegalStateException("Json object is null");
		} else {
			return queryJson.toString();
		}
	}
	
	public String getWorkJson(String[] rows) {
		throw new RuntimeException("Method not yet implemented");
	}
}
