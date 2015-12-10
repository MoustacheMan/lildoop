package lildoop.fileStorage.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lildoop.fileStorage.enums.RequestType;

public class Messenger {
	
	public static HttpURLConnection createConnection(String url, RequestType type, String contentType)
			throws MalformedURLException, IOException {

		HttpURLConnection connection = null;
		String queryString = "";
		
		queryString = queryString.substring(0, queryString.length()-1);

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
