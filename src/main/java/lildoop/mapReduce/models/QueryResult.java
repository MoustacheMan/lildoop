package lildoop.mapReduce.models;

import org.json.JSONObject;

import lildoop.mapReduce.enums.Function;

public class QueryResult {
	private Function function;
	
	private String param;
	
	private int value;
	
	private JSONObject queryJson;
	
	public QueryResult(JSONObject json) {
		this.queryJson = json;
		validateValues();
	}

	private void validateValues() throws IllegalArgumentException {
		this.function = Function.valueOf(queryJson.getString("function").toUpperCase());
		this.param = queryJson.getString("param");
		this.value = queryJson.getInt("value");
	}
	
	public Function getFunction() {
		return function;
	}

	public String getParam() {
		return param;
	}

	public int getValue() {
		return value;
	}
	
	public void updateValue(int newVal) {
		this.queryJson.remove("value");
		this.queryJson.put("value", newVal);
		this.value = newVal;
	}

	public JSONObject getQueryJson() {
		return queryJson;
	}

	public String getJSON() {
		return queryJson.toString();
	}
}
