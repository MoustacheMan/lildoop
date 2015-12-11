package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

import lildoop.mapReduce.client.LilDoopQueryString;
import lildoop.mapReduce.client.MapReduceClient;
import lildoop.mapReduce.enums.ConditionOperator;

public class Runner {

	public static void main(String[] args) {
		String address = "http://localhost:8080";
		MapReduceClient worker1 = new MapReduceClient(address);
		MapReduceClient worker2 = new MapReduceClient(address);
		MapReduceClient worker3 = new MapReduceClient(address);
		MapReduceClient worker4 = new MapReduceClient(address);
		worker1.registerAsWorker();
		worker2.registerAsWorker();
		worker3.registerAsWorker();
		worker4.registerAsWorker();
		
		MapReduceClient client = new MapReduceClient(address);
		LilDoopQueryString queryBuilder = new LilDoopQueryString();
//		queryBuilder.count("firstname").from("C:/Users/David Gonzalez/Documents/~School~/CS/Distributed Systems/ExampleData/data1.txt")
//			.where("firstname", ConditionOperator.EQUAL, "Kelvin");
		// Do a where also, sometime in the future
		queryBuilder.sum("age").from("C:/Users/David Gonzalez/Documents/~School~/CS/Distributed Systems/ExampleData/data1.txt");
//		queryBuilder.avg("age").from("C:/Users/David Gonzalez/Documents/~School~/CS/Distributed Systems/ExampleData/data1.txt");
		try {
			client.start(queryBuilder);
			
			int waitTime = 5000;
			while (!client.isProcessingFinished()) {
				Thread.sleep(waitTime);
			}
			
			String result = client.getResult();
			System.out.println("Result of query: " + result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String readFile(String filePath) {
		StringBuilder file = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
			String line = null;
			while((line = reader.readLine()) != null) {
				file.append(line);
				file.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.substring(0, file.length() - 2);
	}
}
