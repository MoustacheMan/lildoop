package lildoop.mapReduce.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.json.JSONObject;

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
//	private FileClient fileClient;
	
	public Dispatcher(Query query) {
		this.currentQuery = query;
//		this.fileStream = null;
		this.numSetsSent = 0;
		this.numSetsReceived = 0;
//		this.fileClient = fileClient;
		this.currentIndex = 0;
		this.columns = null;
		this.totalResult = null;
		readFile();
	}
	
	private void readFile() {
		String file = null;
//		try {
//			file = fileClient.retrieveFile(currentQuery.getFileName());
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		file = convertFileToString(currentQuery.getFileName());
		
		this.fileData = file.split("\n");
		columns = fileData[currentIndex++].split(",");
	}
	
	public String convertFileToString(String filePath) {
		StringBuilder file = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				file.append(line);
				file.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.substring(0, file.length() - 2);
	}

	public JSONObject[] getNextWorkSet() {
		int amount = 20;
		int count = 0;
		int endIndex = currentIndex + amount;
		if (endIndex >= fileData.length) {
			endIndex = fileData.length;
		}
		JSONObject[] workData = new JSONObject[endIndex - currentIndex];
		for (int i = currentIndex; i < endIndex; i++) {
			String[] data = fileData[i].split(",");
			JSONObject row = new JSONObject();
			for (int j = 0; j < columns.length; j++) {
				String column = columns[j];
				String val = data[j];
				row.put(column, val);
			}
			workData[count++] = row;
		}
		currentIndex = endIndex;
		numSetsSent++;
		return workData;
	}
	
	public boolean isProccessing() {
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
		int resultVal = result.getValue();
		int totVal = totalResult.getValue();
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
		totalResult.updateValue(totVal);
	}
}
