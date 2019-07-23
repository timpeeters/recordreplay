package be.xplore.fakes.config;

public interface Configuration {

    int port();
    Class<? extends be.xplore.recordreplay.service.AbstractHttpServlet> servletClass();

}
