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


import com.trabalho.caio.model.Produto;
import com.trabalho.caio.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

	private final ProdutoRepository produtoRepository;
	
	public ProdutoController(ProdutoRepository produtorepository) {
		produtoRepository = produtorepository;
	}
	
	//Create
	@PostMapping("")
	public ResponseEntity<Produto> criarProduto(@RequestBody Produto novoProduto){
		var produto = produtoRepository.save(novoProduto);
		
		return ResponseEntity.ok(produto);
	}
	
	//Read (por id)
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id){
		var produto = produtoRepository.findById(id);
		
		if(produto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			Produto produtoPego = produto.get();
			return ResponseEntity.ok(produtoPego);
		}
	}
	
	//Read (Todos)
	@GetMapping()
	public  ResponseEntity<List<Produto>> buscarTodos(){
		var produtos = produtoRepository.findAll();
		
		if(produtos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(produtos);
		}
	}
	
	//Update
	@PutMapping("/atualizar")
	public ResponseEntity<Produto> atualizarProduto(@RequestBody Produto produto){
		
		Produto produtoId = produtoRepository.findById(produto.getId()).orElseThrow(() -> new RuntimeException("Registro não encontrado"));
		produtoId.setId(produto.getId());
		produtoId.setNome(produto.getNome());
		produtoId.setPreco(produto.getPreco());
		produtoId.setEmEstoque(produto.getEmEstoque());
		
		Produto produtoSalvo;
		
		try {
			 produtoSalvo=produtoRepository.save(produtoId);
		} catch (Exception e) {
			throw new RuntimeException("Falha ao salvar o produto!");
		}
		
		return ResponseEntity.ok(produtoSalvo);
	}
	
	
	//Delete
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Produto> removerProduto(@PathVariable Long id){
		
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado."));
		
		produtoRepository.delete(produto);
		return ResponseEntity.noContent().build();
	
	}
	
	
}
