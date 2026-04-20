package com.GABC_JRPA.Practica2.repository;

import com.GABC_JRPA.Practica2.model.Estudiante;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends CrudRepository<Estudiante, Long> {

    @Query("SELECT COUNT(*) FROM estudiantes WHERE carrera = :carrera")
    long countByCarrera(@Param("carrera") String carrera);
}