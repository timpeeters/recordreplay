package be.xplore.recordreplay;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {
    @Bean
    public Endpoint userService(Bus bus) {
        EndpointImpl endpoint = new EndpointImpl(bus, new UserServiceImpl());
        endpoint.publish("/user");

        return endpoint;
    }
}
