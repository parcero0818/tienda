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
import com.cidenet.tienda.dto.RespuestaConsultaMovimientos;
import com.cidenet.tienda.infraestructura.entidades.Movimiento;
import com.cidenet.tienda.servicios.ServicioMovimiento;

@RestController
@RequestMapping("/tienda/movimientos")
public class ComandoMovimiento {
	
	
	ServicioMovimiento servicioMovimiento;
	
	@Autowired
	public ComandoMovimiento(ServicioMovimiento servicioMovimiento) {
		this.servicioMovimiento = servicioMovimiento;
	}
	
	@CrossOrigin
	@GetMapping("/getMovimientos")
	public RespuestaConsultaMovimientos getMovimientos() {
		return servicioMovimiento.getMovimientos();
	}
	
	@CrossOrigin
	@PostMapping(value="/registrarMovimiento",  consumes = "application/json")
	public Respuesta registrarMovimiento(@Validated @RequestBody Movimiento movimiento) {
		return servicioMovimiento.registrarMovimiento(movimiento);
	}

	
}
