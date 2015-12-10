package lildoop.fileStorage.service;


import java.io.IOException;
import java.net.MalformedURLException;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/file")
public class LilDoopService {
	FileMaster f = new FileMaster();
	
	@Path("/message")
	@GET
	public String getMsg() {
		return "Verified, the server is working";
	}
	
	@Path("/workers")
	@GET
	public String getWorkers() {
		String s = "Current Workers: ";
		for(String n : f.ips){
			s += n + "\n";
		}
		return s;
	}
	
	@Path("/join")
	@Consumes("text/plain")
	@POST
	public String joinFileSystem(@QueryParam("ip") String value) {
		f.addNode(value);
		return "Joined";
	}
	
	@Path("/get")
	@Consumes("text/plain")
	@POST
	public String getFile(@QueryParam("file") String value) throws Exception {
		if(value.contains(".")){
			return f.retrieveData(value);
		}else{
			return f.retrieveData(Long.parseLong(value));
		}
	}
	
	@Path("/delete")
	@Consumes("text/plain")
	@POST
	public String deleteFile(@QueryParam("file") String value) throws NumberFormatException, MalformedURLException, IOException {
		if(value.contains(".")){
			f.delete(value);
		}else{
			f.delete(Long.parseLong(value));
		}
		return "Verified, the server is working";
	}


	@POST
	@Consumes("text/plain")
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/save")
	public String sendResponse(@QueryParam("fileName") String name, @QueryParam("fileContent") String content) {
		System.out.println("Got: " + name + " and " + content);
		try {
			return ""+f.storeData(name, content);
		} catch (Exception e) {
			System.out.println(e);
			return "Sorry, it broke :(";
		}
	}
}
