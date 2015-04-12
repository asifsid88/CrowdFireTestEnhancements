package com.crowdfireapp.instagramservice.helpers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.crowdfireapp.utility.JSONUtils;
import com.crowdfireapp.utility.ObjectUtils;

public class ResponseBuilder {
	public static Response buildResponse(Status status, Object response) {
		String json = "";
		if(!ObjectUtils.isNull(response)) {
			json = JSONUtils.toJSON(response);
		}
		
		return Response.status(status).entity(json).build();
	}
}
