package mapReduce.concrete;

import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fileStorage.interfaces.FileWorker;
import mapReduce.interfaces.Mapper;
import mapReduce.interfaces.Reducer;
import mapReduce.interfaces.WorkerNode;

public class Dispatcher<K,V,T> {
	
	private Mapper<K,V,T> mapper;
	
	private Map<K,List<V>> mappedData;
	
	private Reducer<K,V> reducer;
	
	private Map<K, V> reducedData;
	
	private T[] data;
	
	public Dispatcher(Mapper<K,V,T> mapper, Reducer<K,V> reducer, T[] data) {
		this.mapper = mapper;
		this.reducer = reducer;
		this.data = data;
		
		mappedData = new HashMap<K,List<V>>();
		reducedData = new HashMap<K,V>();
	}
	
	public T[] getNextDataSetToMap() {
		return null;
	}
	
	public Mapper<K,V,T> getMapper() { return mapper; }
	
	public Reducer<K,V> getReducer() { return reducer; }
	
	public void addMappedData(List<Map<K,List<V>>> newMappedData) {
		//put new mapped data into mappedData
	}
	
	public void addReducedData(Map<K, V> newReducedData) {
		//put new reduced data into reducedData
	}
	
	
}
