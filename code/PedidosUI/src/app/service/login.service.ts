import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map'
import { Pedido } from './../model/pedido';

import { User } from './../model/user';

@Injectable()
export class LoginService {
  //users : User[];
  //authUser : User;
  //erroMsj : string;
  user : User;
  res : Response;

  headers = new Headers({ 'Content-Type': 'application/json' }); 
  options = new RequestOptions({ headers: this.headers });

  constructor(private http : Http) { }

    auth (nombre: string, password: string): Observable<User>{
    return this.http.post('http://127.0.0.1:8080/PedidosREST/rest/login/auth', { nombre, password }, this.options)
      .map(this.extractData)
      .catch(this.handleError);
    }

    loadUsuarios (): Observable<User[]>{
      return this.http.get('http://127.0.0.1:8080/PedidosREST/rest/login/load')
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
  /*	constructor(
  		private loginRestService : LoginRestService
  		) { }

	checkCredentials (user) {
		this.loginRestService.auth(user.nombre, user.password).subscribe(
                   userAuth  => this.authUser = userAuth,
                   error => this.erroMsj = <any>error);
    return this.authUser;
  	}

  loadUsuarios () {
    this.loginRestService.loadUsuarios().subscribe(
                   users => this.users = users,
                   error => this.erroMsj = <any>error);
    return this.users;
  }*/

}
