package fileStorage.concrete;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import mapReduce.concrete.Dispatcher;
import mapReduce.interfaces.Mapper;
import mapReduce.interfaces.Reducer;

@Path("/mapReduce")
public class MapReduceService {
	
	private Dispatcher currentDispatcher;
	
	private <K,V,T> void startMapReduce(Mapper<K,V,T> mapper, Reducer<K,V> reducer, String fileDataPath, String lilDoopAddress) {
		
	}
	
	@Path("/status")
	@GET
	public Response getStatus() {
		return null;
	}
	
	@Path("/start")
	@Consumes("application/json")
	@POST
	public Response startMapReduce() {
		return null;
	}
	
	@Path("/getWork")
	@POST
	public Response getNextWork() {
		return null;
	}
	
	@Path("/addMapResult")
	@POST
	public Response addMapResult() {
		return null;
	}
	
	@Path("/addReduceResult")
	@POST
	public Response addReduceResult() {
		return null;
	}
	
	

}
