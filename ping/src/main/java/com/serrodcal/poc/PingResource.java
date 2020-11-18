package com.serrodcal.poc;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@ApplicationScoped
@RouteBase(path = "api/v1")
public class PingResource {
    
    private static final Logger log = Logger.getLogger(PingResource.class);

    @Inject
    @RestClient
    PongService pongService;

    @Route(path = "ping/:message", methods = HttpMethod.GET)
    void ping(RoutingContext rc, @Param("message") String message) {
        log.info("Ping request received");
        log.debug("Ping message is: " + message);
        this.pongService.pong(message).subscribe().with(result -> {
            rc.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain").setStatusCode(HttpResponseStatus.OK.code()).end(result);
        }, failure -> {
            rc.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain").setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end(failure.getMessage());
        });
    }

}
