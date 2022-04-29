package com.example.webflux.apirest.app.models.services;

import com.example.webflux.apirest.app.models.documents.Categoria;
import com.example.webflux.apirest.app.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {
	
	public Flux<Producto> findAll();
	public Flux<Producto> findAllConNombreUpperCase();
	public Flux<Producto> findAllConNombreUpperCaseConRepeat();
	public Mono<Producto> findById(String id);
	public Mono<Producto> save(Producto producto);
	public Mono<Void> delete(Producto producto);
	
	public Flux<Categoria> findAllCategoria();
	public Mono<Categoria> findCategoriaById(String id);
	public Mono<Categoria> saveCategoria(Categoria categoria);

}
