package com.example.webflux.apirest.app.controllers;

import java.net.URI;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webflux.apirest.app.models.documents.Producto;
import com.example.webflux.apirest.app.models.services.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoService service;
	
	/*SOLO POR PRUEBAS GITHUB CON ACTIONS*/
	@GetMapping("/test")
	public String listarTest() {
		return "Hola, testeando Github Actions con Azure!";
	}

    /*PRUEBA PARA PROBAR HARD RESET*/
    @GetMapping("/test2")
    public String listarTest2() {
        return "Hola, testeando Github Actions con Azure!";
    }

	/*PRUEBA REVERT*/
	@GetMapping("/test3")
	public String listarTest3() {
		return "Hola, testeando Github Actions con Azure PARA REVERT!";
	}
	
	@GetMapping("/test3")
	public String listarTest2() {
		return "Hola, testeando para PR prueba nueva!";
	}

	// ESTA ES LA FORMA MAS SIMPLE Y FACIL
	/*@GetMapping	
	 * public Flux<Producto> listar(){ return service.findAll(); }
	 */
	// ESTA ES LA FORMA CON MONO Y RESPONSEENTITY
	public Mono<ResponseEntity<Flux<Producto>>> listar() {
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON) // APPLICATION_JSON_UTF8
				.body(service.findAll()));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Producto>> ver(@PathVariable String id) {
		return service.findById(id).map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<Producto>> crear(@Valid @RequestBody Producto producto) {

		if (producto.getCreateAt() == null) {
			producto.setCreateAt(new Date());
		}

		return service.save(producto).map(p -> ResponseEntity.created(URI.create("/api/productos/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(p));
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Producto>> editar(@RequestBody Producto producto, @PathVariable String id) {
		return service.findById(id).flatMap(p -> {
			p.setNombre(producto.getNombre());
			p.setPrecio(producto.getPrecio());
			p.setCategoria(producto.getCategoria());
			return service.save(p);
		}).map(p -> ResponseEntity.created(URI.create("/api/productos/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
		return service.findById(id).flatMap(p -> {
			return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
