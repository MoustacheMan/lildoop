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
	public void count(String columnName) {
		queryString.columnName = columnName;
		queryString.field = "count";
	}

	@Override
	public void sum(String columnName) {
		queryString.columnName = columnName;
		queryString.field = "sum";	
	}

	@Override
	public void avg(String columnName) {
		queryString.columnName = columnName;
		queryString.field = "avg";	
	}

	@Override
	public void where(String columnName, String operation, Condition value) {
		queryString.Condition =value;
		queryString.field = "count";
	}

	@Override
	public void from(String Table) {
		queryString.table = Table;	
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
}
