package com.egg.news.servicios;

import com.egg.news.Enumeraciones.Rol;
import com.egg.news.entidades.Periodista;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.PeriodistaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServicio {

    @Autowired
    private PeriodistaRepositorio periodistaRepositorio;

    public void registrarPeriosita(String nombreUsuario, String password, String password2, Integer sueldo) throws MiException {
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

        periodistaRepositorio.save(periodista);

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

}
