package com.cidenet.tienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cidenet.tienda.modelo.Movimiento;

public interface MovimientoRepositorio extends JpaRepository<Movimiento, Long>{

}
