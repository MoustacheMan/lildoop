package lildoop.fileStorage.interfaces;

public interface FileWorker {

	public long storeData(String fileName, String content);
	public String retrieveData(long id);
	public String retrieveData(String fileName);
	public void delete(long id);
	public void delete(String fileName);
}
