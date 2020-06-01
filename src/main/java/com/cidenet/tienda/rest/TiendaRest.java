package com.cidenet.tienda.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cidenet.tienda.modelo.Movimiento;
import com.cidenet.tienda.modelo.Producto;
import com.cidenet.tienda.modelo.normal;
import com.cidenet.tienda.repositorio.MovimientoRepositorio;
import com.cidenet.tienda.repositorio.ProductoRepositorio;

@RestController
@RequestMapping("/tienda")
public class TiendaRest {
	
	MovimientoRepositorio movimientoRepositorio;
	ProductoRepositorio productoRepositorio;
	
	@Autowired
	public TiendaRest(MovimientoRepositorio movimientoRepositorio, ProductoRepositorio productoRepositorio) {
		this.movimientoRepositorio = movimientoRepositorio;
		this.productoRepositorio = productoRepositorio;
	}

	@CrossOrigin
	@GetMapping("/productos")
	public List<Producto> getProductos() {
		return productoRepositorio.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/movimientos")
	public List<Movimiento> getMovimientos() {
		return movimientoRepositorio.findAll();
	}
	
	@CrossOrigin
	@PostMapping(value="/registrarMovimiento",  consumes = "application/json")
	public Movimiento registrarMovimiento(@RequestBody Movimiento movimiento) {
		System.out.println(movimiento.getProducto());
		return movimientoRepositorio.save(movimiento);
	}

	@CrossOrigin
	@PostMapping("/registrarProducto")
	public Producto registrarProducto(@RequestBody Producto producto) {
		return productoRepositorio.save(producto);
	}
	
}
