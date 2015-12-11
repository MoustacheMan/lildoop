package lildoop.mapReduce.models;

import org.json.JSONObject;

import lildoop.mapReduce.enums.Function;

public class QueryResult {
	private Function function;
	
	private String param;
	
	private String value;
	
	private JSONObject queryJson;
	
	public QueryResult(JSONObject json) {
		this.queryJson = json;
		validateValues();
	}

	private void validateValues() throws IllegalArgumentException {
		this.function = Function.valueOf(queryJson.getString("field").toUpperCase());
		this.param = queryJson.getString("param");
		this.value = queryJson.getString("value");
	}
	
	public Function getFunction() {
		return function;
	}

	public String getParam() {
		return param;
	}

	public String getValue() {
		return value;
	}
	
	public void updateValue(String newVal) {
		this.value = newVal;
		this.queryJson.remove("value");
		this.queryJson.put("value", value);
	}

	public JSONObject getQueryJson() {
		return queryJson;
	}

	public String getJSON() {
		return queryJson.toString();
	}
}
