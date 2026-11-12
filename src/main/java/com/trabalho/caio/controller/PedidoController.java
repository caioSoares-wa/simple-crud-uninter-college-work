package com.trabalho.caio.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalho.caio.dto.PedidoProdutoClienteDTO;
import com.trabalho.caio.model.Pedido;
import com.trabalho.caio.repository.ClienteRepository;
import com.trabalho.caio.repository.PedidoRepository;
import com.trabalho.caio.repository.ProdutoRepository;


@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
	
private final PedidoRepository pedidoRepository;
private final ClienteRepository clienteRepository;
private final ProdutoRepository produtoRepository;
	
	public PedidoController(PedidoRepository pedidorepository, ClienteRepository clienterepository, ProdutoRepository produtorepository) {
		pedidoRepository = pedidorepository;
		clienteRepository = clienterepository;
		produtoRepository = produtorepository;
	}
	
	//Create
	@PostMapping("")
	public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoProdutoClienteDTO pedido){
		
		if(pedido.getQuantidade() <= 0 ) {
			return ResponseEntity.badRequest().build();
		}
		
		var cliente = clienteRepository.findById(pedido.getIdCliente()).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
		var produto = produtoRepository.findById(pedido.getIdProduto()).orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
		
		
		Pedido pedidoNovo = new Pedido();
		pedidoNovo.setCliente(cliente);
		pedidoNovo.setProduto(produto);
		pedidoNovo.setQuantidade(pedido.getQuantidade());
		
		pedidoRepository.save(pedidoNovo);
		
		return ResponseEntity.ok(pedidoNovo);
	}
	
	
	//Read (pelo id)
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscarPedidoPorId (@PathVariable Long id){
		Pedido pedido = pedidoRepository.findById(id).orElseThrow( () -> new RuntimeException("Pedido não encontrado!"));
		return ResponseEntity.ok(pedido);
	}
	

	@GetMapping("")
	public ResponseEntity<List<Pedido>> buscarPedidos(){
		
		var pedidos = pedidoRepository.findAll();
		
		if(pedidos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(pedidos);
		}
	}
	
	//Update 
	@PutMapping("/atualizar")
	public ResponseEntity<Pedido> atualizarPedido(@RequestBody Pedido pedido){

		if(pedido.getQuantidade() == null || pedido.getQuantidade() <= 0) {
			return ResponseEntity.badRequest().build();
		}

		if(pedido.getId() == null || pedido.getCliente() == null || pedido.getProduto() == null) {
			return ResponseEntity.badRequest().build();
		}

		var pedidoAntigo = pedidoRepository.findById(pedido.getId())
				.orElseThrow(() -> new RuntimeException("Pedido não encontrado!"));

		var cliente = clienteRepository.findById(pedido.getCliente().getId())
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

		var produto = produtoRepository.findById(pedido.getProduto().getId())
				.orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

		pedidoAntigo.setCliente(cliente);
		pedidoAntigo.setProduto(produto);
		pedidoAntigo.setQuantidade(pedido.getQuantidade());

		pedidoRepository.save(pedidoAntigo);

		return ResponseEntity.ok(pedidoAntigo);
	}
	
	//Delete (por id)
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Pedido> deletarPedidoPorId(@PathVariable Long id){
		
		Pedido pedido = pedidoRepository.findById(id).orElseThrow(()-> new RuntimeException("Pedido não encontrado!"));
		
		pedidoRepository.delete(pedido);
		
		
		return ResponseEntity.accepted().body(pedido);
	}
}

