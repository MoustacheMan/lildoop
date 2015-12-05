package mapReduce.dummyTests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapReduce.interfaces.Reducer;

public class DummyReducer implements Reducer<String, Integer> {

	@Override
	public Map<String, Integer> reduce(Map<String, List<Integer>> mappedData) {
		Map<String, Integer> reduced = new HashMap<String, Integer>();
		
		for(String s : mappedData.keySet()) {
			reduced.put(s, mappedData.get(s).size());
		}
		return reduced;
	}

}
