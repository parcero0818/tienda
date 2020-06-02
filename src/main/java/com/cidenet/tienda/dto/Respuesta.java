package com.cidenet.tienda.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@Data
public class Respuesta {

	private String estado;
	private String mensaje;
		
}
