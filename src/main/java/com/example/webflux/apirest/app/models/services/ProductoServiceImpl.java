package com.example.webflux.apirest.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webflux.apirest.app.models.repository.CategoriaDao;
import com.example.webflux.apirest.app.models.repository.ProductoDao;
import com.example.webflux.apirest.app.models.documents.Categoria;
import com.example.webflux.apirest.app.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	public ProductoDao dao;
	
	@Autowired
	public CategoriaDao categoriaDao;

	@Override
	public Flux<Producto> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Producto> findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return dao.save(producto);
	}

	@Override
	public Mono<Void> delete(Producto producto) {
		return dao.delete(producto);
	}

	@Override
	public Flux<Producto> findAllConNombreUpperCase() {
		return dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
	}

	@Override
	public Flux<Producto> findAllConNombreUpperCaseConRepeat() {
		return dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		})
		.repeat(6000);
	}

	@Override
	public Flux<Categoria> findAllCategoria() {
		return categoriaDao.findAll();
	}

	@Override
	public Mono<Categoria> findCategoriaById(String id) {
		return categoriaDao.findById(id);
	}

	@Override
	public Mono<Categoria> saveCategoria(Categoria categoria) {
		return categoriaDao.save(categoria);
	}

}
