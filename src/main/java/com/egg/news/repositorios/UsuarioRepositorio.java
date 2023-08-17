package com.egg.news.repositorios;

import com.egg.news.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    //Query muy muy importante para el login!!!!
    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorUser(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'USER'")
    List<Usuario> buscarPorRolUser();

    public boolean existsByNombreUsuario(String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'PERIODISTA'")
    List<Usuario> buscarPorRolPeriodista();

}
