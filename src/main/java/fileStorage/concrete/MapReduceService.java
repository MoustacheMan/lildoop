package fileStorage.concrete;

import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import javax.print.attribute.HashDocAttributeSet;
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
	public Response getNextWork() {
		//[Have to know when were mapping or reducing here]
		ResponseBuilder builder = Response.ok();
		Object jobPerformerToSend = null;
		String jobType = null;
		int workAmount = 0;
		Map work = null;
		//When mapping
		if (currentDispatcher.hasDataToMap()) {
			//get next data set to map
			currentDispatcher.getNextDataSetToMap(workAmount);
			//set job type to map
			jobPerformerToSend = currentDispatcher.getMapper();
			jobType = "map";
		} else {
		//When reducing	
			//get next mapped data set to reduce
			currentDispatcher.getNextMappedData(workAmount);
			//set job type to reduce
			jobPerformerToSend = currentDispatcher.getReducer();
			jobType = "reduce";
		}	
		
		//form JSON with data and mapper or reducer according to job type
		//create response with job type header and JSON
		builder.header("X-JobType", jobType);
//		builder.entity();
		//return the response
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
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response addMapResult(InputStream input) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(input);
		Map<?,List> mapperResult = (Map<?,List>)ois.readObject();
		if(mapperResult != null)
		{
			currentDispatcher.addMappedData(mapperResult);
			
			return Response.ok("200").build();
		}
		
		return Response.noContent().build();
	}
	
	@Path("/addReduceResult")
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response addReduceResult(InputStream input) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(input);
		Map<?,?> reduceResult = (Map<?,?>)ois.readObject();
		if(reduceResult != null)
		{
			currentDispatcher.addReducedData(reduceResult);
			return Response.ok("200").build();
		}
		return Response.noContent().build();
	}
}
