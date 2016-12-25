package com.navent.service.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.navent.dao.impl.PedidoDAO;
import com.navent.model.Pedido;
import com.navent.service.cache.impl.BumexMemCached;

public class PedidoServiceTest {
	private PedidoService pedidoService;
	private Pedido pedido;

	@Before
	public void setUp() throws Exception {
		pedidoService = PedidoService.getInstance();
		pedido = new Pedido(10, "Iron Man", 10000d, 13.3f);
	}

	@Test
	public void testCrearPedido() {
		PedidoDAO mockDao = mock(PedidoDAO.class);
		BumexMemCached mockCache = mock(BumexMemCached.class);
		when(mockCache.set(pedido.getId().toString(), pedido)).thenReturn(pedido);
		when(mockDao.insertOrUpdate(pedido)).thenReturn(pedido);

		pedidoService.setBumexMemCache(mockCache);
		pedidoService.setPedidoDAO(mockDao);
		pedidoService.crearPedido(pedido);

		verify(mockCache, times(1)).set(pedido.getId().toString(), pedido);
		verify(mockDao, times(1)).insertOrUpdate(pedido);
	}

	@Test
	public void testBuscarPedido() {
		PedidoDAO mockDao = mock(PedidoDAO.class);
		BumexMemCached mockCache = mock(BumexMemCached.class);
		when(mockCache.get(pedido.getId().toString())).thenReturn(null);
		when(mockDao.select(pedido.getId())).thenReturn(pedido);
		when(mockCache.set(pedido.getId().toString(), pedido)).thenReturn(pedido);

		pedidoService.setBumexMemCache(mockCache);
		pedidoService.setPedidoDAO(mockDao);
		pedidoService.buscarPedido(pedido.getId());

		verify(mockCache, times(1)).get(pedido.getId().toString());
		verify(mockCache, times(1)).set(pedido.getId().toString(), pedido);
		verify(mockDao, times(1)).select(pedido.getId());
	}

	@Test
	public void testModificarPedido() {
		PedidoDAO mockDao = mock(PedidoDAO.class);
		BumexMemCached mockCache = mock(BumexMemCached.class);
		when(mockDao.insertOrUpdate(pedido)).thenReturn(pedido);
		when(mockCache.update(pedido.getId().toString(), pedido)).thenReturn(true);

		pedidoService.setBumexMemCache(mockCache);
		pedidoService.setPedidoDAO(mockDao);
		pedidoService.modificarPedido(pedido);

		verify(mockDao, times(1)).insertOrUpdate(pedido);
		verify(mockCache, times(1)).update(pedido.getId().toString(), pedido);
	}

	@Test
	public void testBorrarPedido() {
		PedidoDAO mockDao = mock(PedidoDAO.class);
		BumexMemCached mockCache = mock(BumexMemCached.class);
		doNothing().when(mockDao).delete(pedido.getId());
		doNothing().when(mockCache).delete(pedido.getId().toString());

		pedidoService.setBumexMemCache(mockCache);
		pedidoService.setPedidoDAO(mockDao);
		pedidoService.borrarPedido(pedido);

		verify(mockDao, times(1)).delete(pedido.getId());
		verify(mockCache, times(1)).delete(pedido.getId().toString());
	}
}
