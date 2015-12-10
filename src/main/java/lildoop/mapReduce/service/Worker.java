package lildoop.mapReduce.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import lildoop.fileStorage.enums.RequestType;

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
				HttpURLConnection con = createConnection("/rest/mapReduce/getData", RequestType.GET);
				//if got work
				if(con.getResponseCode())
					//get query
					//do query
					//return list of result objects
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private HttpURLConnection createConnection(String url, RequestType type)
			throws MalformedURLException, IOException {

		HttpURLConnection connection = null;
		String queryString = "";
		
		queryString = queryString.substring(0, queryString.length()-1);

		connection = (HttpURLConnection) new URL(this.masterIP + "" + url).openConnection();
		connection.setDoOutput(true);

		switch (type) {
		case POST:
			connection.setRequestMethod("POST");
			break;
		case GET:
			connection.setRequestMethod("GET");
			break;
		default:
			break;
		}

		connection.setRequestProperty("Content-type", "application/json");

		return connection;
	}

}
