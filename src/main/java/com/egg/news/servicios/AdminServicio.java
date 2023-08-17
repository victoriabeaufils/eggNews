package com.egg.news.servicios;

import com.egg.news.Enumeraciones.Rol;
import com.egg.news.entidades.Imagen;
import com.egg.news.entidades.Periodista;
import com.egg.news.entidades.Usuario;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.PeriodistaRepositorio;
import com.egg.news.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminServicio {

    @Autowired
    private PeriodistaRepositorio periodistaRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio; 

    public Periodista registrarPeriosita(MultipartFile archivo,String nombreUsuario, String password, String password2, Integer sueldo) throws MiException {
        //nombreUsuario, contraseña, booleano y la fecha de alta, Rol

        validar(nombreUsuario, password, password2, sueldo);

        Periodista periodista = new Periodista();

        periodista.setNombreUsuario(nombreUsuario);
        periodista.setPassword(new BCryptPasswordEncoder().encode(password));
        periodista.setActivo(true);
        periodista.setAlta(new Date());
        periodista.setRol(Rol.PERIODISTA);
        periodista.setSueldoMensual(sueldo);
        periodista.setMisNoticias(new ArrayList());
        
        //Logica para guardar la imagen del usuario!
        Imagen imagen = imagenServicio.guardar(archivo);

        periodista.setImagen(imagen);

       return periodistaRepositorio.save(periodista);

    }

    public void validar(String nombreUsuario, String password, String password2, Integer sueldo) throws MiException {

        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new MiException("El usuario no puede estar vacio o ser nulo");
        }

        if (periodistaRepositorio.existsByNombreUsuario(nombreUsuario)) {
            throw new MiException("El nombre de usuario ya está registrado.");
        }

        if (password == null || password.isEmpty() || password.length() < 5) {
            throw new MiException("La contraseña no puede estar vacia o ser menor a 5 caracteres");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas deben ser iguales");
        }

        if (sueldo == null || sueldo < 0) {
            throw new MiException("El sueldo no puede ser nulo o menor a 0");
        }

    }

    
    
    /*LISTA DE CONTROL*/
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.buscarPorRolUser();

        return usuarios;
    }
    
     public List<Usuario> listarPeriodistas() {
        List<Usuario> periodistas = new ArrayList();

        periodistas = usuarioRepositorio.buscarPorRolPeriodista();

        return periodistas;
    }

    
    
    
    
    /* CAMBIO DE ROL */
    @Transactional
    public void cambiarRol(String id, Rol rol) throws MiException {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();


            if (rol == Rol.PERIODISTA) {

                Periodista periodista = registrarPeriosita((MultipartFile) usuario.getImagen(), usuario.getNombreUsuario(),
                        "123456", "123456", 1000);

                periodistaRepositorio.save(periodista);
                usuarioRepositorio.delete(usuario);// Guardar el nuevo objeto Periodista

            } else {
                usuario.setRol(rol);
                usuarioRepositorio.save(usuario);
            }
        }
    }
    
    
}
