package lildoop.mapReduce.models;

import org.json.JSONObject;

import lildoop.mapReduce.enums.ConditionOperator;
import lildoop.mapReduce.enums.Function;

public class QueryResult {
	private Function function;
	
	private String param;
	
	private String value;
	
	private JSONObject queryJson;
	
	public QueryResult(JSONObject json) {
		this.queryJson = json;
		// Verify the validty of the data like the fileName
		validateValues();
	}

	private void validateValues() throws IllegalArgumentException {
		this.function = Function.valueOf(queryJson.getString("field").toUpperCase());
		this.param = queryJson.getString("param");
		this.value = queryJson.getString("value");
	}
	
	public String getJSON() {
		return queryJson.toString();
	}
}
