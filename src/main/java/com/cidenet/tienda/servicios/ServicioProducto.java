package com.cidenet.tienda.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cidenet.tienda.dto.Respuesta;
import com.cidenet.tienda.dto.RespuestaConsultaProductos;
import com.cidenet.tienda.infraestructura.entidades.Producto;
import com.cidenet.tienda.repositorio.ProductoRepositorio;

@Service
public class ServicioProducto {

	private static final String EXITO = "200";
	private static final String ERROR = "500";
	
	ProductoRepositorio productoRepositorio;
	
	@Autowired
	public ServicioProducto(ProductoRepositorio productoRepositorio) {
		this.productoRepositorio = productoRepositorio;
	}

	public RespuestaConsultaProductos getProductos() {
		RespuestaConsultaProductos respuesta = new RespuestaConsultaProductos();
		try {
			List<Producto> productos = productoRepositorio.findAll();
			if(Optional.ofNullable(productos).isPresent()) {
				respuesta.setEstado(EXITO);
				respuesta.setProductos(productos);
			}else {
				respuesta.setEstado(ERROR);
			}
		}catch (Exception e) {
			respuesta.setEstado(ERROR);
		}
		
		return respuesta;
		
	}

	public Respuesta registrarProducto(@Validated Producto producto) {
		Respuesta respuesta = new Respuesta();
		try {
			Producto prod = productoRepositorio.save(producto);
			if(Optional.ofNullable(prod).isPresent()) {
				respuesta.setEstado(EXITO);
			}else {
				respuesta.setEstado(ERROR);
			}
		}catch (Exception e) {
			respuesta.setEstado(ERROR);
		}
		return respuesta;
	}
	
	
}
