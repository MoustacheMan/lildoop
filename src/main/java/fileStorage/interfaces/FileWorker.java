package fileStorage.interfaces;

import java.io.File;

public interface FileWorker {

	public int storeData(File file);
	public File retrieveData(int id);
	public File retrieveData(String fileName);
	public void delete(int id);
}
