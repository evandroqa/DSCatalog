package com.devsuperior.DSCatalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.DSCatalog.dto.CategoryDTO;
import com.devsuperior.DSCatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> lista = service.findAll();
		return ResponseEntity.ok(lista);
	}	
	
	@GetMapping(value= "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	// Teste inicial ...
	/* @GetMapping
	public ResponseEntity<List<Category>> findTeste() {
		List<Category> lista = new ArrayList<>();
		lista.add(new Category(1L, "Books"));
		lista.add(new Category(3L, "Eletronics"));
		lista.add(new Category(4L, "Magazines"));
		return ResponseEntity.ok(lista);
	}*/	
}
