package lildoop.mapReduce.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONObject;

import lildoop.fileStorage.enums.RequestType;
import lildoop.fileStorage.service.Messenger;
import lildoop.mapReduce.models.Query;

public class Worker implements Runnable {
	

	private String masterIP;
	private boolean keepRunning;
	
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
				HttpURLConnection con = Messenger.requestJSON("/mapReduce/getData");
				//if got work
				if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					//get query
					Query query = getQueryFromStream(con.getInputStream());
					//do query
					//return list of result objects
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
