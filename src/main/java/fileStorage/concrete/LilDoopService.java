package fileStorage.concrete;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/message")
public class LilDoopService
{
    @GET
    public String getMsg()
    {
         return "Hello World !! - Jersey 2";
    }
}
