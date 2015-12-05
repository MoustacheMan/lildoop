package mapReduce.interfaces;

import java.util.List;
import java.util.Map;

public interface Reducer<K,V> {
	public Map<K,V> reduce(Map<K,List<V>> mappedData);
}
