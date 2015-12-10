package fileStorage.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;

import fileStorage.enums.RequestType;

public class FileClient {

	private String masterIP;
	private String charset = "utf-8";

	public FileClient(String masterIP) {

		this.masterIP = masterIP;
	}
	
	private HttpURLConnection createConnection(String url, RequestType type, Map<String, String> params)
			throws MalformedURLException, IOException {

		HttpURLConnection connection = null;
		String queryString = "";
		Object[] paramsArray = new Object[params.size()];

		int index = 0;
		for (Map.Entry<String, String> entry : params.entrySet()) {

			queryString += entry.getKey() + "=%s&";
			paramsArray[index] = URLEncoder.encode(entry.getValue(), charset);
			index++;
		}
		
		queryString = queryString.substring(0, queryString.length()-1);

		String query = String.format(queryString, paramsArray);

		connection = (HttpURLConnection) new URL(this.masterIP + "" + url + "?" + query).openConnection();
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

		connection.setRequestProperty("Content-type", "text/plain");
		connection.setRequestProperty("Accept-Charset", charset);

		return connection;

	}

	public void join(String ip) throws MalformedURLException, IOException {
		
		String out = "";

		Hashtable<String, String> paramsTable = new Hashtable<String, String>();
		paramsTable.put("ip", ip);


		HttpURLConnection connection = this.createConnection("/LilDoop/rest/file/join", RequestType.POST,
				paramsTable);

		out = this.readResponse(connection);
		connection.disconnect();
	}
	
	public long storeFile(String fileName, String text) throws MalformedURLException, IOException {

		String out = "";

			Hashtable<String, String> paramsTable = new Hashtable<String, String>();
			paramsTable.put("fileName", fileName);
			paramsTable.put("fileContent", text);

			HttpURLConnection connection = this.createConnection("/LilDoop/rest/file/save", RequestType.POST,
					paramsTable);

			out = this.readResponse(connection);
			connection.disconnect();
			
		return Long.parseLong(out.toString());
	}
	
	public String retrieveFile(String file) throws MalformedURLException, IOException {

		String out = "";

			Hashtable<String, String> paramsTable = new Hashtable<String, String>();
			paramsTable.put("file", file);

			HttpURLConnection connection = this.createConnection("/LilDoop/rest/file/get", RequestType.POST,
					paramsTable);

			connection.disconnect();

			out = this.readResponse(connection);


		return out.toString();
	}
	
	public String deleteFile(String file) throws MalformedURLException, IOException {

		String out = "";

			Hashtable<String, String> paramsTable = new Hashtable<String, String>();
			paramsTable.put("file", file);

			HttpURLConnection connection = this.createConnection("/LilDoop/rest/file/delete", RequestType.POST,
					paramsTable);

			connection.disconnect();

			out = this.readResponse(connection);


		return out.toString();
	}

	private String readResponse(HttpURLConnection connection) throws IOException {

		String out = "";

		InputStream response = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(response));

		String line;
		while ((line = reader.readLine()) != null) {

			out += line;
		}

		return out;
	}
}