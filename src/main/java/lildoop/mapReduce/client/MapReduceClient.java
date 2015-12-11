package lildoop.mapReduce.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import lildoop.mapReduce.service.Worker;

public class MapReduceClient {
	
	private String address;
	
	public MapReduceClient(String address) {
		this.address = address;
	}
	
	public void registerAsWorker() {
		Thread workerThread = new Thread(new Worker(address));
		workerThread.start();
	}
	
	public void sendFile(String filePath, String fileName) {
		String file = convertFileToString(filePath);
		String json = "{ \"fileName\": \"" + fileName + "\", \"fileContent\": \"" + file + "\"}";
		String url = (address.endsWith("/")) ? "" : "/";
		url += "LilDoop/restful/mapReduce/save";
		try {
			HttpURLConnection con = Messenger.postJSONToAddress(address, url, json);
			con.getResponseCode();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String convertFileToString(String filePath) {
		StringBuilder file = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				file.append(line);
				file.append("--");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.substring(0, file.length() - 2);
	}
	
	public boolean start(LilDoopQueryString query) throws MalformedURLException, IOException
	{
		String json = query.GenerateJson();
		String url = (address.endsWith("/")) ? "" : "/";
		url += "LilDoop/restful/mapReduce/start";
		HttpURLConnection connection = Messenger.postJSONToAddress(address, url, json);
		int statusCode = connection.getResponseCode();
		connection.disconnect();
		return (statusCode == HttpURLConnection.HTTP_ACCEPTED);
	}
	
	
	public boolean isProcessingFinished() throws MalformedURLException, IOException
	{
		String url = (address.endsWith("/")) ? "" : "/";
		url += "LilDoop/restful/mapReduce/status";
		HttpURLConnection connection = Messenger.requestJSONFromAddress(address, url);
		int statusCode = connection.getResponseCode();
		connection.disconnect();
		return (statusCode != HttpURLConnection.HTTP_NOT_MODIFIED && statusCode != HttpURLConnection.HTTP_NO_CONTENT);
	}

	public String getResult() throws MalformedURLException, IOException
	{
		String url = (address.endsWith("/")) ? "" : "/";
		url += "LilDoop/restful/mapReduce/result";
		HttpURLConnection connection = Messenger.requestJSONFromAddress(address, url);
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String json = br.readLine();
		connection.disconnect();
		return json;
	}
}
