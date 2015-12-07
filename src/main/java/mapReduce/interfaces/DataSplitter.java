package mapReduce.interfaces;

import java.io.File;

public interface DataSplitter<T> {
	
	public T[] splitData(String data);

}
