package com.dualpsp.app.service;

import com.dualpsp.app.entity.Producto;
import com.dualpsp.app.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de productos.
 * 
 * Implementa la lógica de negocio para operaciones CRUD
 * sobre la entidad Producto.
 * 
 * @author Estudiante FP Dual
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Obtiene todos los productos de la base de datos.
     * 
     * @return Lista de todos los productos
     */
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su ID.
     * 
     * @param id Identificador del producto
     * @return Optional con el producto si existe
     */
    @Transactional(readOnly = true)
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     * 
     * @param producto Producto a guardar
     * @return Producto guardado con su ID generado
     */
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Actualiza un producto existente.
     * 
     * @param id                  Identificador del producto a actualizar
     * @param productoActualizado Datos actualizados del producto
     * @return Optional con el producto actualizado si existe
     */
    public Optional<Producto> update(Long id, Producto productoActualizado) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(productoActualizado.getNombre());
                    productoExistente.setDescripcion(productoActualizado.getDescripcion());
                    productoExistente.setPrecio(productoActualizado.getPrecio());
                    productoExistente.setStock(productoActualizado.getStock());
                    return productoRepository.save(productoExistente);
                });
    }

    /**
     * Elimina un producto por su ID.
     * 
     * @param id Identificador del producto a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean deleteById(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Verifica si existe un producto con el ID especificado.
     * 
     * @param id Identificador del producto
     * @return true si existe, false en caso contrario
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return productoRepository.existsById(id);
    }

}
