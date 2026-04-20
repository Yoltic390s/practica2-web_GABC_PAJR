package com.GABC_JRPA.Practica2.repository;

import com.GABC_JRPA.Practica2.model.Cliente;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    @Query("SELECT COUNT(*) FROM clientes WHERE correo IS NOT NULL AND correo != ''")
    long countConCorreo();

    @Query("SELECT COUNT(*) FROM clientes WHERE correo IS NULL OR correo = ''")
    long countSinCorreo();
}