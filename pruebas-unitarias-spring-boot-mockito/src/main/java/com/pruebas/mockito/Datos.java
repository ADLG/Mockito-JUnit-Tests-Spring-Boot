package com.pruebas.mockito;

import java.math.BigDecimal;
import java.util.Optional;

import com.pruebas.mockito.entity.Banco;
import com.pruebas.mockito.entity.Cuenta;

public class Datos
{
	public static Optional<Cuenta> crearCuenta001()
	{
		return Optional.of(new Cuenta(1L, "ADLG", new BigDecimal("33000")));
	}

	public static Optional<Cuenta> crearCuenta002()
	{
		return Optional.of(new Cuenta(2L, "CJ", new BigDecimal("15000")));
	}

	public static Optional<Banco> crearBanco()
	{
		return Optional.of(new Banco(1L, "El banco del Sol", 0));
	}
}
