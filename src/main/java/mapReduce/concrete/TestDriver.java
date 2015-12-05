package mapReduce.concrete;

import mapReduce.dummyTests.DummyHttpLayer;

public class TestDriver {
	
	public static void main(String[] args) {
		DummyHttpLayer dummy = new DummyHttpLayer();
		dummy.dummyRun();
	}

}
