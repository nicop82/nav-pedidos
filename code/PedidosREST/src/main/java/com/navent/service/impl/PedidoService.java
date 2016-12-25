package com.navent.service.impl;

import java.util.HashSet;

import com.navent.dao.impl.PedidoDAO;
import com.navent.model.Pedido;
import com.navent.service.cache.impl.BumexMemCached;

/**
 * <p>
 * Consigna: Se desean modelar los Pedidos realizados por un cliente. Las
 * operaciones que se necesitan son: crear pedidos, modificar pedidos, buscar
 * pedidos por id, y borrar pedidos. Se pide modelar la clase que implementa
 * estas operaciones (en lenguaje Java) sabiendo que se utiliza una estructura
 * de caches para no acceder a la base de datos en cada operación (utilizar las
 * clases BumexMemcached y PedidosDAO descritas más adelante).
 * </p>
 * 
 * @author nicolasp
 *
 */
public class PedidoService {
	private static PedidoService instance = null;
	private static BumexMemCached bumexMemCache = null;
	private static PedidoDAO pedidoDAO = null;


	private PedidoService() {

	}
	
	public static PedidoService getInstance() {
		if (instance == null) {
			instance = new PedidoService();

			// Obtengo instancia de memCache para no tener que pedirla siempre
			bumexMemCache = BumexMemCached.getInstance();
			pedidoDAO = PedidoDAO.getInstance();
		}
		return instance;
	}

	/** Solo utilizado para mock en test
	 * 
	 * @param bumexMemCache
	 */
	public static void setBumexMemCache(BumexMemCached bumexMemCache) {
		PedidoService.bumexMemCache = bumexMemCache;
	}
	
	/** Solo utilizado para mock en test
	 * 
	 * @param pedidoDAO
	 */
	public static void setPedidoDAO(PedidoDAO pedidoDAO) {
		PedidoService.pedidoDAO = pedidoDAO;
	}

	public Pedido crearPedido(Pedido pedido) {
		pedido = pedidoDAO.insertOrUpdate(pedido);
		bumexMemCache.set(String.valueOf(pedido.getId()), pedido);
		return pedido;
	}

	public Pedido modificarPedido(Pedido pedido) {
		Pedido pedidoUpdated = pedidoDAO.insertOrUpdate(pedido);
		if (pedido != null) {
			bumexMemCache.update(String.valueOf(pedido.getId()), pedidoUpdated);
		}
		return pedidoUpdated;
	}

	public Pedido buscarPedido(Integer idPedido) {
		Pedido pedido = bumexMemCache.get(String.valueOf(idPedido));
		if (pedido == null) {
			pedido = pedidoDAO.select(idPedido);
			if (pedido != null) {
				bumexMemCache.set(String.valueOf(pedido.getId()), pedido);
			}
		}
		return pedido;
	}

	public void borrarPedido(Pedido pedido) {
		// Borramos de la Base
		pedidoDAO.delete(pedido.getId());
		// Borramos del cache si existe
		bumexMemCache.delete(String.valueOf(pedido.getId()));
	}

	public HashSet<Pedido> getPedidos() {
		return bumexMemCache.getValues();
	}

	public HashSet<Pedido> initCache() {

		if (bumexMemCache.isEmpty()) {
			bumexMemCache.set("1", this.getNewPedido(1, "Villavicencio", 10000d, 0f));
			bumexMemCache.set("2", this.getNewPedido(2, "Villa Del sur", 20000d, 10f));
			bumexMemCache.set("3", this.getNewPedido(3, "Coca-Cola", 30000d, 20f));
			bumexMemCache.set("4", this.getNewPedido(4, "Gatorade", 40000d, 30f));
			bumexMemCache.set("5", this.getNewPedido(5, "Levite", 50000d, 40f));
		}
		return this.getPedidos();
	}

	private Pedido getNewPedido(Integer id, String nombre, Double monto, Float descuento) {
		Pedido pedido = new Pedido();
		pedido.setId(id);
		pedido.setNombre(nombre);
		pedido.setMonto(monto);
		pedido.setDescuento(descuento);
		return pedido;
	}
}
