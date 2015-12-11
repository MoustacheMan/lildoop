package lildoop.mapReduce.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONObject;

import lildoop.fileStorage.client.FileClient;
import lildoop.mapReduce.models.Query;

@Path("/mapReduce")
public class MapReduceService {
	
	private Dispatcher currentDispatcher;
	
	@Path("/ping")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String pingServer() {
		return "Ping successful. Server available";
	}
	
	@Path("/start")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response startMapReduce(String jsonString) {
		System.out.println("<Start map reduce>");
		// Get data from JSON
		JSONObject json = new JSONObject(jsonString);
		// Create dispatcher
		currentDispatcher = new Dispatcher(new Query(json), new FileClient(""));
		// send accepted response
		ResponseBuilder response = Response.accepted();
		response.entity("Job accepted. Processing query.");
		System.out.println("<End map reduce>");
		return response.build();
	}
	
	@Path("/status")
	@GET
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.TEXT_PLAIN)
	public Response getStatus() {
		System.out.println("<Start status check>");
		// send 304 if still running 200 when done
		ResponseBuilder builder = null;
		if (currentDispatcher != null && currentDispatcher.isProccessing()) {
			builder = Response.notModified();
		} else {
			builder = Response.ok();
		}
		System.out.println("<End status check : code = " + builder.build().getStatusInfo().getStatusCode() + ">");
		
		return builder.build();
	}
	
	@Path("/data")
	@GET
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getData() {
		String resultsJson = currentDispatcher.getResults();
		ResponseBuilder builder = Response.ok();
		builder.entity(resultsJson);
		return builder.build();
	}
	
	@Path("/work")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWork() {
		// Get next data set
		JSONObject[] rows = currentDispatcher.getNextWorkSet();
		// Prep json using same json got just replace data with actual data
		String workJson = currentDispatcher.getWorkJson(rows);
		// 200 ok response, add json and send
		ResponseBuilder builder = Response.ok();
		builder.entity(workJson);
		return builder.build();
	}
	
	@Path("/result")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response receiveResult(String jsonString) {
		// Get data from json (func, param, result)
		JSONObject json = new JSONObject(jsonString);
		// Add to dispatcher result list/set
		currentDispatcher.addResults(json);
		ResponseBuilder builder = Response.ok();
		return builder.build();
	}
}
