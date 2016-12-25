import { NgModule }               from '@angular/core';
import { RouterModule, Routes }   from '@angular/router';

import { LoginComponent }           from './login/login.component';
import { HomeComponent }             from './home/home.component';
import { NuevoPedidoComponent }    from './nuevo-pedido/nuevo-pedido.component';
import { VerPedidoComponent }    from './ver-pedido/ver-pedido.component';
import { ErrorPageComponent }     from './error-page/error-page.component';

@NgModule({
  imports: [
    RouterModule.forRoot([
      { 
        path: '', redirectTo: '/login', 
        pathMatch: 'full'
      },
  		{ 
        path: 'login',  
        component: LoginComponent 
      },
      { 
        path: 'home',  
        component: HomeComponent
      },
      { 
        path: 'nuevoPedido',  
        component: NuevoPedidoComponent
      },
      { 
        path: 'verPedido/:id',  
        component: VerPedidoComponent
      },
      { 
        path: '**', 
        component: ErrorPageComponent 
      }
    ])
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
