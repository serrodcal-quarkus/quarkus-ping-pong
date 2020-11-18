package com.serrodcal.poc;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class PongService {

    private static final Logger log = Logger.getLogger(PongService.class);

    private static final String PING_MESSAGE = "ping";

    public Uni<String> pong(String pingMessage) {
        if (pingMessage.equals(PING_MESSAGE)) {
            log.info("Ping message recevied successfully");
            return Uni.createFrom().item("pong");
        } else { 
            log.error("Ping message is wrong");
            return Uni.createFrom().failure(new RuntimeException("Ping message is wrong!"));
        }
    }
    
}
