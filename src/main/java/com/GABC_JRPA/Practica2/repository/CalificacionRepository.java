package com.GABC_JRPA.Practica2.repository;

import com.GABC_JRPA.Practica2.model.Calificacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepository extends CrudRepository<Calificacion, Long> {
}