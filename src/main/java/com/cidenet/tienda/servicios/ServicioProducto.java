package com.cidenet.tienda.servicios;

import java.util.List;

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

		List<Producto> productos = encontrarProductos();
		if (!productos.isEmpty()) {
			respuesta.setEstado(EXITO);
			respuesta.setProductos(productos);
		} else {
			respuesta.setEstado(EXITO);
		}
		return respuesta;

	}

	public Respuesta registrarProducto(@Validated Producto producto) {
		Respuesta respuesta = new Respuesta();

		Producto prod = guardarProducto(producto);
		if (prod != null) {
			respuesta.setEstado(EXITO);
		} else {
			respuesta.setEstado(ERROR);
		}
		return respuesta;
	}

	public Producto guardarProducto(Producto producto) {
		return productoRepositorio.save(producto);
	}

	public List<Producto> encontrarProductos() {
		return productoRepositorio.findAll();
	}
		
}
