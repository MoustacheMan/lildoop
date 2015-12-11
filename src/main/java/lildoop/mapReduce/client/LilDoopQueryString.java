package lildoop.mapReduce.client;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import lildoop.mapReduce.enums.ConditionOperator;

public class LilDoopQueryString implements IQuery {
	QueryObject queryString ;
	public LilDoopQueryString() {
		queryString = new QueryObject();
	}
	@Override
	public LilDoopQueryString count(String columnName) {
		queryString.functionColumn = columnName;
		queryString.field = "count";
		return this;
	}

	@Override
	public LilDoopQueryString sum(String columnName) {
		queryString.functionColumn = columnName;
		queryString.field = "sum";	
		return this;
	}

//	@Override
//	public LilDoopQueryString avg(String columnName) {
//		queryString.functionColumn = columnName;
//		queryString.field = "avg";
//		return this;
//	}

	@Override
	public void where(String columnName, ConditionOperator operation, String value) {
		queryString.conditionColumn = columnName;
		queryString.condition = operation;
		queryString.conditionValue = value;
		//return this;
	}

	@Override
	public LilDoopQueryString from(String Table) {
		queryString.data = Table;
		return this;
	}

	public QueryObject getObjectJson()
	{
		return queryString;
	}
	public String GenerateJson()
	{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(queryString);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	public static void example()
	{
		//this is how you create a query object
		LilDoopQueryString query = new LilDoopQueryString();
		query.count("name").from("person").where("name", ConditionOperator.EQUAL, "kelvin");
		// now you need to put this query in start() in FileClient class
		// it will send your query to master
	}
}
