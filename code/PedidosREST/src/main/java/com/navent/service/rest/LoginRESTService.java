package com.navent.service.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.navent.model.User;
import com.navent.service.impl.UserService;

@Path("/login")
public class LoginRESTService {

	/** Realiza una carga inicial del usuario del sistema (user/pass: test/test)
	 * 
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@GET
	@Path("/load")
	@Produces(MediaType.APPLICATION_JSON)
	public Response load() throws JsonGenerationException, JsonMappingException, IOException {
		UserService userService = UserService.getInstance();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userService.initCache());
		System.out.println("Login: load method. Json: " + json);
		return Response.status(200).entity(json).build();
	}

	/**
	 * Devuelve true si el usuario/password existe en el sistema
	 * 
	 * @param json
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@POST
	@Path("/auth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(String userJson) throws JsonGenerationException, JsonMappingException, IOException {
		UserService userService = UserService.getInstance();
		ObjectMapper mapper = new ObjectMapper();
		
		User userJson2 = mapper.readValue(userJson, User.class);
		User authenticatedUser = userService.auth(userJson2.getNombre(), userJson2.getPassword());
		
		String responseJson = mapper.writeValueAsString(authenticatedUser);
		System.out.println("Login: Authenticate method. Json: "+responseJson);
		return Response.status(200).entity(responseJson).build();
	}

}
