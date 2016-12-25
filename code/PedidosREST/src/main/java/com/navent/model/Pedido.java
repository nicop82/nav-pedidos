package com.navent.model;

public class Pedido {
	private Integer id;
	private String nombre;
	private Double monto;
	private Float descuento;
	
	public Pedido() {
	}
	
	public Pedido(Integer id, String nombre, Double monto, Float descuento) {
		this.id = id;
		this.nombre = nombre;
		this.monto = monto;
		this.descuento = descuento;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public Float getDescuento() {
		return descuento;
	}
	public void setDescuento(Float descuento) {
		this.descuento = descuento;
	}
}
