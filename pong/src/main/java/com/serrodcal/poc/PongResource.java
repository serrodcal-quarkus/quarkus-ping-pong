package com.serrodcal.poc;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import org.jboss.logging.Logger;

@ApplicationScoped
@RouteBase(path = "api/v1")
public class PongResource {

    private static final Logger log = Logger.getLogger(PongResource.class);

    @Inject
    PongService pongService;

    @Route(path = "pong", methods = HttpMethod.POST)
    void pong(RoutingContext rc, @Body String pingMessage) {
        log.info("Ping request received");
        log.debug("Ping message is: " + pingMessage);
        this.pongService.pong(pingMessage).subscribe().with(result -> {
            rc.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain").setStatusCode(HttpResponseStatus.OK.code()).end(result);
        }, failure -> {
            rc.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain").setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end(failure.getMessage());
        });
    }
    
}
