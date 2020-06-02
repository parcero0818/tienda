package com.cidenet.tienda.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cidenet.tienda.dto.Respuesta;
import com.cidenet.tienda.dto.RespuestaConsultaMovimientos;
import com.cidenet.tienda.infraestructura.entidades.Movimiento;
import com.cidenet.tienda.infraestructura.entidades.Producto;
import com.cidenet.tienda.repositorio.MovimientoRepositorio;
import com.cidenet.tienda.repositorio.ProductoRepositorio;

@Service
public class ServicioMovimiento {
	
	private static final  String VENTA = "VENTA";
	private static final String EXITO = "200";
	private static final String ERROR = "500";
	private static final String NODISPONIBLE = "100";
	private static final String MSJNODISPONIBLE = "La cantidad de compra supera las disponibles";
	
	MovimientoRepositorio movimientoRepositorio;
	ProductoRepositorio productoRepositorio;
	
	@Autowired
	public ServicioMovimiento(MovimientoRepositorio movimientoRepositorio, ProductoRepositorio productoRepositorio) {
		this.movimientoRepositorio = movimientoRepositorio;
		this.productoRepositorio = productoRepositorio;
	}

	public RespuestaConsultaMovimientos getMovimientos() {
		RespuestaConsultaMovimientos respuesta = new RespuestaConsultaMovimientos();
		try {
			List<Movimiento> movimientos = movimientoRepositorio.findAll();
			if(Optional.ofNullable(movimientos).isPresent()) {
				respuesta.setEstado(EXITO);
				respuesta.setMovimientos(movimientos);
			}else {
				respuesta.setEstado(ERROR);
			}
		}catch (Exception e) {
			respuesta.setEstado(ERROR);
		}
		return respuesta;
	}
	
	public Respuesta registrarMovimiento(Movimiento movimiento) {
		Respuesta respuesta = new Respuesta();
		try {
			return validarMovimiento(movimiento);
		}catch (Exception e) {
			respuesta.setEstado(ERROR);
			return respuesta;
		}
	}
	
	public Respuesta validarMovimiento(Movimiento movimiento) {
		Respuesta respuesta = new Respuesta();
		Producto producto = encontrarProducto(movimiento);
		if(producto != null) {
			return actualizarMovimiento(movimiento, producto);
		}else {
			respuesta.setEstado(ERROR);
			return respuesta;
		}
	}
	
	public Respuesta actualizarMovimiento(Movimiento movimiento, Producto producto){
		Respuesta respuesta = new Respuesta();
		if(isVenta(movimiento)) {
			boolean disponible = cantidadDisponible(movimiento, producto);
			if(disponible) {
				return guardarYactualizar(movimiento, producto);
			}else {
				respuesta.setEstado(NODISPONIBLE);
				respuesta.setMensaje(MSJNODISPONIBLE);
				return respuesta;
			}
		}else {
			return guardarYactualizar(movimiento, producto);
		}
	}
	
	public boolean isVenta(Movimiento movimiento) {
		return movimiento.getTipo().equalsIgnoreCase(VENTA);
	}
	
	public Producto encontrarProducto(Movimiento movimiento){
		Long idProd = movimiento.getProducto().getId();
		Optional<Producto> producto = productoRepositorio.findById(idProd);
			
		return producto.isPresent() ? producto.get() : null;
	}
	
	public boolean cantidadDisponible(Movimiento movimiento, Producto producto){
		return producto.getCantidadDisponible() >= movimiento.getCantidad() && producto.getCantidadDisponible() > 0;
	}
	
	public boolean guardarMovimiento(Movimiento movimiento){
		movimiento.setFecha(new Date());
		return Optional.ofNullable(movimientoRepositorio.save(movimiento)).isPresent();
	}
	
	public int cantidadNueva(Movimiento movimiento, int cantidadActual) {
		if(isVenta(movimiento)) {
			return cantidadActual - movimiento.getCantidad();
		}
		return cantidadActual + movimiento.getCantidad();
	}
	
	public Respuesta guardarYactualizar(Movimiento movimiento, Producto producto) {
		Respuesta respuesta = new Respuesta();
		if(guardarMovimiento(movimiento)) {
			int cantidadNueva = cantidadNueva(movimiento, producto.getCantidadDisponible());
			producto.setCantidadDisponible(cantidadNueva);
			String res = actualizarProducto(producto) ? EXITO : ERROR;
			respuesta.setEstado(res);
		}
		return respuesta;
	}
	
	public boolean actualizarProducto(Producto producto){
		return Optional.ofNullable(productoRepositorio.save(producto)).isPresent();
	}
	
}
