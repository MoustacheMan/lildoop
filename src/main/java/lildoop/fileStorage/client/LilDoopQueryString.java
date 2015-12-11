package lildoop.fileStorage.client;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class LilDoopQueryString implements IQuery {
	QueryObject queryString ;
	public LilDoopQueryString() {
		queryString = new QueryObject();
	}
	@Override
	public LilDoopQueryString count(String columnName) {
		queryString.columnName = columnName;
		queryString.field = "count";
		return this;
	}

	@Override
	public LilDoopQueryString sum(String columnName) {
		queryString.columnName = columnName;
		queryString.field = "sum";	
		return this;
	}

	@Override
	public LilDoopQueryString avg(String columnName) {
		queryString.columnName = columnName;
		queryString.field = "avg";
		return this;
	}

	@Override
	public LilDoopQueryString where(String columnName, Condition operation, String value) {
		queryString.Condition =operation;
		queryString.conditionData = value;
		return this;
	}

	@Override
	public LilDoopQueryString from(String Table) {
		queryString.table = Table;
		return this;
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
		LilDoopQueryString query = new LilDoopQueryString();
		String json = query.count("name").from("person").where("name", Condition.Equal, "kelvin").GenerateJson();
		
		
	}
}
