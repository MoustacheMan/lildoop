package lildoop.mapReduce.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import lildoop.mapReduce.client.LilDoopQueryString;
import lildoop.mapReduce.client.Messenger;
import lildoop.mapReduce.service.Worker;

public class MapReduceClient {
	
	private String masterIP;
	
	public MapReduceClient(String masterIP) {
		this.masterIP = masterIP;
		Thread thread = new Thread(new Worker(masterIP));
		thread.start();
	}
	/*
	 * this is for send the job to master
	 * POST
	 */
	public boolean start(LilDoopQueryString query) throws MalformedURLException, IOException
	{
		String json = query.GenerateJson();
		String url = (masterIP.endsWith("/")) ? "" : "/";
		url += "LilDoop/restful/mapReduce/start";
		HttpURLConnection connection = Messenger.postJSONToAddress(masterIP, url, json);
		int statusCode = connection.getResponseCode();
		connection.disconnect();
		return (statusCode == HttpURLConnection.HTTP_ACCEPTED);
	}
	/*
	 * this will check and see if the status is complete
	 */
	public boolean isProcessingFinished() throws MalformedURLException, IOException
	{
		String url = (masterIP.endsWith("/")) ? "" : "/";
		url += "LilDoop/restful/mapReduce/status";
		HttpURLConnection connection = Messenger.requestJSONFromAddress(masterIP, url);
		int statusCode = connection.getResponseCode();
		//304 - not modify
		//200 - ok
		connection.disconnect();
		return (statusCode != HttpURLConnection.HTTP_NOT_MODIFIED);
	}

	public String getResult() throws MalformedURLException, IOException
	{
		String url = (masterIP.endsWith("/")) ? "" : "/";
		url += "LilDoop/restful/mapReduce/start";
		HttpURLConnection connection = Messenger.requestJSONFromAddress(masterIP, url);
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String json = br.readLine();
		connection.disconnect();
		return json;
	}
}
