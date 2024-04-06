package com.pruebas.mockito.service;

import java.math.BigDecimal;
import java.util.List;

import com.pruebas.mockito.entity.Cuenta;

public interface SvcCuenta
{
	public List<Cuenta> listAll();
	public Cuenta findById(Long id);
	public Cuenta save(Cuenta cuenta);
	public int revisarTotalTransferencias(Long bancoId);
	public BigDecimal revisarSaldo(Long cuentaId);
	public void transferirDinero(Long cuentaOrigen, Long cuentaDestino, BigDecimal monto, Long bancoId);
}
