import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';

import { PedidoService } from './../service/pedido.service';

import { PedidoDTO } from './../model/pedidoDto';
import { Pedido } from './../model/pedido';

@Component({
  selector: 'app-nuevo-pedido',
  templateUrl: './nuevo-pedido.component.html',
  styleUrls: ['./nuevo-pedido.component.css']
})
export class NuevoPedidoComponent implements OnInit {

	model = new PedidoDTO('','','','');
	submitted = false;
	pedidoForm : FormGroup;
	active = true;
	msgSuccess = "";
	errorMsg = "";
	pedidoSaved : Pedido;

  	constructor(
		private route: ActivatedRoute,
		private pedidoService : PedidoService
  		) { }

  	ngOnInit() {
  	}

  	onSubmit() { 
		this.submitted = true; 
	}

	public newPedido() {
		this.model = new PedidoDTO('','','','');
		this.submitted = false;
		setTimeout(() => this.active = true, 0);
	}

	public savePedido () {
		let pedido = this.convertPedidoFromDTO(this.model);
		this.pedidoService.createPedido(pedido).subscribe(
	      pedidoResponse => this.pedidoSaved = pedidoResponse,
	      error => this.errorMsg = <any>error);
		this.submitted = false;
		this.model = new PedidoDTO('','','','');
		this.active = false;
		this.msgSuccess = "Pedido guardado correctamente";
		setTimeout(() => this.active = true, 0);
	}

	private convertPedidoFromDTO(pedidoDTO) {
		let monto = parseFloat(pedidoDTO.monto);
		let descuento = 0;
		if (pedidoDTO.descuento) {
			descuento = parseFloat(pedidoDTO.descuento);	
		}
		
		return new Pedido(-1,pedidoDTO.nombre, monto, descuento);
	}


}
