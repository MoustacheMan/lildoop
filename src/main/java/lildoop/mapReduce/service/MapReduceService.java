package lildoop.mapReduce.service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONObject;

import lildoop.mapReduce.models.Query;

@Path("/mapReduce")
public class MapReduceService {
	
	private static Dispatcher currentDispatcher;
	
	@Path("/ping")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String pingServer() {
		return "Ping successful. Server available";
	}
	
	@Path("/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response saveFileLocally(String jsonString) {
		System.out.println("Saving file...");
		String dataDirectory = "C:/Lildoop/map_reduce_data/";
		JSONObject json = new JSONObject(jsonString);
		String filePath = dataDirectory + json.getString("fileName") + ".txt";
		writeFile(filePath, json.getString("fileContent"));
		ResponseBuilder builder = Response.ok();
		return builder.build();
	}
	
	private void writeFile(String filePath, String data) {
		File dataFile = new File(filePath);
		String[] lines = data.split("--");
		dataFile.getParentFile().mkdirs();
		try (PrintWriter writer = new PrintWriter(new FileWriter(dataFile))) {
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i];
				writer.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Path("/start")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response startMapReduce(String jsonString) {
		System.out.println("Processing query...");
		JSONObject json = new JSONObject(jsonString);
		currentDispatcher = new Dispatcher(new Query(json, false));
		ResponseBuilder response = Response.accepted();
		response.entity("Job accepted. Processing query.");
		return response.build();
	}
	
	@Path("/status")
	@GET
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.TEXT_PLAIN)
	public Response getStatus() {
		ResponseBuilder builder = null;
		if (currentDispatcher == null) {
			builder = Response.noContent();
		} else if (currentDispatcher.isProccessing()) {
			builder = Response.notModified();
		} else {
			builder = Response.ok();
		}
		
		return builder.build();
	}
	
	@Path("/result")
	@GET
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getData() {
		System.out.println("Sending result...");
		String resultsJson = currentDispatcher.getResults();
		ResponseBuilder builder = Response.ok();
		builder.entity(resultsJson);
		currentDispatcher = null;
		return builder.build();
	}
	
	@Path("/work")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWork() {
		JSONObject[] rows = null;
		synchronized(currentDispatcher) {
			rows = currentDispatcher.getNextWorkSet();
		}
		String workJson = currentDispatcher.getWorkJson(rows);
		ResponseBuilder builder = Response.ok();
		builder.entity(workJson);
		return builder.build();
	}
	
	@Path("/result")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response receiveResult(String jsonString) {
		JSONObject json = new JSONObject(jsonString);
		synchronized(currentDispatcher) {
			currentDispatcher.addResults(json);
		}
		ResponseBuilder builder = Response.ok();
		return builder.build();
	}
}
