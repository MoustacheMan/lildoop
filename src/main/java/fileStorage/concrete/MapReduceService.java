package fileStorage.concrete;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import mapReduce.concrete.Dispatcher;

@Path("/mapReduce")
public class MapReduceService {
	
	private Dispatcher currentDispatcher;
	
	
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
