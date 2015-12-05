package mapReduce.interfaces;

import java.util.List;
import java.util.Map;

public interface Mapper<K,V,T> {
	
	public Map<K,List<V>> map(T[] data);

}
