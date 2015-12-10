package lildoop.fileStorage.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lildoop.fileStorage.enums.RequestType;

public class Messenger {
	
	public static HttpURLConnection createJsonConnection(String url, RequestType type, String json, String contentType)
			throws MalformedURLException, IOException {

		HttpURLConnection connection = null;
		String queryString = "";
		
		queryString = queryString.substring(0, queryString.length()-1);
		
		ObjectOutputStream outputStream = new ObjectOutputStream(connection.getOutputStream());
		outputStream.writeObject(json);
		outputStream.flush();

		connection = (HttpURLConnection) new URL(url).openConnection();
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

		connection.setRequestProperty("Content-type", contentType);

		return connection;
	}

}
