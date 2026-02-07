package com.dualpsp.app.repository;

import com.dualpsp.app.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Producto.
 * 
 * Extiende JpaRepository para proporcionar operaciones CRUD
 * básicas sin necesidad de implementación adicional:
 * - findAll(): Obtener todos los productos
 * - findById(Long id): Buscar por ID
 * - save(Producto): Guardar o actualizar
 * - deleteById(Long id): Eliminar por ID
 * - existsById(Long id): Verificar existencia
 * 
 * @author Estudiante FP Dual
 * @version 1.0.0
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // JpaRepository proporciona todos los métodos CRUD básicos
    // Se pueden añadir métodos personalizados si es necesario, por ejemplo:
    // List<Producto> findByNombreContaining(String nombre);
    // List<Producto> findByPrecioBetween(Double min, Double max);

}
