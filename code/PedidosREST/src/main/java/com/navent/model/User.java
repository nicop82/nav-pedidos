package com.navent.model;

public class User {
    private String nombre;
    private String password;
    private String rol;
    private String rolDescripcion;
    
    public User() {
	}
    
	public User(String nombre, String password, String rol, String rolDescripcion) {
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
		this.rolDescripcion = rolDescripcion;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getRolDescripcion() {
		return rolDescripcion;
	}
	public void setRolDescripcion(String rolDescripcion) {
		this.rolDescripcion = rolDescripcion;
	}
}
