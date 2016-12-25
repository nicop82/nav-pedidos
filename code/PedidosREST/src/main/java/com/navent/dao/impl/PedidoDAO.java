package com.navent.dao.impl;

import com.navent.model.Pedido;

public class PedidoDAO extends GenericDAOImpl<Pedido>{
	
	private static final Integer ID_COUNTER_INITIAL = 100;
	private static Integer idCounter = null;
	private static PedidoDAO instance = null;

	private PedidoDAO () {
	}
	
	public static PedidoDAO getInstance() {
		if (instance == null) {
			instance = new PedidoDAO();
			idCounter = ID_COUNTER_INITIAL;
		}
		return instance;
	}
	/**
	 *  Método que inserta un nuevo pedido en la base de datos o modifica 
	 *	un pedido existente (en cado de crear uno nuevo, el pedido pasado como parámetro se
	 *	completa con el nuevo id).
	 * 
	 * @param pedido
	 */
	public Pedido insertOrUpdate(Pedido pedido) {
		if (pedido.getId() < 0) {
			// Crea pedido
			// Simulamos el grabado de el Pedido a la base y le asignamos un idPedido
			pedido.setId(idCounter);
			idCounter++;
		} else {
			// debería actualizar el pedido
		}
		return pedido;
	}
	
	/**
	 * Método que elimina el pedido que corresponde al id recibido.
	 * 
	 * @param pedido
	 */
	public void delete(Pedido pedido) {
//		No hacemos nada
	}
	
	/**
	 * Método que busca un pedido por id.
	 * 
	 * @param idPedido
	 * @return
	 */
	public Pedido select(Integer idPedido) {
		// No hacemos nada ya que no podemos ir a la base. Devolvemos un nuevo
		// pedido
		Pedido pedido = new Pedido();
		pedido.setId(idPedido);
		pedido.setNombre("Nuevo");
		pedido.setMonto(1000d);
		pedido.setDescuento(25F);
		return pedido;
	}
}
