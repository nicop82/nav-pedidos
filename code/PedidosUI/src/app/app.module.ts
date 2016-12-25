import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppRoutingModule } from './app-routing.module';
import { Ng2TableModule } from 'ng2-table/ng2-table';

// Services
import { LoginService } from './service/login.service';
import { PedidoService } from './service/pedido.service';

//Components
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { HomeComponent } from './home/home.component';
import { TableComponent } from './table/table.component';
import { NuevoPedidoComponent } from './nuevo-pedido/nuevo-pedido.component';
import { VerPedidoComponent } from './ver-pedido/ver-pedido.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ErrorPageComponent,
    HomeComponent,
    TableComponent,
    NuevoPedidoComponent,
    VerPedidoComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    Ng2TableModule
  ],
  providers: [
    LoginService,
    PedidoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
