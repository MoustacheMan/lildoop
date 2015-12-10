package lildoop.fileStorage.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import lildoop.fileStorage.client.FileClient;

public class FileMaster {
	
	static ArrayList<String> ips = new ArrayList<String>();
	long id = 0;
	FileNode local = new FileNode(null);
	int serverLocation = 0;
	
	public FileMaster(){
		System.out.println("Created");
		addNode(null);
	}
	
	public void addNode(String value){
		ips.add(value);
	}
	
	public long storeData(String fileName, String content) throws MalformedURLException, IOException {
		long l = -1;
		id++;
		for(int i = 0; i<2; i++){
			if(serverLocation>=ips.size())
				serverLocation=0;
			if(ips.get(serverLocation)!=null){
			FileClient temp = new FileClient(ips.get(serverLocation));
			l = temp.storeFile(fileName, content);
			}else{
				l = local.storeData(fileName, content, id);
			}
			serverLocation++;
		}
		return id;
	}

	public String retrieveData(long l) throws Exception {
		for(String n : ips){
			if(n!=null){
			FileClient temp = new FileClient(n);
			return temp.retrieveFile(""+l);
			}else{
				return local.retrieveData(l);
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
				return local.retrieveData(fileName);
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