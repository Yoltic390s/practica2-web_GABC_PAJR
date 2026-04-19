package com.GABC_JRPA.Practica2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad que representa la tabla "usuarios" en la base de datos.
 */
@Table("usuarios")
public class Usuario {

    @Id
    private Long id;
    private String nombre;
    private String boleta;
    private String contrasena;

    // Constructores
    public Usuario() {}

    public Usuario(String nombre, String boleta, String contrasena) {
        this.nombre = nombre;
        this.boleta = boleta;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getBoleta() { return boleta; }
    public void setBoleta(String boleta) { this.boleta = boleta; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}