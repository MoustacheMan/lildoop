package lildoop.mapReduce.models;

import org.json.JSONObject;

import lildoop.mapReduce.enums.ConditionOperator;
import lildoop.mapReduce.enums.Function;

public class Query {
	
	private Function func;
	private String funcColumn;
	private String fileName;
	private ConditionOperator condition;
	private String conditionColumn;
	private String conditionValue;
	private JSONObject queryJson;
	
	public Query(JSONObject json) {
		this.queryJson = json;
		// Verify the validty of the data like the fileName
		validateValues();
	}

	private void validateValues() throws IllegalArgumentException {
		this.func = Function.valueOf(queryJson.getString("field").toUpperCase());
		this.conditionColumn = queryJson.getString("functionColumn");
		this.fileName = queryJson.getString("data");
		this.condition = ConditionOperator.valueOf(queryJson.getString("condition"));
		this.conditionColumn = queryJson.getString("conditionColumn");
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

	public String getFuncColumn() {
		return funcColumn;
	}

	public String getConditionColumn() {
		return conditionColumn;
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
	
	public String getWorkJson(JSONObject[] rows) {
		JSONObject work = new JSONObject(queryJson, JSONObject.getNames(queryJson));
		Object val = work.remove("data");
		work.put("data", rows);
//		if (val == null) {
//			throw new IllegalStateException("daa field is not present in received Json");
//		}
		return work.toString();
	}
}
