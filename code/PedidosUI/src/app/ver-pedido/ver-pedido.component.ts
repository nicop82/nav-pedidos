import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { PedidoService } from './../service/pedido.service';

import { PedidoDTO } from './../model/pedidoDto';
import { Pedido } from './../model/pedido';

@Component({
	selector: 'app-ver-pedido',
	templateUrl: './ver-pedido.component.html',
	styleUrls: ['./ver-pedido.component.css']
})
export class VerPedidoComponent implements OnInit {

	model : PedidoDTO;
	pedido : Pedido;
	submitted = false;
	pedidoForm : FormGroup;
	active = true;
	idPedido : string;
	successMsg = "";
	errorMsg = "";
	isDataAvailable:boolean = false;

	constructor( private route: ActivatedRoute, private router: Router, private pedidoService: PedidoService) { 
		this.getPedido();
	}

	ngOnInit() {
		
	}

	getPedido() {
		this.route.params.forEach((params: Params) => {
			this.idPedido = params['id'];
			this.pedidoService.getPedido(this.idPedido).subscribe(
				(success) => {this.pedido = success},
				(error) => {this.errorMsg = <any>error},
				() => {
					if (this.pedido) {
						this.model = this.convertDTOFromPedido(this.pedido);
						this.isDataAvailable = true;
					} else {
						this.errorMsg = "No se pudo recuperar el pedido";
					}
				}
			);
		});	
	}

	modificarPedido () {
		let pedido = this.convertPedidoFromDTO(this.model);
		this.pedidoService.updatePedido(pedido)
			.subscribe(
				(success)  => {
					this.successMsg = 'Pedido modificado correctamente';
					this.router.navigate(['/home']);
				},
				(error) =>  { 
					this.errorMsg = 'Error al modificar el pedido'; 
				}
			);
	}

	deletePedido(idPedido) {
		this.pedidoService.deletePedido(this.model.id).subscribe(
			(success) => {
				this.successMsg = 'Pedido eliminado correctamente';
				this.router.navigate(['/home']);
			},
			(error) => {
				this.errorMsg = "No se pudo eliminar el pedido"
			}
		);
	}

	volverHome() {
		this.router.navigate(['/home']);
	}

	private convertDTOFromPedido(pedido) {
		let monto = pedido.monto.toString();
		let descuento = pedido.descuento.toString();
		let id = pedido.id.toString();
		return new PedidoDTO(id,pedido.nombre, monto, descuento);
	}

	private convertPedidoFromDTO(pedidoDTO) {
		let monto = parseFloat(pedidoDTO.monto);
		let descuento = 0;
		if (pedidoDTO.descuento) {
			descuento = parseFloat(pedidoDTO.descuento);	
		}
		return new Pedido(pedidoDTO.id,pedidoDTO.nombre, monto, descuento);
	}
}
