package com.cidenet.tienda.controller.comandos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cidenet.tienda.dto.Respuesta;
import com.cidenet.tienda.dto.RespuestaConsultaProductos;
import com.cidenet.tienda.infraestructura.entidades.Producto;
import com.cidenet.tienda.servicios.ServicioProducto;

@RestController
@RequestMapping("/tienda/productos")
public class ComandoProducto {
	

	ServicioProducto servicioProducto;
	
	@Autowired
	public ComandoProducto(ServicioProducto servicioProducto) {
		this.servicioProducto = servicioProducto;
	}

	@CrossOrigin
	@GetMapping("/getProductos")
	public RespuestaConsultaProductos getProductos() {
		return servicioProducto.getProductos();
	}

	@CrossOrigin
	@PostMapping(value="/registrarProducto", consumes = "application/json")
	public Respuesta registrarProducto(@RequestBody @Validated Producto producto) {
		return servicioProducto.registrarProducto(producto);
	}
	
}
