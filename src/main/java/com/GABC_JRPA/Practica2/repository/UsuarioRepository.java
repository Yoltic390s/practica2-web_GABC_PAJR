package com.GABC_JRPA.Practica2.repository;

import com.GABC_JRPA.Practica2.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que maneja las operaciones de base de datos para los Usuarios usando Spring Data JDBC.
 */
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    // Método mágico de Spring Data para buscar un usuario por boleta y contraseña (para el login)
    Usuario findByBoletaAndContrasena(String boleta, String contrasena);

    // NUEVO: Método para buscar un usuario SOLO por su boleta (para recuperar contraseña)
    Usuario findByBoleta(String boleta);
}