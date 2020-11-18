package com.serrodcal.poc;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@Path("/pong")
@RegisterRestClient
public interface PongService {
 
    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    Uni<String> pong(String message);

}
