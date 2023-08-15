/*
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias para operar
con la vista del usuario diseñada para la gestión de noticias (guardar/modificar noticia, listar
noticias, dar de baja, etc).
 */
package com.egg.news.controladores;

import com.egg.news.excepciones.MiException;
import com.egg.news.servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noticia")
public class NoticiaContolador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/subir")
    public String crearNota() {
        return ("noticia_form.html");
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelo) {
        try {
            noticiaServicio.crearNoticia(titulo, cuerpo);
            modelo.put("exito", "El autor fue cargado correctamente");
            return "redirect:/inicio";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_form.html";
        }

    }
    
    @GetMapping("/{id}")
    public String noticiaCompleta(@PathVariable("id")Integer id, ModelMap modelo){
        modelo.put("noticia", noticiaServicio.getOne(id));
        return "noticia.html";
    }
    
    
    @GetMapping("/modificar")
    public String modificarNota(Integer id, ModelMap modelo){
        
        return "noticia_modificar.html";
    }
}
