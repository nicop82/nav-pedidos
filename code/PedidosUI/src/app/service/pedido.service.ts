import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

//import { PedidoRestService } from './pedido-rest.service';
import { Pedido } from './../model/pedido';

@Injectable()
export class PedidoService {
  headers = new Headers({ 'Content-Type': 'application/json' }); 
  options = new RequestOptions({ headers: this.headers });
	//pedidos : Pedido[];
	//pedido : Pedido;
	//errorMsj : string;

  constructor(private http : Http) { }

  loadPedidos (): Observable<Pedido[]>{
    return this.http.get('http://127.0.0.1:8080/PedidosREST/rest/pedido/load')
        .map(this.extractData)
        .catch(this.handleError);
  }

  getPedidos (): Observable<Pedido[]>{
    return this.http.get('http://127.0.0.1:8080/PedidosREST/rest/pedido/todos')
        .map(this.extractData)
        .catch(this.handleError);
  }

  getPedido (idPedido : string): Observable<Pedido>{
    return this.http.get('http://127.0.0.1:8080/PedidosREST/rest/pedido/get/' + idPedido)
        .map(this.extractData)
        .catch(this.handleError);
  }

  createPedido(pedido: Pedido): Observable<Pedido>{
    let body = JSON.stringify(pedido);
    return this.http.post('http://127.0.0.1:8080/PedidosREST/rest/pedido/post', body, this.options)
        .map(this.extractData)
        .catch(this.handleError);
  }

  updatePedido (pedido: Pedido): Observable<Pedido>{
    let body = JSON.stringify(pedido);
    return this.http.put('http://127.0.0.1:8080/PedidosREST/rest/pedido/put/', body, this.options)
        .map(this.extractData)
        .catch(this.handleError);
  }

  deletePedido (idPedido : string): Observable<any>{
    return this.http.delete('http://127.0.0.1:8080/PedidosREST/rest/pedido/delete/' + idPedido , this.options)
        .map(this.extractData)
        .catch(this.handleError);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body || { };
  }

  private handleError (error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
  /*loadPedidos () {
    this.pedidoRestService.loadPedidos().subscribe(
      pedidos => this.pedidos = pedidos,
      error => this.errorMsj = <any>error);
    return this.pedidos;
  }

  getPedidos () {
    this.pedidoRestService.getPedidos().subscribe(
      pedidos => this.pedidos = pedidos,
      error => this.errorMsj = <any>error);
    return this.pedidos;
  }

  getPedido (idPedido) {
    this.pedidoRestService.getPedido(idPedido).subscribe(
      pedido => this.pedido = pedido,
      error => this.errorMsj = <any>error);
    return this.pedido;
  }

  createPedido (ped) {
    this.pedidoRestService.postPedido(ped).subscribe(
      pedido => this.pedido = pedido,
      error => this.errorMsj = <any>error);
    return this.pedido;
  }

  updatePedido (ped) {
    this.pedidoRestService.putPedido(ped).subscribe(
      pedido => this.pedido = pedido,
      error => this.errorMsj = <any>error);
    return this.pedido;
  }

  deletePedido(idPedido) {
    this.pedidoRestService.deletePedido(idPedido).subscribe(
      pedido => this.pedido = pedido,
      error => this.errorMsj = <any>error);
    return this.pedido;
  }*/
}
