package fileStorage.concrete;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import mapReduce.interfaces.Mapper;
import mapReduce.interfaces.Reducer;

@Path("/mapReduce")
public class MapReduceService {
	
	public static <K,V,T> void startMapReduce(Mapper<K,V,T> mapper, Reducer<K,V> reducer, String fileDataPath, String lilDoopAddress) {
		
	}
	
	@Path("/test")
	@GET
	public String test() {
		return "test";
	}

}
