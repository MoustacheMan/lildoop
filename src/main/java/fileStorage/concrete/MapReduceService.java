package fileStorage.concrete;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import mapReduce.concrete.Dispatcher;

@Path("/mapReduce")
public class MapReduceService {
	
	private Dispatcher currentDispatcher;
	
	@Path("/status")
	@GET
	public Response getStatus() {
		//get status from dispatcher
		return null;
	}
	
	@Path("/start")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response startMapReduce() {
		//Break up JSON if need be (deserialization)
		//Create new dispatcher
//		this.currentDispatcher = new Dispatcher<>(mapper, reducer, data);
		//Create response of 'accepted' or 'ok'
		// Should include some status monitor or some time estimate (HATEOAS thing, send URL they can use to call for status)
		ResponseBuilder responseBuilder = Response.accepted();
		//return response
		return responseBuilder.build();
	}
	
	@Path("/getWork")
	@POST
	@Consumes(MediaType.WILDCARD)
	public Response getNextWork(int workAmount) {
		//[Have to know when were mapping or reducing here]
		ResponseBuilder builder = Response.ok();
		String jobType = null;
//		int workAmount = 0; // need this from request
		Object work = null;
		//When mapping
		if (currentDispatcher.isMapping()) {
			//get next data set to map
			work = currentDispatcher.getNextDataSetToMap(workAmount);
			//set job type to map
			jobType = "map";
		} else {
		//When reducing	
			//get next mapped data set to reduce
			work = currentDispatcher.getNextMappedData(workAmount);
			//set job type to reduce
			jobType = "reduce";
		}	
		
		//form JSON with data and mapper or reducer according to job type
		//create response with job type header and JSON
		builder.header("X-JobType", jobType);
		builder.entity(work);
		//return the response
		return builder.build();
	}
	
	@Path("/getWorkClass")
	@GET
	public Response getWorkClass(String jobType) {
		Object workClass = null;
		if (jobType.equals("map")) {
			workClass = currentDispatcher.getMapper();
		} else if (jobType.equals("reduce")) {
			workClass = currentDispatcher.getReducer();
		}
		
		ResponseBuilder builder = Response.ok();
		builder.entity(workClass);
		return builder.build();
	}
	
//	private String formWorkJson(String jobType, Map<K, V> work) {
//		if (jobType.equals("map")) {
//			
//		} else if(jobType.equals("reduce")) {
//			
//		} else {
//			throw new IllegalArgumentException("the specified job type is not valid");
//		}
//	}
	
	@Path("/addMapResult")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMapResult() {
		//Break up JSON if need be (deserialization) // They will be sending a map so this may change
		//add map to dispatcher
		//Create 'ok' response
		//return response
		return null;
	}
	
	@Path("/addReduceResult")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReduceResult() {
		//Break up JSON if need be (deserialization) // They will be sending a map so this may change
		//add reduced data to dispatcher
		//Create 'ok' response
		//return response
		return null;
	}
}
