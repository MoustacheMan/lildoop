package lildoop.mapReduce.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import lildoop.fileStorage.enums.RequestType;
import lildoop.fileStorage.service.Messenger;
import lildoop.mapReduce.enums.ConditionOperator;
import lildoop.mapReduce.enums.Function;
import lildoop.mapReduce.models.Query;
import lildoop.mapReduce.models.QueryResult;

public class Worker implements Runnable {
	
	private String masterIP;
	private boolean keepRunning;
	
	private static final long WAIT_TIME = 10000;
	
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
				HttpURLConnection con = Messenger.requestJSONFromAddress(masterIP, "/restful/mapReduce/work");
				//if got work
				if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					//get query
					Query query = getQueryFromStream(con.getInputStream());
					//do query
					QueryResult result = doQuery(query);
					//return list of result objects
					Messenger.postJSONToAddress(masterIP, "/restful/mapReduce/result", result.getJSON());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private QueryResult doQuery(Query query) {
		//Function
		Function func = query.getFunc();
		//Get data
		String[][] data = getData(new JSONArray(query.getFileName()), "query.getFunctionColumn()", "query.getConditionColumn()");
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
			data[i][1] = obj.getString(conditionColumn);
		}
		
		return null;
	}
	
	private Query getQueryFromStream(InputStream stream) {
		Query newQuery = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			String json = reader.readLine();
			newQuery = new Query(new JSONObject(json));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newQuery;
	}

}
