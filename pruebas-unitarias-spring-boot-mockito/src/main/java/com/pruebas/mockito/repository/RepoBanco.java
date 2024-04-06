package com.pruebas.mockito.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.mockito.entity.Banco;

public interface RepoBanco extends JpaRepository<Banco, Long>{

}
