package lildoop.mapReduce.service;

import java.util.stream.Stream;

import org.json.JSONObject;

import lildoop.mapReduce.models.Query;

public class Dispatcher {
	
	private Query currentQuery;
	private Stream fileStream;
	private int numSetsSent;
	private int numSetsReceived;
	private final String resultFileName = "Map_Reduce_Result.txt";
	
	public Dispatcher(Query query) {
		this.currentQuery = query;
		this.fileStream = null;
		this.numSetsSent = 0;
		this.numSetsReceived = 0;
	}
	
	public String[] getNextWorkSet() {
		throw new RuntimeException("Method not yet implemented");
	}
	
	public boolean isProccessing() {
		throw new RuntimeException("Method not yet implemented");
	}
	
	public String getWorkJson(String[] rows) {
		return currentQuery.getWorkJson(rows);
	}
	
	public void addResults(JSONObject resultJson) {
		throw new RuntimeException("Method not yet implemented");
	}
}
