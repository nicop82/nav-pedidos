import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router  } from '@angular/router';

@Component({
	selector: 'app-home',
	templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

	constructor(private router: Router,  private route: ActivatedRoute) { }

	ngOnInit() {
	}

	irAGuardarPedido() {
		this.router.navigate(['/nuevoPedido']);	
	}

}
