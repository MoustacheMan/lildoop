package fileStorage.concrete;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FileClient {

	private String masterIP;
	
	public FileClient(String masterIP) {
		
		this.masterIP = masterIP;
	}
	
	public int storeFile(File file) {
		
		
		// store file
		// return file id
		
		return 0;
	}
	
	public void sendDummyText(String text) {
		
		String fileName = "test";
		String charset = "UTF-8";
		
		try {
		String query = String.format("fileName=%s&fileContent=%s",
				URLEncoder.encode(fileName, charset),
				URLEncoder.encode(text, charset));
		
		URLConnection connection = new URL(this.masterIP+"/LilDoop/rest/message/post").openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type", "application/x-www.form-urlencoded);charset="+charset);
		
		try(OutputStream output = connection.getOutputStream()) {
			
			output.write(query.getBytes(charset));
		}
		
		InputStream response = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(response));
		StringBuilder out = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null) {
			
			out.append(line);
		}
		
		System.out.println(out);
		} catch(Exception e) {
			
		}
		
		
	}
}
