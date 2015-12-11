package lildoop.mapReduce.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONObject;

import lildoop.fileStorage.client.FileClient;
import lildoop.mapReduce.models.Query;

public class Dispatcher {
	
	private Query currentQuery;
//	private BufferedReader fileStream;
	private String[] fileData;
	private int numSetsSent;
	private int numSetsReceived;
	private int currentIndex;
	private String[] columns;
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
	
	public String getWorkJson(JSONObject[] rows) {
		return currentQuery.getWorkJson(rows);
	}
	
	public void addResults(JSONObject resultJson) {
		numSetsReceived++;
		throw new RuntimeException("Method not yet implemented");
	}
}
