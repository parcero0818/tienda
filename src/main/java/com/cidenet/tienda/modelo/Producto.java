package com.cidenet.tienda.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Producto {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter(value = AccessLevel.PUBLIC)
	private int id;
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private String nombre;
	@Getter(value = AccessLevel.PUBLIC)
	@Setter(value = AccessLevel.PUBLIC)
	private String descripcion;

	

}
