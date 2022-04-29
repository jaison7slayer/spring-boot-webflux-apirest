package com.example.webflux.apirest.app.models.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.webflux.apirest.app.models.documents.Producto;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String> {

}
