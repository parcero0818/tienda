package com.cidenet.tienda.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cidenet.tienda.TiendaApplication;
import com.cidenet.tienda.dto.Respuesta;
import com.cidenet.tienda.dto.RespuestaConsultaProductos;
import com.cidenet.tienda.infraestructura.entidades.Producto;
import com.cidenet.tienda.repositorio.ProductoRepositorio;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TiendaApplication.class)
public class ServicioProductoTests {

	ServicioProducto servicioProducto;
	@Mock
	ProductoRepositorio productoRepositorio;

	@Before
	public void configuration() {
		this.servicioProducto = spy(new ServicioProducto(productoRepositorio));
	}

	@Test
	public void sinProductosRegistrados() {
		List<Producto> productos = new ArrayList<>();
		doReturn(productos).when(servicioProducto).encontrarProductos();

		RespuestaConsultaProductos resp = servicioProducto.getProductos();

		assertEquals(resp.getEstado(), "200");
		assertEquals(resp.getProductos(), null);
		verify(servicioProducto, times(1)).encontrarProductos();
	}

	@Test
	public void falloObteniendoProducto() {
		Producto producto = new Producto();
		List<Producto> productos = new ArrayList<>();
		productos.add(producto);
		doReturn(productos).when(servicioProducto).encontrarProductos();

		RespuestaConsultaProductos resp = servicioProducto.getProductos();

		assertEquals(resp.getEstado(), "200");
		assertEquals(resp.getProductos(), productos);
		verify(servicioProducto, times(1)).encontrarProductos();
	}

	@Test
	public void falloGuarandoProducto() {
		Producto producto = new Producto();
		doReturn(null).when(servicioProducto).guardarProducto(producto);

		Respuesta resp = servicioProducto.registrarProducto(producto);

		assertEquals(resp.getEstado(), "500");
		verify(servicioProducto, times(1)).guardarProducto(producto);
	}

	@Test
	public void exitoGuarandoProducto() {
		Producto producto = new Producto();
		Producto productoUno = new Producto();
		doReturn(productoUno).when(servicioProducto).guardarProducto(producto);

		Respuesta resp = servicioProducto.registrarProducto(producto);

		assertEquals(resp.getEstado(), "200");
		verify(servicioProducto, times(1)).guardarProducto(producto);
	}

}
