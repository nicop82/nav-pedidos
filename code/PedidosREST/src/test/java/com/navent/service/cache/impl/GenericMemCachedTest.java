/**
 * 
 */
package com.navent.service.cache.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.navent.model.Pedido;

/**
 * @author nicolasp
 *
 */
public class GenericMemCachedTest {
	
	private BumexMemCached bumexCache;
	private Pedido pedido;
	private Pedido pedido2;
	private Pedido pedido3;
	
	@Before
	public void setUp() throws Exception {
		bumexCache = new BumexMemCached(10000, 20000, 2); 
		pedido = new Pedido(1000, "peter parker", 1000d, 10f);
		pedido2 = new Pedido(2000, "spiderman", 2000d, 20f);
		pedido3 = new Pedido(3000, "black spider", 3000d, 30f);
	}

	/**
	 * Test method for {@link com.navent.service.cache.GenericMemCached#set(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testSet() {
		String idPedido = pedido.getId().toString(); 
		bumexCache.set(idPedido, pedido);
		assertEquals(bumexCache.isEmpty(), false);
		
		Pedido pedidoSaved = bumexCache.get(idPedido);
		assertEquals(pedido.getId(), pedidoSaved.getId());
		assertEquals(bumexCache.exists(pedido.getId().toString()), true);
		assertEquals(bumexCache.getAll().size(), 1);
	}

	/**
	 * Test method for {@link com.navent.service.cache.GenericMemCached#get(java.lang.Object)}.
	 */
	@Test
	public void testGet() {
		String idPedido = pedido.getId().toString(); 
		bumexCache.set(idPedido, pedido);
		Pedido pedidoSaved = bumexCache.get(idPedido);
		
		assertEquals(pedido.getId(), pedidoSaved.getId());
	}

	/**
	 * Test method for {@link com.navent.service.cache.GenericMemCached#getAll()}.
	 */
	@Test
	public void testGetAll() {
		bumexCache.set(pedido.getId().toString(), pedido);
		bumexCache.set(pedido2.getId().toString(), pedido2);
		bumexCache.set(pedido3.getId().toString(), pedido3);
		
		assertEquals(bumexCache.getAll().size(), 3);
		
		Pedido pedido2Saved = bumexCache.get(pedido2.getId().toString());
		
		assertEquals(pedido2Saved.getNombre(), pedido2.getNombre() );
	}

	/**
	 * Test method for {@link com.navent.service.cache.GenericMemCached#delete(java.lang.Object)}.
	 */
	@Test
	public void testDelete() {
		String idPedido = pedido.getId().toString(); 
		bumexCache.set(idPedido, pedido);
		assertEquals(bumexCache.isEmpty(), false);
		
		bumexCache.delete(idPedido);
		assertEquals(bumexCache.isEmpty(), true);
	}

	/**
	 * Test method for {@link com.navent.service.cache.GenericMemCached#update(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public void testUpdate() {
		String nuevoNombre = "Jean Gray";
		String idPedido = pedido.getId().toString(); 
		bumexCache.set(idPedido, pedido);
		Pedido pedidoToUpdate = bumexCache.get(idPedido);
		
		pedidoToUpdate.setNombre(nuevoNombre);
		bumexCache.update(idPedido, pedidoToUpdate);
		
		Pedido pedidoSaved = bumexCache.get(idPedido);
		
		assertEquals(bumexCache.isEmpty(), false);
		assertEquals(pedidoSaved.getNombre(), nuevoNombre);
	}
}
