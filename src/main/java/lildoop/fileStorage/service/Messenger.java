package lildoop.fileStorage.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Messenger {
	
	private static final String MASTER_ADDRESS = "http://localhost:8080/LilDoop/restful";
	
	public static HttpURLConnection postJSON(String url, String json)
			throws MalformedURLException, IOException {

		HttpURLConnection connection = (HttpURLConnection) new URL(MASTER_ADDRESS + url).openConnection();
		connection.setDoOutput(true);

		connection.setRequestProperty("Content-type", "application/json");
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		connection.setRequestMethod("POST");
		
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(json.getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
		

		return connection;
	}
	
	public static HttpURLConnection requestJSON(String url) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(MASTER_ADDRESS + url).openConnection();
		connection.setRequestProperty("Content-type", "text/plain");
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		connection.setRequestMethod("GET");
		
		connection.connect();
		
		return connection;
	}

}
