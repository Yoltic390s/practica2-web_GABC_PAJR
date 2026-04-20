package com.GABC_JRPA.Practica2.repository;

import com.GABC_JRPA.Practica2.model.Reserva;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {
}