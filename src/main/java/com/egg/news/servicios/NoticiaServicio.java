/*
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias para
administrar noticias (consulta, creación, modificación y dar de baja).
 */
package com.egg.news.servicios;

import com.egg.news.Enumeraciones.Rol;
import com.egg.news.entidades.Noticia;
import com.egg.news.entidades.Periodista;
import com.egg.news.entidades.Usuario;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.NoticiaRepositorio;
import com.egg.news.repositorios.PeriodistaRepositorio;
import com.egg.news.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @Autowired
    private PeriodistaRepositorio periodistaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo) throws MiException {
        verificar(titulo, cuerpo);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        Usuario usuario = usuarioRepositorio.buscarPeriodistaPorUser(nombreUsuario);

        if (usuario != null && usuario.getRol() == Rol.PERIODISTA) {
            Periodista periodista = (Periodista) usuario;
            Noticia noticia = new Noticia();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setPublicacion(new Date());
            noticia.setCreador(periodista);
            periodista.getMisNoticias().add(noticia);
            noticiaRepositorio.save(noticia);
            periodistaRepositorio.save(periodista);
        } else {
            System.out.println("La noticia no pudo ser cargada");
        }

    }

    @Transactional
    public void modificarNoticia(String titulo, String cuerpo, Integer id) throws MiException {
        verificar(titulo, cuerpo);
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticiaRepositorio.save(noticia);
        }
    }

    public List<Noticia> listaNoticias() {
        List<Noticia> noticias = new ArrayList();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return noticiaRepositorio.findAll(sort);
    }

    @Transactional
    public void eliminarNoticia(Integer id) {
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            noticiaRepositorio.deleteById(id);
        }

    }

    public Noticia getOne(Integer id) {
        return noticiaRepositorio.getOne(id);
    }

    public void verificar(String titulo, String cuerpo) throws MiException {
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no puede estar vacio o ser nulo");
        }
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("El cuerpo no puede estar vacio o ser nulo");
        }
    }

    public List<Noticia> listarNoticiasPorAutor(String id) {
        return noticiaRepositorio.findByCreador(id);
    }

}
