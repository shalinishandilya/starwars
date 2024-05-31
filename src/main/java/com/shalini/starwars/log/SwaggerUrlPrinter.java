package com.shalini.starwars.log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SwaggerUrlPrinter {

    //ToDo Make this class run only for local environment
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${server.port}")
    private String serverPort;

    @EventListener(ApplicationReadyEvent.class)
    public void printSwaggerUrl() {
        //TODO Remove before moving to production.
        String baseUrl = "http://localhost:" + serverPort + contextPath;
        String swaggerUrl = baseUrl + "/swagger-ui/index.html";
        System.out.println("Swagger UI URL: " + swaggerUrl);
    }
}