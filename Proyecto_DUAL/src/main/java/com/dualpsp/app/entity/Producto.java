package com.dualpsp.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad Producto para la gestión de productos.
 * 
 * RA5 - Seguridad: Incluye validaciones de datos para garantizar
 * la integridad de la información:
 * - nombre: No puede estar vacío (@NotBlank)
 * - precio: Debe ser positivo (@Positive)
 * - stock: Mínimo 0 unidades (@Min)
 * 
 * @author Estudiante FP Dual
 * @version 1.0.0
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    /**
     * Identificador único del producto.
     * Se genera automáticamente mediante estrategia IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del producto.
     * Validación: No puede estar vacío ni contener solo espacios.
     */
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(nullable = false)
    private String nombre;

    /**
     * Descripción detallada del producto.
     * Campo opcional.
     */
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Precio del producto.
     * Validación: Debe ser un valor positivo (mayor que 0).
     */
    @Positive(message = "El precio debe ser un valor positivo")
    @Column(nullable = false)
    private Double precio;

    /**
     * Stock disponible del producto.
     * Validación: No puede ser negativo (mínimo 0).
     */
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

}
