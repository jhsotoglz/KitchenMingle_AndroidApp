package RoundTrip.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class CommentSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {return new ServerEndpointExporter();}
}
