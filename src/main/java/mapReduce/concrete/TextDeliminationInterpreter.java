package mapReduce.concrete;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import mapReduce.interfaces.DataSplitter;

public class TextDeliminationInterpreter implements DataSplitter<String> {

	@Override
	public String[] splitData(String data) {
		String[] returnData = null;
		try(Scanner scanner = new Scanner(new FileInputStream(data));) {
			returnData = scanner.nextLine().split(",");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnData;
	}
}
