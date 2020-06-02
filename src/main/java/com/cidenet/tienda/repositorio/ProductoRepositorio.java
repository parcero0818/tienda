package com.cidenet.tienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cidenet.tienda.infraestructura.entidades.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

}
