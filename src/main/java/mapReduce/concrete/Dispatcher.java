package mapReduce.concrete;

public class Dispatcher<K,V,T> {
	
//	private Mapper<K,V,T> mapper;
//	
//	private Map<K,List<V>> mappedData;
//	
//	private Reducer<K,V> reducer;
//	
//	private Map<K, V> reducedData;
//	
//	private int currentDataIndex;
//	private boolean isMapping;
//	private boolean stillReducing;
//	private T[] data;
//	private K[] keys;
//	
//	public Dispatcher(Mapper<K,V,T> mapper, Reducer<K,V> reducer, T[] data) {
//		this.mapper = mapper;
//		this.reducer = reducer;
//		this.data = data;
//		this.isMapping = true;
//		this.stillReducing = false;
//		this.keys = null;
//		
//		currentDataIndex = 0;
//		
//		mappedData = new HashMap<K,List<V>>();
//		reducedData = new HashMap<K,V>();
//	}
//	
//	public List<T> getNextDataSetToMap(int amountOfData) {
//		int endIndex = currentDataIndex + amountOfData;
//		if(endIndex > data.length) {
//			endIndex = data.length;
//		}
//		List<T> subArray = new ArrayList<T>();
//		for(;isMapping = currentDataIndex < endIndex; currentDataIndex++) {
//			subArray.add(data[currentDataIndex]);
//		}
//		if (currentDataIndex >= data.length) {
//			currentDataIndex = 0;
//		}
//		return subArray;
//	}
//	
//	public Map<K, List<V>> getNextMappedData(int numKeys) {
//		if (keys == null) {
//			keys = (K[]) mappedData.keySet().toArray();
//		}
//		Map<K,List<V>> toReturn = new HashMap<>();
//		int endIndex = currentDataIndex + numKeys;
//		if(endIndex > data.length) {
//			endIndex = data.length;
//		}
//		for (int i = currentDataIndex; i < endIndex; i++) {
//			K key = keys[i];
//			toReturn.put(key, mappedData.get(keys));
//		}
//		currentDataIndex = endIndex;
//		return toReturn;
//	}
//	
//	public void addMappedData(Map<K,List<V>> newMappedData) {
//		//put new mapped data into mappedData
//		for(K key : newMappedData.keySet()) {
//			if(mappedData.containsKey(key)) {
//				mappedData.get(key).addAll(newMappedData.get(key));
//			} else {
//				mappedData.put(key, newMappedData.get(key));
//			}
//		}
//	}
//	
//	public void addReducedData(Map<K, V> newReducedData) {
//		//put new reduced data into reducedData
//		for(K key : newReducedData.keySet()) {
//			if(reducedData.containsKey(key)) {
//				throw new IllegalArgumentException("New data contains key that has already been reduced");
//			} else {
//				reducedData.put(key, newReducedData.get(key));
//			}
//		}
//	}
//	
//	public Mapper<K,V,T> getMapper() { return mapper; }
//	
//	public Map<K, List<V>> getMappedData() { return mappedData; }
//	
//	public Reducer<K,V> getReducer() { return reducer; }
//	
//	public Map<K,V> getReducedData() { return reducedData; }
//	
//	public boolean hasDataToMap() {
//		return currentDataIndex < data.length;
//	}
//	
//	public boolean isMapping() {
//		return this.isMapping;
//	}
//	
//	// This might be transactional since need this part to finish before continuing (maybe not though cause don't 
//	// necessarily need to rollback)
//	public boolean processingRequest() {
//		return isMapping || stillReducing;
//	}
}
