import { Injectable } from '@angular/core';
import { async, inject, TestBed } from '@angular/core/testing';
import { MockBackend, MockConnection } from '@angular/http/testing';
import {
	Http, HttpModule, XHRBackend, ResponseOptions,
	Response, BaseRequestOptions
} from '@angular/http';
import { tick, fakeAsync } from '@angular/core/testing/fake_async';

import { PedidoService } from './../service/pedido.service';
import { Pedido } from './../model/pedido';

describe('service: PedidoService', () => {
	let pedidos: Pedido[];
	beforeEach(() => {
		TestBed.configureTestingModule({
			providers: [
			{
				provide: Http, useFactory: (backend, options) => {
					return new Http(backend, options);
				},
				deps: [MockBackend, BaseRequestOptions]
			},
			MockBackend,
			BaseRequestOptions,
			PedidoService
			]
		});
	});

	it('debería buscar los pedidos',
		inject([PedidoService, MockBackend], fakeAsync((_service: PedidoService, mockBackend: MockBackend) => {
			let res: Pedido[];
			mockBackend.connections.subscribe(c => {
				expect(c.request.url).toBe('http://127.0.0.1:8080/PedidosREST/rest/pedido/todos');
				let response = new ResponseOptions({body: '[{"nombre": "Santa", "id":1, "monto":2000, "descuento":10}]'});
				c.mockRespond(new Response(response));
			});
			_service.getPedidos().subscribe((response) => {
				res = response;
			});
			tick();
			expect(res[0].nombre).toBe('Santa');
		}))
	);
});