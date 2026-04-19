package com.GABC_JRPA.Practica2.repository;

import com.GABC_JRPA.Practica2.model.Alumno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

    /* * APRENDIZAJE: La magia de Spring Data.
     * Al nombrar este método exactamente "countByCarrera", Spring automáticamente
     * crea por detrás la consulta SQL: SELECT COUNT(*) FROM alumnos WHERE carrera = ?
     * No necesitas programar la lógica de la base de datos, ¡Spring la infiere del nombre!
     */
    long countByCarrera(String carrera);
}