package lildoop.mapReduce.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONObject;

import lildoop.fileStorage.client.FileClient;
import lildoop.mapReduce.enums.Function;
import lildoop.mapReduce.models.Query;
import lildoop.mapReduce.models.QueryResult;

public class Dispatcher {
	
	private Query currentQuery;
//	private BufferedReader fileStream;
	private String[] fileData;
	private int numSetsSent;
	private int numSetsReceived;
	private int currentIndex;
	private String[] columns;
	private QueryResult totalResult;
//	private final String resultFileName = "Map_Reduce_Result.txt";
	private FileClient fileClient;
	
	public Dispatcher(Query query, FileClient fileClient) {
		this.currentQuery = query;
//		this.fileStream = null;
		this.numSetsSent = 0;
		this.numSetsReceived = 0;
		this.fileClient = fileClient;
		this.currentIndex = 0;
		this.columns = null;
		this.totalResult = null;
		readFile();
	}
	
	private void readFile() {
		String file = null;
		try {
			file = fileClient.retrieveFile(currentQuery.getFileName());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.fileData = file.split("\n");
		columns = fileData[currentIndex++].split(",");
	}

	public JSONObject[] getNextWorkSet() {
		int amount = 20;
		int endIndex = currentIndex + amount;
		if (endIndex > fileData.length) {
			endIndex = fileData.length;
		}
		JSONObject[] workData = new JSONObject[amount];
		for (int i = currentIndex; i < endIndex; i++) {
			String[] data = fileData[i].split(",");
			JSONObject row = new JSONObject();
			for (int j = 0; j < columns.length; j++) {
				String column = columns[j];
				String val = data[j];
				row.put(column, val);
			}
			workData[i] = row;
		}
		currentIndex = endIndex;
		numSetsSent++;
		return workData;
	}
	
	public boolean isProccessing() {
		// may run into race conditions or concurrency problems here
		return (currentIndex < fileData.length) || (numSetsReceived < numSetsSent);
	}
	
	public String getResults() {
		return totalResult.getJSON();
	}
	
	public String getWorkJson(JSONObject[] rows) {
		return currentQuery.getWorkJson(rows);
	}
	
	public void addResults(JSONObject resultJson) {
		if (totalResult == null) {
			totalResult = new QueryResult(resultJson);
		} else {
			QueryResult result = new QueryResult(resultJson);
			aggregateResults(result);
		}
		numSetsReceived++;
	}

	private void aggregateResults(QueryResult result) {
		// Assuming everything is the same just manipulating the vals
		int resultVal = Integer.parseInt(result.getValue());
		int totVal = Integer.parseInt(totalResult.getValue());
		Function func = result.getFunction();
		switch (func) {
			case COUNT:
				totVal += resultVal;
				break;
			case SUM:
				totVal += resultVal;
				break;
			case AVG:
				totVal += resultVal;
				totVal /= 2;
				break;
			default:
				throw new IllegalStateException("Specified function is not valid.");
		}
		totalResult.updateValue(new Integer(totVal).toString());
	}
}
