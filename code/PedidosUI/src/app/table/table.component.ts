import { Component, OnInit } from '@angular/core';
import { Ng2TableModule } from 'ng2-table/ng2-table';
import { Router } from '@angular/router';

import { PedidoService } from './../service/pedido.service';
import { Pedido } from './../model/pedido';
import { PedidoDTO } from './../model/pedidoDto';

@Component({
	selector: 'app-table',
	templateUrl: './table.component.html'
})
export class TableComponent implements OnInit {
	errorMsg: String;
	pedidos : Pedido[];
	pedidoDTO : PedidoDTO;
	pedido : Pedido;
	public rows:Array<any> = [];
	public columns:Array<any> = [
	{title: 'Id', name: 'id', sort: 'asc', filtering: {filterString: '', placeholder: 'Id'}},
	{title: 'Nombre', name: 'nombre', sort: 'asc', filtering: {filterString: '', placeholder: 'Nombre'}},
	{title: 'Monto', name: 'monto', sort: 'asc', filtering: {filterString: '', placeholder: 'Monto'}},
	{title: 'Descuento', name: 'descuento', sort: 'asc', filtering: {filterString: '', placeholder: 'Descuento'}}
	];
	public page:number = 1;
	public itemsPerPage:number = 15;
	public maxSize:number = 5;
	public numPages:number = 1;
	public length:number = 0;

	public config:any = {
		paging: false,
		sorting: {columns: this.columns},
		filtering: {filterString: ''},
		className: ['table-striped', 'table-bordered']
	};	

	private data:Array<any>;

	constructor(private router: Router, private pedidoService : PedidoService) { 
		
	}

	public ngOnInit():void {	
		this.getPedidos();	
	}

	public changePage(page:any, data:Array<any> = this.data):Array<any> {
		let start = (page.page - 1) * page.itemsPerPage;
		let end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
		return data.slice(start, end);
	}

	public changeSort(data:any, config:any):any {
		if (!config.sorting) {
			return data;
		}

		let columns = this.config.sorting.columns || [];
		let columnName:string = void 0;
		let sort:string = void 0;

		for (let i = 0; i < columns.length; i++) {
			if (columns[i].sort !== '' && columns[i].sort !== false) {
				columnName = columns[i].name;
				sort = columns[i].sort;
			}
		}

		if (!columnName) {
			return data;
		}

		// simple sorting
		return data.sort((previous:any, current:any) => {
			if (previous[columnName] > current[columnName]) {
				return sort === 'desc' ? -1 : 1;
			} else if (previous[columnName] < current[columnName]) {
				return sort === 'asc' ? -1 : 1;
			}
			return 0;
		});
	}

	public changeFilter(data:any, config:any):any {

		let filteredData:Array<any> = data;
		this.columns.forEach((column:any) => {
			if (column.filtering) {
				filteredData = filteredData.filter((item:any) => {
					return item[column.name].toString().match(column.filtering.filterString);
				});
			}
		});

		if (!config.filtering) {
			return filteredData;
		}

		if (config.filtering.columnName) {
			return filteredData.filter((item:any) =>
				item[config.filtering.columnName].match(this.config.filtering.filterString));
		}

		let tempArray:Array<any> = [];
		filteredData.forEach((item:any) => {
			let flag = false;
			this.columns.forEach((column:any) => {
				if (item[column.name].toString().match(this.config.filtering.filterString)) {
					flag = true;
				}
			});
			if (flag) {
				tempArray.push(item);
			}
		});
		filteredData = tempArray;

		return filteredData;
	}

	public onChangeTable(config:any, page:any = {page: this.page, itemsPerPage: this.itemsPerPage}):any {
		if (config.filtering) {
			Object.assign(this.config.filtering, config.filtering);
		}

		if (config.sorting) {
			Object.assign(this.config.sorting, config.sorting);
		}

		let filteredData = this.changeFilter(this.data, this.config);
		let sortedData = this.changeSort(filteredData, this.config);
		this.rows = page && config.paging ? this.changePage(page, sortedData) : sortedData;
		this.length = sortedData.length;
	}

	public onCellClick(data: any): any {
		this.router.navigate(['verPedido', data.row.id]);
	}

	private convertDTOFromPedido(pedido) {
		let monto = pedido.monto.toString();
		let descuento = pedido.descuento.toString();
		let id = pedido.id.toString();
		return new PedidoDTO(id,pedido.nombre, monto, descuento);
	}

	getPedidos() {
		this.pedidoService.getPedidos()
			.subscribe(
				(success)  => { this.data = success;},
				(error) =>  { this.errorMsg = 'No se pudieron cargar los pedidos'; },
				() => {
					if (this.data) {
						this.length = this.data.length;
						this.onChangeTable(this.config);
					} else {
						this.errorMsg = 'No se pudieron cargar los pedidos';
					}
				}
			);
	}

}
