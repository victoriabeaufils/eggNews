/*
El repositorio que persiste a esta entidad (NoticiaRepositorio) debe contener los métodos
necesarios para guardar/actualizar noticias en la base de datos, realizar consultas o dar de baja
según corresponda.
*/

package com.egg.news.repositorios;

import com.egg.news.entidades.Noticia;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, Integer> {

    @Override
    public List<Noticia> findAll(Sort sort);

    public List<Noticia> findByCreador(String id);

}
