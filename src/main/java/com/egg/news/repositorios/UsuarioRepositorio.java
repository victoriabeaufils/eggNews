package com.egg.news.repositorios;

import com.egg.news.entidades.Periodista;
import com.egg.news.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorUser(@Param("nombreUsuario") String nombreUsuario);

    public boolean existsByNombreUsuario(String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario AND u.rol = 'PERIODISTA'")
    public Usuario buscarPeriodistaPorUser (@Param("nombreUsuario") String nombreUsuario);
    
}
