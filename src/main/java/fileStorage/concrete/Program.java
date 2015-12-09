package fileStorage.concrete;

public class Program {

	public static void main(String args[]) {
		
		FileClient client = new FileClient("");
		
		client.sendDummyText("test");
	}
}
