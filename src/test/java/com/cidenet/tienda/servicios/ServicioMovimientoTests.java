package com.cidenet.tienda.servicios;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cidenet.tienda.TiendaApplication;
import com.cidenet.tienda.dto.Respuesta;
import com.cidenet.tienda.infraestructura.entidades.Movimiento;
import com.cidenet.tienda.infraestructura.entidades.Producto;
import com.cidenet.tienda.repositorio.MovimientoRepositorio;
import com.cidenet.tienda.repositorio.ProductoRepositorio;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = TiendaApplication.class)
public class ServicioMovimientoTests {

	ServicioMovimiento servicioMovimiento;
	@Mock
	MovimientoRepositorio movimientoRepositorio;
	@Mock
	ProductoRepositorio productoRepositorio;

	@Before
	public void configuration() {
		this.servicioMovimiento = spy(new ServicioMovimiento(movimientoRepositorio, productoRepositorio));
	}

	@Test
	public void registroFallidoDeUnMovimientoCuandoNoEncuentraProducto() {
		doReturn(null).when(servicioMovimiento).encontrarProducto(any());

		Respuesta resp = servicioMovimiento.registrarMovimiento(any());

		verify(servicioMovimiento, times(1)).encontrarProducto(any());
		assertEquals(resp.getEstado(), "500");
	}

	@Test
	public void registroFallidoDeUnMovimientoCuandoNoActualiza() {
		Respuesta respuesta = new Respuesta("500", "");
		Producto producto = new Producto();
		doReturn(producto).when(servicioMovimiento).encontrarProducto(any());
		doReturn(respuesta).when(servicioMovimiento).actualizarMovimiento(any(), any());

		Respuesta resp = servicioMovimiento.registrarMovimiento(any());

		verify(servicioMovimiento, times(1)).encontrarProducto(any());
		verify(servicioMovimiento, times(1)).actualizarMovimiento(any(), any());
		assertEquals(resp.getEstado(), "500");
	}

	@Test
	public void registroExitosoDeUnMovimiento() {
		Respuesta respuesta = new Respuesta("200", "");
		Producto producto = new Producto();
		doReturn(producto).when(servicioMovimiento).encontrarProducto(any());
		doReturn(respuesta).when(servicioMovimiento).actualizarMovimiento(any(), any());

		Respuesta resp = servicioMovimiento.registrarMovimiento(any());

		verify(servicioMovimiento, times(1)).encontrarProducto(any());
		verify(servicioMovimiento, times(1)).actualizarMovimiento(any(), any());
		assertEquals(resp.getEstado(), "200");
	}

	@Test
	public void actualizacionFallidaDeCompra() {
		Respuesta respuesta = new Respuesta("500", "");
		doReturn(false).when(servicioMovimiento).esVenta(any());
		doReturn(respuesta).when(servicioMovimiento).guardarYactualizar(any(), any());

		Respuesta resp = servicioMovimiento.actualizarMovimiento(any(), any());

		verify(servicioMovimiento, times(1)).esVenta(any());
		verify(servicioMovimiento, times(1)).guardarYactualizar(any(), any());
		assertEquals(resp.getEstado(), "500");
	}

	@Test
	public void actualizacionExitosaDeCompra() {
		Respuesta respuesta = new Respuesta("200", "");
		doReturn(false).when(servicioMovimiento).esVenta(any());
		doReturn(respuesta).when(servicioMovimiento).guardarYactualizar(any(), any());

		Respuesta resp = servicioMovimiento.actualizarMovimiento(any(), any());

		verify(servicioMovimiento, times(1)).esVenta(any());
		verify(servicioMovimiento, times(1)).guardarYactualizar(any(), any());
		assertEquals(resp.getEstado(), "200");
	}

	@Test
	public void actualizacionFallidaDeVentaPorFaltaDeProductos() {
		doReturn(true).when(servicioMovimiento).esVenta(any());
		doReturn(false).when(servicioMovimiento).cantidadDisponible(any(), any());

		Respuesta resp = servicioMovimiento.actualizarMovimiento(any(), any());

		verify(servicioMovimiento, times(1)).esVenta(any());
		verify(servicioMovimiento, times(1)).cantidadDisponible(any(), any());
		verify(servicioMovimiento, times(0)).guardarYactualizar(any(), any());
		assertEquals(resp.getEstado(), "100");
	}

	@Test
	public void actualizacionFallidaDeVenta() {
		doReturn(true).when(servicioMovimiento).esVenta(any());
		doReturn(false).when(servicioMovimiento).cantidadDisponible(any(), any());

		Respuesta resp = servicioMovimiento.actualizarMovimiento(any(), any());

		verify(servicioMovimiento, times(1)).esVenta(any());
		verify(servicioMovimiento, times(1)).cantidadDisponible(any(), any());
		verify(servicioMovimiento, times(0)).guardarYactualizar(any(), any());
		assertEquals(resp.getEstado(), "100");
	}

