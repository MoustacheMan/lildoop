package lildoop.fileStorage.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileWorker {

	public long storeData(String fileName, String content, long id);
	public String retrieveData(long id) throws Exception;
	public String retrieveData(String fileName);
	public void delete(long id);
	public void delete(String fileName);
}
