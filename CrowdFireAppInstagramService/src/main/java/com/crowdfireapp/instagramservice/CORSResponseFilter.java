package com.crowdfireapp.instagramservice;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CORSResponseFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		ResponseBuilder responseBuilder = Response.fromResponse(response.getResponse());
        
        responseBuilder.header("Access-Control-Allow-Origin", "*")
        				.header("Access-Control-Allow-Methods", "GET, POST, PUT, UPDATE, OPTIONS")
        				.header("Access-Control-Allow-Headers", "x-requested-with,Content-Type");
 
        String requestHeader = request.getHeaderValue("Access-Control-Request-Headers");
        if (null != requestHeader && !requestHeader.equals(null)) {
            responseBuilder.header("Access-Control-Allow-Headers", requestHeader);
        }
 
        response.setResponse(responseBuilder.build());
        return response;
	}
}