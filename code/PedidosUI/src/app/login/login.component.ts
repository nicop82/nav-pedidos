import { Component, OnInit, Input } from '@angular/core';
import {Router} from '@angular/router';

import { User } from '../model/user';
import { Pedido } from '../model/pedido';

import { LoginService } from './../service/login.service';
import { PedidoService } from './../service/pedido.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

	public user = new User('','','','');
	public userAuth : User;
	public users : User[];
	public pedidos : Pedido[];
	public loadMsg = '';
	public errorMsg = '';

	constructor(
		private router: Router,
		private loginService: LoginService,
		private pedidoService : PedidoService) { }

	ngOnInit() {
	}

	login(user) {
		this.loginService.auth(user.nombre, user.password)
		.subscribe(
			(success)  => {
					this.userAuth = success;
			},
			(error) =>  {
					this.errorMsg = <any>error;
					this.loadMsg = "";
					this.errorMsg = 'Error en usuario o clave';
			},
			() => {
				if (this.userAuth && this.userAuth.rol) {
					this.router.navigate(['/home']);
				} else {
					this.loadMsg = "";
					this.errorMsg = 'Error en usuario o clave';	
				}
			;}
		);
	}

	loadData() {
		this.loginService.loadUsuarios().subscribe(
			(success)  => {this.users = success;},
			(error) =>  {this.errorMsg = <any>error;}
		);
		this.pedidoService.loadPedidos().subscribe(
			(success)  => {this.pedidos = success;},
			(error) =>  {this.errorMsg = <any>error;}
		);

		this.errorMsg = "";
		this.loadMsg = "Información cargada en cache.";
	}
}
