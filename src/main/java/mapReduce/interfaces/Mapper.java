package mapReduce.interfaces;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Mapper<K,V,T> {
	
	public Map<K,List<V>> map(List<String> list);

}
