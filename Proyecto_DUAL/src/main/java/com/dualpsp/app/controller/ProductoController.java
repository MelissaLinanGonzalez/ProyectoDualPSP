package com.dualpsp.app.controller;

import com.dualpsp.app.entity.Producto;
import com.dualpsp.app.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 * 
 * RA4 - Servicios en red: Implementa una API REST con los
 * métodos HTTP estándar (GET, POST, PUT, DELETE).
 * 
 * Endpoints:
 * - GET /api/productos -> Lista todos los productos (USER, ADMIN)
 * - POST /api/productos -> Crea un producto (solo ADMIN)
 * - PUT /api/productos/{id} -> Actualiza un producto (solo ADMIN)
 * - DELETE /api/productos/{id} -> Elimina un producto (solo ADMIN)
 * 
 * @author Estudiante FP Dual
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Obtiene la lista de todos los productos.
     * 
     * Acceso: Usuarios autenticados (USER y ADMIN)
     * 
     * @return Lista de productos
     */
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto por su ID.
     * 
     * Acceso: Usuarios autenticados (USER y ADMIN)
     * 
     * @param id Identificador del producto
     * @return Producto encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo producto.
     * 
     * RA5 - Seguridad: Usa @Valid para validar los datos de entrada.
     * Acceso: Solo ADMIN
     * 
     * @param producto Datos del producto a crear
     * @return Producto creado con código 201
     */
    @PostMapping
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    /**
     * Actualiza un producto existente.
     * 
     * RA5 - Seguridad: Usa @Valid para validar los datos de entrada.
     * Acceso: Solo ADMIN
     * 
     * @param id       Identificador del producto a actualizar
     * @param producto Datos actualizados del producto
     * @return Producto actualizado o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {
        return productoService.update(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un producto por su ID.
     * 
     * Acceso: Solo ADMIN
     * 
     * @param id Identificador del producto a eliminar
     * @return 204 No Content si se eliminó, 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        if (productoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
