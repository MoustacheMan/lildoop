package fileStorage.concrete;

import java.util.ArrayList;

public class FileMaster {
	
	static ArrayList<FileNode> nodes = new ArrayList<FileNode>();
	
	public FileMaster(){
		FileNode f = new FileNode(null);
		System.out.println("Created");
		addNode(f);
	}
	
	public void addNode(FileNode toAdd){
		nodes.add(toAdd);
	}
	
	public long storeData(String fileName, String content) {
		long l = -1;
		for(FileNode n : nodes){
			l = n.storeData(fileName, content);
		}
		return l;
	}

	public String retrieveData(long l) {
		for(FileNode n : nodes){
			return n.retrieveData(l);
		}
		return null;
	}

	public String retrieveData(String fileName) {
		for(FileNode n : nodes){
			return n.retrieveData(fileName);
		}
		return null;
	}

	public void delete(long l) {
		for(FileNode n : nodes){
			n.delete(l);
		}
	}
	
	public void delete(String fileName) {
		for(FileNode n : nodes){
			n.delete(fileName);
		}
	}
}