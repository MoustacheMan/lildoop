package lildoop.mapReduce.models;

import org.json.JSONObject;

import lildoop.mapReduce.enums.ConditionOperator;
import lildoop.mapReduce.enums.Function;

public class Query {
	
	private Function func;
	private String funcColumn;
	private String data;
	private ConditionOperator condition;
	private String conditionColumn;
	private String conditionValue;
	private JSONObject queryJson;
	private boolean isWork;
	
	public Query(JSONObject json, boolean isWork) {
		this.queryJson = json;
		this.isWork = isWork;
		// Verify the validty of the data like the fileName
		validateValues();
	}

	private void validateValues() throws IllegalArgumentException {
		this.func = Function.valueOf(queryJson.getString("field").toUpperCase());
		this.funcColumn = queryJson.getString("functionColumn");
		if (isWork) {
			this.data = queryJson.getJSONArray("data").toString();
		} else {
			this.data = queryJson.getString("data");
		}
		
		this.condition = queryJson.isNull("condition") ? ConditionOperator.NO_COND : ConditionOperator.valueOf(queryJson.getString("condition"));
		this.conditionColumn = queryJson.isNull("conditionColumn") ? "null" : queryJson.getString("conditionColumn");
		this.conditionValue = queryJson.isNull("conditionValue") ? funcColumn : queryJson.getString("conditionValue");
	}

	public Function getFunc() {
		return func;
	}

	public String getFileName() {
		return data;
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
		work.remove("data");
		work.put("data", rows);
		return work.toString();
	}
}
