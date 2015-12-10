package lildoop.mapReduce.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import lildoop.fileStorage.enums.RequestType;
import lildoop.fileStorage.service.Messenger;

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
				HttpURLConnection con = Messenger.createConnection(masterIP + "/rest/mapReduce/getData", RequestType.GET, "application/json");
				//if got work
//				if(con.getResponseCode())
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

}
