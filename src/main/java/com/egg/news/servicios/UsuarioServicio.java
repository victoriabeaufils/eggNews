package com.egg.news.servicios;

import com.egg.news.Enumeraciones.Rol;
import com.egg.news.entidades.Imagen;
import com.egg.news.entidades.Usuario;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombreUsuario, String password, String password2) throws MiException {
        //nombreUsuario, contraseña, booleano y la fecha de alta, Rol

        validar(nombreUsuario, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setActivo(true);
        usuario.setAlta(new Date());
        usuario.setRol(Rol.USER);

        //Logica para guardar la imagen del usuario!
        Imagen imagen = imagenServicio.guardar(archivo);

        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);

    }

    @Transactional
    public void actualizar(MultipartFile archivo,  String idUsuario, String nombreUsuario, String password, String password2) throws MiException {

        validar(nombreUsuario, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            
            Usuario usuario = respuesta.get();
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuario.setRol(Rol.USER);

            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario);
        }

    }

    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    public void validar(String nombreUsuario, String password, String password2) throws MiException {

        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new MiException("El usuario no puede estar vacio o ser nulo");
        }

        if (usuarioRepositorio.existsByNombreUsuario(nombreUsuario)) {
            throw new MiException("El nombre de usuario ya está registrado.");
        }

        if (password == null || password.isEmpty() || password.length() < 5) {
            throw new MiException("La contraseña no puede estar vacia o ser menor a 5 caracteres");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas deben ser iguales");
        }

    }

    //Metodo propio de UserDetailService.
    //Logear un usuario
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorUser(nombreUsuario);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());

            permisos.add(p);

            //Contexto para la sesión del usuario. 
            //Gracias a estas tres lineas vamos a poder trabajar con la sesion logueada del usuario. 
            //Nos guarda el usuario para poder usarla donde nesesitenmos sin necesidad de hacerlo viajar por la URL. 
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuarioSession", usuario);

            return new User(usuario.getNombreUsuario(), usuario.getPassword(), permisos);

        } else {
            return null;
        }

    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }

    @Transactional
    public void cambiarRol(String id, Rol rol) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setRol(rol);
        }
    }

}
