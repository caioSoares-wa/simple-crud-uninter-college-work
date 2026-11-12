package com.trabalho.caio.controller;

import com.trabalho.caio.repository.ClienteRepository;

import java.time.LocalDate;
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

import com.trabalho.caio.dto.ClienteRequestDTO;
import com.trabalho.caio.model.Cliente;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	private final ClienteRepository clienteRepository;

	public ClienteController(ClienteRepository clienterepository) {
		clienteRepository = clienterepository;
		
	}
	
	//Create
	@PostMapping("")
	public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteRequestDTO cliente){
		
		if(cliente.getNome().isBlank() || cliente.getNome().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}else {
			
			Cliente clienteNovo = new Cliente();
			
			clienteNovo.setNome(cliente.getNome());
			
			LocalDate clienteDesde = LocalDate.now();
			
			clienteNovo.setClienteDesde(clienteDesde);
			
			clienteRepository.saveAndFlush(clienteNovo);
			
			return ResponseEntity.status(201).body(clienteNovo);
		}
	}
	
	
	
	//read (por id)
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id){
		
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
		
		return ResponseEntity.ok(cliente);
	}
	
	//read 
	@GetMapping("")
	public ResponseEntity<List<Cliente>> buscarTodosOsClientes (){
		
		var clientes = clienteRepository.findAll();
				
		if(clientes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(clientes);
		}
	}
	
	
	//update4
	@PutMapping("atualizar")
	public ResponseEntity<Cliente> atualizarCliente (@RequestBody Cliente cliente){
		
		var clienteAtualizar = clienteRepository.findById(cliente.getId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
		
		clienteAtualizar.setNome(cliente.getNome());
		
		clienteRepository.saveAndFlush(clienteAtualizar);
		return ResponseEntity.ok(clienteAtualizar);
	}
	
	
	//Delete
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Cliente> deletarCliente(@PathVariable Long id){
		
		Cliente cliente  = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
		
		clienteRepository.delete(cliente);
		return ResponseEntity.accepted().body(cliente);
	}
	
}

