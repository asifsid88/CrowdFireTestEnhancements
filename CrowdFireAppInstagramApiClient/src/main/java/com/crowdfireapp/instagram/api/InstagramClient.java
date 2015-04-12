package com.crowdfireapp.instagram.api;

import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.crowdfireapp.utility.CrowdFireAppUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

class InstagramClient {
	private static InstagramClient instagramClient;
	private Client client;
	
	private InstagramClient() {}
	
	public static InstagramClient getInstance() {
		if(instagramClient == null) {
			synchronized(InstagramClient.class) {
				if(instagramClient == null) {
					instagramClient = new InstagramClient();
					instagramClient.client = Client.create();	
				}	
			}
		}
		
		return instagramClient;
	}
	
	private Client getClient() {
		return instagramClient.client;
	}
	
	public String doGet(String restUrl) {
		String instagramResponse = "";
		try {
			WebResource resource = instagramClient.getClient().resource(restUrl);
			System.out.println("Calling -> " + restUrl);
			ClientResponse clientResponse = resource.accept(MediaType.APPLICATION_JSON)
													.get(ClientResponse.class);
			instagramResponse = clientResponse.getEntity(String.class);
			System.out.println("Response received !!!");
		} catch(Exception e) {
			System.out.println(CrowdFireAppUtils.formatExceptionString("Exception while making call to Instagram API: "+ restUrl, e));
		}
		
		return instagramResponse;
	}
	
	public String doPost(String restUrl, Map<String, String> data) {
		String instagramResponse = "";
		try {
			WebResource resource = instagramClient.getClient().resource(restUrl);
			MultivaluedMap<String, String> payload = createPayload(data);
			ClientResponse clientResponse = resource.accept(MediaType.APPLICATION_JSON)
													.post(ClientResponse.class, payload);
			instagramResponse = clientResponse.getEntity(String.class);
		} catch(Exception e) {
			System.out.println(CrowdFireAppUtils.formatExceptionString("Exception while making call to Instagram API: "+restUrl, e));
		}
		
		return instagramResponse;
	}
	
	private MultivaluedMap<String, String> createPayload(Map<String, String> data) {
		MultivaluedMap<String, String> payload = new MultivaluedMapImpl();
		for(Entry<String, String> entry : data.entrySet()) {
			payload.add(entry.getKey(), entry.getValue());
		}
		
		return payload;
	}
}
