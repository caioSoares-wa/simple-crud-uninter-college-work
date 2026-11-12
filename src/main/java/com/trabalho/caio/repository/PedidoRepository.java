package com.trabalho.caio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trabalho.caio.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
