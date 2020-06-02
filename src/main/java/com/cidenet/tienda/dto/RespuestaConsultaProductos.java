package com.cidenet.tienda.dto;

import java.util.List;

import com.cidenet.tienda.infraestructura.entidades.Producto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public class RespuestaConsultaProductos extends Respuesta{

	private List<Producto> productos;

	

}
