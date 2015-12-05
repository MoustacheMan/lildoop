package mapReduce.dummyTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapReduce.interfaces.Mapper;

public class DummyMapper implements Mapper<String, Integer, String> {

	@Override
	public Map<String, List<Integer>> map(List<String> data) {
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		
		for(String s : data) {
			if(map.containsKey(s)) {
				map.get(s).add(1);
			} else {
				List<Integer> newList = new ArrayList<Integer>();
				newList.add(1);
				map.put(s, newList);
			}
		}
		return map;
	}

}