	@Test
	public void actualizacionFallidaDeVentaPorErrorEnActualizacion() {
		Respuesta respuesta = new Respuesta("500", "");
		doReturn(true).when(servicioMovimiento).esVenta(any());
		doReturn(true).when(servicioMovimiento).cantidadDisponible(any(), any());
		doReturn(respuesta).when(servicioMovimiento).guardarYactualizar(any(), any());

		Respuesta resp = servicioMovimiento.actualizarMovimiento(any(), any());

		verify(servicioMovimiento, times(1)).esVenta(any());
		verify(servicioMovimiento, times(1)).cantidadDisponible(any(), any());
		verify(servicioMovimiento, times(1)).guardarYactualizar(any(), any());
		assertEquals(resp.getEstado(), "500");
	}

	@Test
	public void actualizacionExitosaDeVenta() {
		Respuesta respuesta = new Respuesta("200", "");
		doReturn(true).when(servicioMovimiento).esVenta(any());
		doReturn(true).when(servicioMovimiento).cantidadDisponible(any(), any());
		doReturn(respuesta).when(servicioMovimiento).guardarYactualizar(any(), any());

		Respuesta resp = servicioMovimiento.actualizarMovimiento(any(), any());

		verify(servicioMovimiento, times(1)).esVenta(any());
		verify(servicioMovimiento, times(1)).cantidadDisponible(any(), any());
		verify(servicioMovimiento, times(1)).guardarYactualizar(any(), any());
		assertEquals(resp.getEstado(), "200");
	}

	@Test
	public void esVenta() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 0);

		boolean result = servicioMovimiento.esVenta(movimiento);

		assertTrue(result);
	}

	@Test
	public void noEsVenta() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "compra", 0);

		boolean result = servicioMovimiento.esVenta(movimiento);

		assertFalse(result);
	}

	@Test
	public void sinUnidadesDelProducto() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 2);
		Producto producto = new Producto(Long.valueOf(10), "", "", 0);

		boolean result = servicioMovimiento.cantidadDisponible(movimiento, producto);

		assertFalse(result);
	}

	@Test
	public void cantidadUnidadesProductoMenorALaVenta() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 2);
		Producto producto = new Producto(Long.valueOf(10), "", "", 1);

		boolean result = servicioMovimiento.cantidadDisponible(movimiento, producto);

		assertFalse(result);
	}

	@Test
	public void cantidadUnidadesProductoMayorALaVenta() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 2);
		Producto producto = new Producto(Long.valueOf(10), "", "", 3);

		boolean result = servicioMovimiento.cantidadDisponible(movimiento, producto);

		assertTrue(result);
	}

	@Test
	public void cantidadUnidadesProductoIgualALaVenta() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 2);
		Producto producto = new Producto(Long.valueOf(10), "", "", 3);

		boolean result = servicioMovimiento.cantidadDisponible(movimiento, producto);

		assertTrue(result);
	}

	@Test
	public void cantidadProductosDespuesDeAdquirirUnidades() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 2);
		doReturn(false).when(servicioMovimiento).esVenta(any());
		int cantidadActual = 10;

		int result = servicioMovimiento.cantidadNueva(movimiento, cantidadActual);

		assertEquals(result, 12);
		verify(servicioMovimiento, times(1)).esVenta(any());
	}

	@Test
	public void cantidadProductosDespuesDeVenderUnidades() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 2);
		doReturn(true).when(servicioMovimiento).esVenta(any());
		int cantidadActual = 10;

		int result = servicioMovimiento.cantidadNueva(movimiento, cantidadActual);

		assertEquals(result, 8);
		verify(servicioMovimiento, times(1)).esVenta(any());
	}

	@Test
	public void errorEnGuardarMovimiento() {
		doReturn(false).when(servicioMovimiento).guardarMovimiento(any());

		Respuesta resp = servicioMovimiento.guardarYactualizar(any(), any());

		assertEquals(resp.getEstado(), "500");
		verify(servicioMovimiento, times(1)).guardarMovimiento(any());
	}

	@Test
	public void errorFallaActualizandoMovimiento() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 2);
		Producto producto = new Producto(Long.valueOf(10), "", "", 1);
		doReturn(10).when(servicioMovimiento).cantidadNueva(movimiento, producto.getCantidadDisponible());
		doReturn(true).when(servicioMovimiento).guardarMovimiento(any());
		doReturn(false).when(servicioMovimiento).actualizarProducto(any());

		Respuesta resp = servicioMovimiento.guardarYactualizar(movimiento, producto);

		assertEquals(resp.getEstado(), "500");
		verify(servicioMovimiento, times(1)).guardarMovimiento(any());
		verify(servicioMovimiento, times(1)).actualizarProducto(any());
	}

	@Test
	public void exitoActualizandoMovimiento() {
		Movimiento movimiento = new Movimiento(Long.valueOf(10), null, null, "venta", 2);
		Producto producto = new Producto(Long.valueOf(10), "", "", 1);
		doReturn(true).when(servicioMovimiento).guardarMovimiento(any());
		doReturn(10).when(servicioMovimiento).cantidadNueva(movimiento, producto.getCantidadDisponible());
		doReturn(true).when(servicioMovimiento).actualizarProducto(any());

		Respuesta resp = servicioMovimiento.guardarYactualizar(movimiento, producto);

		assertEquals(resp.getEstado(), "200");
		verify(servicioMovimiento, times(1)).guardarMovimiento(any());
		verify(servicioMovimiento, times(1)).actualizarProducto(any());
	}

}
