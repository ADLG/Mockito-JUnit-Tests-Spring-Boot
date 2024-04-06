package com.pruebas.mockito.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebas.mockito.entity.Banco;
import com.pruebas.mockito.entity.Cuenta;
import com.pruebas.mockito.repository.RepoBanco;
import com.pruebas.mockito.repository.RepoCuenta;



@Service
public class SvcCuentaImp implements SvcCuenta
{
	@Autowired
	private RepoCuenta repoCuenta;

	@Autowired
	private RepoBanco repoBanco;

	@Override
	@Transactional(readOnly = true)
	public List<Cuenta> listAll()
	{
		return repoCuenta.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cuenta findById(Long id)
	{
		return repoCuenta.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public Cuenta save(Cuenta cuenta)
	{
		return repoCuenta.save(cuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public int revisarTotalTransferencias(Long bancoId)
	{
		Banco banco = repoBanco.findById(bancoId).orElseThrow();
		return banco.getTotalTransferencias();
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal revisarSaldo(Long cuentaId)
	{
		Cuenta cuenta = repoCuenta.findById(cuentaId).orElseThrow();
		return cuenta.getSaldo();
	}

	@Override
	public void transferirDinero(Long cuentaOrigen, Long cuentaDestino, BigDecimal monto, Long bancoId)
	{
		Cuenta NoCuentaOrigen = repoCuenta.findById(cuentaOrigen).orElseThrow();
		NoCuentaOrigen.realizarDebito(monto);
		repoCuenta.save(NoCuentaOrigen);

		Cuenta NoCuentaDestino = repoCuenta.findById(cuentaDestino).orElseThrow();
		NoCuentaDestino.realizarCredito(monto);
		repoCuenta.save(NoCuentaDestino);

		Banco banco = repoBanco.findById(bancoId).orElseThrow();
		int totalTransferencias = banco.getTotalTransferencias();
		banco.setTotalTransferencias(++totalTransferencias);
		repoBanco.save(banco);
	}


}
