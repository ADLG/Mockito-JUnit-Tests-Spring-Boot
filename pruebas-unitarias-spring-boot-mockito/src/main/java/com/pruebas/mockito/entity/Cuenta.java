package com.pruebas.mockito.entity;

import java.math.BigDecimal;
import java.util.Objects;

import com.pruebas.mockito.exceptions.DineroInsuficienteException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuentas")
public class Cuenta
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String persona;
	private BigDecimal saldo;

	public Cuenta(){}

	public Cuenta(long id, String persona, BigDecimal saldo) {
		super();
		this.id = id;
		this.persona = persona;
		this.saldo = saldo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	public void realizarDebito(BigDecimal monto)
	{
		BigDecimal nuevoSaldo = this.saldo.subtract(monto);
		if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0)
		{
			throw new DineroInsuficienteException("Dinero insuficiente en la cuenta");
		}
		this.saldo = nuevoSaldo;
	}

	public void realizarCredito(BigDecimal monto)
	{
		this.saldo = saldo.add(monto);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, persona, saldo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		return id == other.id && Objects.equals(persona, other.persona) && Objects.equals(saldo, other.saldo);
	}
	
	
}
