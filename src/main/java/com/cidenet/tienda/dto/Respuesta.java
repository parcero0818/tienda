package com.cidenet.tienda.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

	private String estado;
	private String mensaje;
		
}
