package com.cidenet.tienda.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@Entity
public class Movimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	//@JoinColumn(name = "id")
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private Producto idProducto;
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private Date fecha;
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private String tipo;
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private int cantidad;
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private int saldo;
		
}
