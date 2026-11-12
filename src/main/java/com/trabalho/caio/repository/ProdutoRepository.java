package com.trabalho.caio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trabalho.caio.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
