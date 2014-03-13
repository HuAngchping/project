package com.weilai.swmf.web;

import javax.json.stream.JsonGenerator;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;


public class MyApplication extends ResourceConfig {

    public MyApplication() {
        packages("com.weilai.swmf.web.controllers");
        register(LoggingFilter.class);
        property(JsonGenerator.PRETTY_PRINTING, true);
    }
}



