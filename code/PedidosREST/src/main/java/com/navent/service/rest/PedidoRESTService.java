package com.navent.service.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.navent.model.Pedido;
import com.navent.service.impl.PedidoService;

/**
 * <p>
 * Implementación de servicio REST para conexión con FrontEnd
 * </p>
 * 
 * @author nicolasp
 *
 */
@Path("/pedido")
public class PedidoRESTService {

	/**
	 * Método que devuelve un pedido sedún su idPedido
	 * 
	 * @param idPedido
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@GET
	@Path("/get/{idPedido}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("idPedido") Integer idPedido)
			throws JsonGenerationException, JsonMappingException, IOException {
		PedidoService pedidoService = PedidoService.getInstance();
		Pedido pedido = pedidoService.buscarPedido(idPedido);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(pedido);
		System.out.println("Pedido: get/idPedido. Json: " + json);
		return Response.status(200).entity(json).build();
	}

	/**
	 * Método que guarda un pedido
	 * 
	 * @param pedido
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@POST
	@Path("/post")
	// @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(String pedidoJson) throws JsonGenerationException, JsonMappingException, IOException {
		PedidoService pedidoService = PedidoService.getInstance();
		ObjectMapper mapper = new ObjectMapper();
		Pedido pedido = mapper.readValue(pedidoJson, Pedido.class);

		Pedido pedidoSaved = pedidoService.crearPedido(pedido);

		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pedidoSaved);
		System.out.println("Pedido: post. Json: " + json);
		return Response.status(200).entity(json).build();
	}

	/**
	 * Método que actualiza un pedido
	 * 
	 * @param idPedido
	 * @param pedidoJson
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@PUT
	@Path("/put")
	// @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(String pedidoJson) throws JsonGenerationException, JsonMappingException, IOException {
		PedidoService pedidoService = PedidoService.getInstance();
		ObjectMapper mapper = new ObjectMapper();
		Pedido pedido = mapper.readValue(pedidoJson, Pedido.class);

		Pedido pedidoSaved = pedidoService.modificarPedido(pedido);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pedidoSaved);
		System.out.println("Pedido: put. Json: " + json);
		return Response.status(200).entity(json).build();
	}

	/**
	 * Método que elimina un pedido según su idPedido
	 * 
	 * @param idPedido
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@DELETE
	@Path("/delete/{idPedido}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idPedido") Integer idPedido)
			throws JsonGenerationException, JsonMappingException, IOException {
		PedidoService pedidoService = PedidoService.getInstance();
		String json = "";
		Pedido pedido = pedidoService.buscarPedido(idPedido);
		if (pedido != null) {
			pedidoService.borrarPedido(pedido);
			ObjectMapper mapper = new ObjectMapper();
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pedidoService.getPedidos());
		} else {
			json = "Pedido inexistente";
		}
		System.out.println("Pedido: delete. Json: " + json);
		return Response.status(200).entity(json).build();
	}

	/**
	 * Método que carga los pedidos iniciales
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
		PedidoService pedidoService = PedidoService.getInstance();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pedidoService.initCache());
		System.out.println("Pedido: load. Json: " + json);
		return Response.status(200).entity(json).build();
	}

	/**
	 * Método que devuelve todos los pedidos guardados
	 * 
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@GET
	@Path("/todos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() throws JsonGenerationException, JsonMappingException, IOException {
		PedidoService pedidoService = PedidoService.getInstance();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pedidoService.getPedidos());
		System.out.println("Pedido: todos. Json: " + json);
		return Response.status(200).entity(json).build();
	}
}
