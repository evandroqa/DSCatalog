package com.devsuperior.DSCatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.DSCatalog.dto.CategoryDTO;
import com.devsuperior.DSCatalog.entities.Category;
import com.devsuperior.DSCatalog.repositories.CategoryRepository;
import com.devsuperior.DSCatalog.services.exceptions.DatabaseException;
import com.devsuperior.DSCatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		return repository.findAll().stream().map(x -> new CategoryDTO(x)).toList();
	}

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaginado(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Não Encontrado - (classe: CategoryService)"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		repository.save(entity);
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
		    Category entity = repository.getReferenceById(id);
		    entity.setId(id); 
		    entity.setName(dto.getName());
		    return new CategoryDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Categoria não encontrado");
		}
		try {
	        repository.deleteById(id);    		
		} catch (DataIntegrityViolationException e) {
	        throw new DatabaseException("Falha de integridade referencial");
	   	}
	}

}
