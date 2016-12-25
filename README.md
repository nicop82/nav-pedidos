# Video
Ver video que explica cómo correr la aplicación en /navent.ogv.

# Proyecto de pedidos para navent
El proyecto consiste en una UI que consume de un backend vía servicios REST (modo de conexión vía JAX-RS). El backend dispone de una cache genérica y sus implementaciones para ser un intermediario entre la base de datos. 

# Estructura de carpetas
- README.md
- Arquitectura.jpg: descripción de arquitectura general del proyecto. 
- Video explicativo. 
- Code/PedidosUI: projecto realizado con Angular 2. Es un ABM de pedidos que se comunica con PedidosRest por medio de servicios web REST. 
- Code/PedidosREST: es consumido por PedidosUI y contiene una cache genérica con sus implementaciónes como as también un capa de Daos que permitirían la integración con una base de datos. 

## Proyecto PedidosUI
Proyecto realizado con Angular 2 basado en la estructura propuesta por [angular-cli](https://github.com/angular/angular-cli). Es una single-web-page responsive (Bootstrap 3).

### Correr localmente:
1. $ Instalar [nodejs](https://nodejs.org/en/download/) (utilizadas nodejs v4.6.2 y npm v3.10.9)
2. $ npm install -g angular-cli 
3. $ cd /PedidosUI
4. $ npm install
5. $ [ng serve](https://github.com/angular/angular-cli#generating-and-serving-an-angular2-project-via-a-development-server)
6. Ir a http://localhost:4200/
> El projecto se desarrollo con la versión v4.6.2 de nodejs y 3.10.9 de npm

### Build para poner en servidor
> También se puede generar un html y archivo javascript que puede ponerse en un servidor por medio del comando [ng build](https://github.com/angular/angular-cli#creating-a-build) desde la carpeta /PedidosUI. El comando generará en la carpeta /PedidosUI/dist los archivos html, js e imagenes de la aplicación.

### Unit Test PedidosUI (herramientas: Karma - Jasmine)
1. cd PedidosUI
2. [ng test](https://github.com/angular/angular-cli#running-unit-tests)
> Testea el servicio REST cliente expuesto por la aplicación. 

### End-to-End test PedidosUI (herramientas: Protractror - Jasmine)
1. cd PedidosUI
2. [ng e2e](https://github.com/angular/angular-cli#running-end-to-end-tests)
> Levanta la aplicación UI, va al home y verifica que existan determinados elementos. 

## Proyecto PedidosREST
Proyecto Maven/JAVA que expone servicios (usuario y pedidos) Rest utilizando una cache genérica. 

### Para agregar a IDE
Para agregar el projecto se debe agregar con un proyecto Maven existente y resolver sus dependencias (mvn install).

### Build
1. Instalar [maven](https://maven.apache.org/download.cgi).
2. cd PedidosREST
3. mvn clean install
4. Utilizar PedidosREST/target/PedidosREST.war en cualquier servidor. Aclaración: desarrolado con un apache tomcat versión 7. 

### Tests PedidosREST
Corren en el install del maven y correpsonden a las siguientes clases:
- com.navent.service.impl.PedidoServiceTest.java
- com.navent.service.cache.impl.GenericMemCachedTest.java
