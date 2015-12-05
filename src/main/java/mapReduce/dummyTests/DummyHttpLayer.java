package mapReduce.dummyTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mapReduce.concrete.Dispatcher;
import mapReduce.interfaces.Mapper;
import mapReduce.interfaces.Reducer;

public class DummyHttpLayer {
	
	private static final String[] DUMMY_WORDS = new String[] {
			"hello", "hi", "yes", "no", "howdey", "maybe", "believe", "to", "and", "the",
			"a", "alive", "arm", "chair", "cherry", "cowboy", "headache", "arch", "wing",
			"monsoon", "burger", "fish", "pole", "ground", "soil", "bird", "fly", "frog",
			"turn", "UFO", "alien", "plasma", "laser", "lead", "rifle", "shotgun", "nest"
	};
	
	private Map<String, Integer> originalWordCount;
	
	public DummyHttpLayer() {
		originalWordCount = new HashMap<String, Integer>();
	}
	
	public void dummyRun() {
		//Start map reduce
		Dispatcher<String, Integer, String> dispatcher = startDummyMapReduce();
		//Run mapper on this machine
		while(dispatcher.hasDataToMap()) {
			Map<String, List<Integer>> mappedData = dispatcher.getMapper().map(dispatcher.getNextDataSetToMap(50));
			dispatcher.addMappedData(mappedData);
		}
		Map<String, List<Integer>> mappedData = dispatcher.getMappedData();
		//Run reducer on this machine
		Map<String, Integer> reducedData = dispatcher.getReducer().reduce(mappedData);
		//Print out values
		for(String s : reducedData.keySet()) {
			String correct = (originalWordCount.get(s).equals(reducedData.get(s))) ? "Correct!" : "WRONG!!!";
			System.out.println(s + ": " + reducedData.get(s) + " (" + correct + ")");
		}
	}
	
	private Dispatcher<String, Integer, String> startDummyMapReduce() {
		//Make Split up Data
		List<String> splitUpData = randomlyRepeat(DUMMY_WORDS);
		String[] arrayData = new String[splitUpData.size()];
		arrayData = splitUpData.toArray(arrayData);
		//Make custom Mapper
		Mapper<String, Integer, String> mapper = new DummyMapper();
		//Make custom Reducer
		Reducer<String, Integer> reducer = new DummyReducer();
		//Make dispatcher
		Dispatcher<String, Integer, String> dispatcher = new Dispatcher<String, Integer, String>(mapper, reducer, arrayData);
		return dispatcher;
	}
	
	private List<String> randomlyRepeat(String[] words) {
		Random rand = new Random();
		
		List<String> repeatedWords = new ArrayList<String>();
		
		for(String word : words) {
			int numRepeats = rand.nextInt(100);
			originalWordCount.put(word, numRepeats);
			for(int i = 0; i < numRepeats; i++) {
				repeatedWords.add(word);
			}
		}
		return repeatedWords;
	}

}
