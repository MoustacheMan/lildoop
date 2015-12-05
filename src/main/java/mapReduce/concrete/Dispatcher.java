package mapReduce.concrete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapReduce.interfaces.Mapper;
import mapReduce.interfaces.Reducer;

public class Dispatcher<K,V,T> {
	
	private Mapper<K,V,T> mapper;
	
	private Map<K,List<V>> mappedData;
	
	private Reducer<K,V> reducer;
	
	private Map<K, V> reducedData;
	
	
	private int currentDataIndex;
	private T[] data;
	
	public Dispatcher(Mapper<K,V,T> mapper, Reducer<K,V> reducer, T[] data) {
		this.mapper = mapper;
		this.reducer = reducer;
		this.data = data;
		
		currentDataIndex = 0;
		
		mappedData = new HashMap<K,List<V>>();
		reducedData = new HashMap<K,V>();
	}
	
	public List<T> getNextDataSetToMap(int amountOfData) {
		int endIndex = currentDataIndex + amountOfData;
		if(endIndex > data.length) {
			endIndex = data.length;
		}
		List<T> subArray = new ArrayList<T>();
		for(;currentDataIndex < endIndex; currentDataIndex++) {
			subArray.add(data[currentDataIndex]);
		}
		return subArray;
	}
	
	public Map<K, List<V>> getNextMappedData(int numKeys) {
		return null;
	}
	
	public void addMappedData(Map<K,List<V>> newMappedData) {
		//put new mapped data into mappedData
		for(K key : newMappedData.keySet()) {
			if(mappedData.containsKey(key)) {
				mappedData.get(key).addAll(newMappedData.get(key));
			} else {
				mappedData.put(key, newMappedData.get(key));
			}
		}
	}
	
	public void addReducedData(Map<K, V> newReducedData) {
		//put new reduced data into reducedData
		for(K key : newReducedData.keySet()) {
			if(reducedData.containsKey(key)) {
				throw new IllegalArgumentException("New data contains key that has already been reduced");
			} else {
				reducedData.put(key, newReducedData.get(key));
			}
		}
	}
	
	public Mapper<K,V,T> getMapper() { return mapper; }
	
	public Map<K, List<V>> getMappedData() { return mappedData; }
	
	public Reducer<K,V> getReducer() { return reducer; }
	
	public Map<K,V> getReducedData() { return reducedData; }
	
	public boolean hasDataToMap() {
		return currentDataIndex < data.length;
	}
	
	
}
