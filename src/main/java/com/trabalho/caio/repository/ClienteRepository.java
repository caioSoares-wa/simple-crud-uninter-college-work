package com.trabalho.caio.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.trabalho.caio.model.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Long > {
}
