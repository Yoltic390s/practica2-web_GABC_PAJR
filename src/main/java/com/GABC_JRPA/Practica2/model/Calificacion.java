package com.GABC_JRPA.Practica2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("estudiantes_cursos")
public class Calificacion {
    @Id
    private Long id;

    @Column("estudiante_id")
    private Long estudianteId;

    @Column("curso_id")
    private Long cursoId;

    private String semestre;
    private Double calificacion;

    public Calificacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }

    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public Double getCalificacion() { return calificacion; }
    public void setCalificacion(Double calificacion) { this.calificacion = calificacion; }
}