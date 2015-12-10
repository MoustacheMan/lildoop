package fileStorage.concrete;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import fileStorage.interfaces.FileWorker;

public class FileNode implements FileWorker{
	
	 HashMap<Long, String> hm = new HashMap<Long, String>();
	 String ip = null;
	 String hostFile = "C:\\LilDoopFS\\";
	public FileNode(String value) {
		ip = value;
	}

	@Override
	public long storeData(String fileName, String content) {
		new File("C:\\LilDoopFS\\").mkdirs();
		try {
			File file = new File("C:\\LilDoopFS\\"+fileName);
			BufferedWriter out;

			out = new BufferedWriter(new FileWriter(file));

			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Long id = System.currentTimeMillis();
		hm.put(id, fileName);
		return  id;
	}

	@Override
	public String retrieveData(long l) {
		String sent = "";
		try {
		    BufferedReader in = new BufferedReader(new FileReader(hostFile+hm.get(l)));
		    String str;
		    while ((str = in.readLine()) != null)
		        sent += str;
		    in.close();
		} catch (IOException e) {
		}
		return sent;
	}

	@Override
	public String retrieveData(String fileName) {
		String sent = "";
		try {
		    BufferedReader in = new BufferedReader(new FileReader(hostFile+fileName));
		    String str;
		    while ((str = in.readLine()) != null)
		        sent += str;
		    in.close();
		} catch (IOException e) {
		}
		return sent;
	}
	
	@Override
	public void delete(String fileName) {
		File file = new File(hostFile+fileName);
		file.delete();
	}

	@Override
	public void delete(long id) {
		File file = new File(hostFile+hm.get(id));
		file.delete();
	}

}
