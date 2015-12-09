package fileStorage.client;

public class Program {

	public static void main(String args[]) {
		
		FileClient client = new FileClient("http://169.254.246.53:8080");
		
		try {
//		long id = client.storeFile("christian.txt", "Hey cuties christian is here");
		
//		System.out.println(id);
//		String fileContent = client.retrieveFile(id+"");
//		String fileContent2 = client.retrieveFile("christian.txt");
//		System.out.println(fileContent);
//		System.out.println(fileContent2);
//		System.out.println(id);
			
//		client.deleteFile("1449546629084");
		
		client.join("169.254.125.145");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}