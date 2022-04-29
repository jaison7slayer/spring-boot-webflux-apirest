package com.example.webflux.apirest.app.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.webflux.apirest.app.models.documents.Categoria;

public interface CategoriaDao extends ReactiveMongoRepository<Categoria, String> {

}
