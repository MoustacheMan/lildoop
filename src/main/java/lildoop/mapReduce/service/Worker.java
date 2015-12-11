package lildoop.mapReduce.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONObject;

import lildoop.mapReduce.client.Messenger;
import lildoop.mapReduce.enums.ConditionOperator;
import lildoop.mapReduce.enums.Function;
import lildoop.mapReduce.models.Query;
import lildoop.mapReduce.models.QueryResult;

public class Worker implements Runnable {
	
	private String masterIP;
	private boolean keepRunning;
	
	private static final long WAIT_TIME = 5000;
	
	public Worker(String masterIP) {
		this.masterIP = masterIP;
		keepRunning = false;
	}

	@Override
	public void run() {
		keepRunning = true;
		while(keepRunning) {
			//Ask for work
			try {
//				System.out.println(masterIP + "/LilDoop/restful/mapReduce/status");
				HttpURLConnection con = Messenger.requestJSONFromAddress(masterIP, "/LilDoop/restful/mapReduce/status");
				int code = con.getResponseCode();
				con.disconnect();
//				System.out.println("Response Code: " + code);
				//if got work
				if(code == HttpURLConnection.HTTP_NOT_MODIFIED) {
//					System.out.println(masterIP + "/LilDoop/restful/mapReduce/work");
					con = Messenger.requestJSONFromAddress(masterIP, "/LilDoop/restful/mapReduce/work");
					//get query
					Query query = getQueryFromStream(con.getInputStream());
					//do query
					QueryResult result = doQuery(query);
					con.disconnect();
					//return list of result objects
					con = Messenger.postJSONToAddress(masterIP, "/LilDoop/restful/mapReduce/result", result.getJSON());
					code = con.getResponseCode();
					con.disconnect();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private QueryResult doQuery(Query query) {
		//Function
		Function func = query.getFunc();
		//Get data
		String[][] data = getData(new JSONArray(query.getFileName()), query.getFuncColumn(), query.getConditionColumn());
		//Get condition
		ConditionOperator cond = query.getCondition();
		String param = query.getConditionValue();
		//return result of function
		return func.doFunction(data, cond, param);
	}
	
	private String[][] getData(JSONArray fileData, String functionColumn, String conditionColumn) {
		String[][] data = new String[fileData.length()][2];
		
		for(int i = 0; i < fileData.length(); i++) {
			JSONObject obj = fileData.getJSONObject(i);
			data[i][0] = obj.getString(functionColumn);
			data[i][1] = obj.optString(conditionColumn);
		}
		
		return data;
	}
	
	private Query getQueryFromStream(InputStream stream) {
		Query newQuery = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			String json = reader.readLine();
			newQuery = new Query(new JSONObject(json), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newQuery;
	}
	
	public void stop() {
		keepRunning = false;
	}

}
