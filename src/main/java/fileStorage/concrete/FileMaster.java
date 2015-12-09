package fileStorage.concrete;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import fileStorage.client.FileClient;

public class FileMaster {
	
	static ArrayList<String> ips = new ArrayList<String>();
	FileNode local = new FileNode(null);
	
	public FileMaster(){
		System.out.println("Created");
		addNode(null);
	}
	
	public void addNode(String value){
		ips.add(value);
	}
	
	public long storeData(String fileName, String content) throws MalformedURLException, IOException {
		long l = -1;
		for(String n : ips){
			if(n!=null){
			FileClient temp = new FileClient(n);
			l = temp.storeFile(fileName, content);
			}else{
				local.storeData(fileName, content);
			}
		}
		return l;
	}

	public String retrieveData(long l) throws MalformedURLException, IOException {
		for(String n : ips){
			if(n!=null){
			FileClient temp = new FileClient(n);
			return temp.retrieveFile(""+l);
			}else{
				local.retrieveData(l);
			}
		}
		return null;
	}

	public String retrieveData(String fileName) throws MalformedURLException, IOException {
		for(String n : ips){
			if(n!=null){
			FileClient temp = new FileClient(n);
			return temp.retrieveFile(fileName);
			}else{
				local.retrieveData(fileName);
			}
		}
		return null;
	}

	public void delete(long l) throws MalformedURLException, IOException {
		for(String n : ips){
			if(n!=null){
			FileClient temp = new FileClient(n);
			temp.deleteFile(""+l);
			}else{
				local.delete(l);
			}
		}
	}
	
	public void delete(String fileName) throws MalformedURLException, IOException {
		for(String n : ips){
			if(n!=null){
			FileClient temp = new FileClient(n);
			temp.deleteFile(fileName);
			}else{
				local.delete(fileName);
			}
		}
	}
}