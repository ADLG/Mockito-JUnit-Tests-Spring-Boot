package com.pruebas.mockito.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bancos")
public class Banco
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nombre;

	@Column(name = "total_transferencias")
	private int totalTransferencias;

	public Banco(){}

	public Banco(Long id, String nombre, int totalTransferencias)
	{
		super();
		this.id=id;
		this.nombre=nombre;
		this.totalTransferencias=totalTransferencias;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTotalTransferencias() {
		return totalTransferencias;
	}

	public void setTotalTransferencias(int totalTransferencias) {
		this.totalTransferencias = totalTransferencias;
	}

	
}
