package com.GABC_JRPA.Practica2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("alumnos")
public class Alumno {

    @Id
    private Long id;
    private String nombre;
    private String apellidos;
    private String matricula;
    private String correo;
    private String carrera;

    // Constructor vacío necesario para Spring
    public Alumno() {}

    public Alumno(String nombre, String apellidos, String matricula, String correo, String carrera) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.correo = correo;
        this.carrera = carrera;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
}