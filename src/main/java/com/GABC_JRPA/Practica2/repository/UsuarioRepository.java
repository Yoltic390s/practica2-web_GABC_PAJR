package com.GABC_JRPA.Practica2.repository;

import com.GABC_JRPA.Practica2.model.Usuario;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {


    @Query("SELECT * FROM usuarios WHERE boleta = :boleta AND contrasena = :contrasena")
    Usuario findByBoletaAndContrasena(@Param("boleta") String boleta, @Param("contrasena") String contrasena);

    @Query("SELECT * FROM usuarios WHERE boleta = :boleta")
    Usuario findByBoleta(@Param("boleta") String boleta);
}