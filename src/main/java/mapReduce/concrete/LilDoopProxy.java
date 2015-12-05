package mapReduce.concrete;

import java.io.File;
import java.util.Map;

import mapReduce.interfaces.Mapper;
import mapReduce.interfaces.Reducer;

public class LilDoopProxy<K,V,T> {
	
	private Mapper<K,V,T> mapper;
	
	private Reducer<K,V> reducer;
	
	private String fileDataPath;
	
	private String lilDoopAddress;
	
	public LilDoopProxy(Mapper<K,V,T> mapper, Reducer<K,V> reducer, String fileDataPath, String lilDoopAddress) {
		this.mapper = mapper;
		this.reducer = reducer;
		this.fileDataPath = fileDataPath;
		this.lilDoopAddress = lilDoopAddress;
	}
	
	public void sendMapReduce() {
		//Create HTTPRequest
		//prepare to send mapper class
		//prepare to send reducer class
		//prepare to send data
		//send to lilDoopAddress
	}
	
	public boolean isProcessingDone() {
		return false;
	}
	
	public Map<K,V> getData() {
		return null;
	}

}
