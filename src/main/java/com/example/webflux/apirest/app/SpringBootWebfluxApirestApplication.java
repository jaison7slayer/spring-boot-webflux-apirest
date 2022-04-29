package com.example.webflux.apirest.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.example.webflux.apirest.app.models.documents.Categoria;
import com.example.webflux.apirest.app.models.documents.Producto;
import com.example.webflux.apirest.app.models.services.ProductoServiceImpl;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApirestApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApirestApplication.class);

	@Autowired
	private ProductoServiceImpl service;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApirestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		mongoTemplate.dropCollection("categorias").subscribe();

		Categoria inalambrico = new Categoria("Inalámbrico");
		Categoria portatiles = new Categoria("Portátiles");
		Categoria escritorio = new Categoria("Escritorio");
		Categoria gamers = new Categoria("Gamers");

		Flux.just(inalambrico, portatiles, escritorio, gamers).flatMap(service::saveCategoria).doOnNext(c -> {
			log.info("Categoria creada: " + c.getNombre() + ", Id: " + c.getId());
		}).thenMany(Flux.just(
				new Producto("Mouse inalambrico", 2.5, inalambrico),
				new Producto("Keyboard cable", 7.5, escritorio), 
				new Producto("Monitor LCD", 2.5, escritorio),
				new Producto("CPU gamer", 500.5, gamers), 
				new Producto("USB 3.0", 15.25, escritorio),
				new Producto("Mousepad 20x10", 10.5, escritorio), 
				new Producto("Laptop DELL", 1000.5, portatiles),
				new Producto("Charger for laptop", 50.5, escritorio),
				new Producto("Disco duro externo", 80.5, portatiles)).flatMap(producto -> {
					producto.setCreateAt(new Date());
					return service.save(producto);
				})).subscribe(dato -> log.info("Insert into: " + dato.getId() + " " + dato.getNombre()));
	}

}
