package com.GABC_JRPA.Practica2.repository;

import com.GABC_JRPA.Practica2.model.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {
}