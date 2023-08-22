# eggNews
Desarrollo de un sistema web con Java utilizando una base de datos MySQL, JPA repository para persistir objetos y Spring Boot como framework de desarrollo web. 

## Sobre el proyecto!  🚀
El portal de noticias Egg nació como pequeño proyecto de difusión de noticias, centrado en una facil visualizar de forma simple las distintas entradas generadas por usuarios con el Rol de Periodistas, definidos específicamente por los Administradores de la web y con acceso para todos los usuarios previamente registrados a dicho sitio.

## Tecnologías 🛠️
- Java 8
- Spring Boot y Spring Security
- MySQL y JPA repository
- HTML, CSS, JavaScript (Bootstrap)
-Thymeleaf

## Estructura del proyecto
      / ├── com.egg.news
    ├── com.egg.news.Enumeraciones
    |   	└── Rol.java
    ├── com.egg.news.controladores
    |   	└── AdminControlador.java
    |   	└── ErrorControlador.java
    |   	└── ImagenControlador.java
    |   	└── NoticiaControlador.java
    ├── com.egg.news.entidades
    |      	└── Imagen.java
    |   	└── Noticia.java
    |   	└── Periodista.java
    |   	└── Usuario.java
    ├── com.egg.news.excepciones
    |      	└── MiException.java
    ├── com.egg.news.repositorios
    |   	└── ImagenRepositorio.java
    |   	└── NoticiaRepositorio.java
    |   	└── PeriodistaRepositorio.java
    |   	└── UsuarioRepositorio.java
    └── com.egg.news.servicios
          	└── AdminServicio.java
       	└── ImagenServicio.java
        └── NoticiaServicio.java
       	└── UsuarioServicio.java
        ├── templates
        |	├── error.html
        |	├── index.html
        |	├── inicio.html
        |	├── lista_periodistas.html
        |	├── lista_usuarios.html
        |	├── login.html
        |	├── noticia.html
        |	├── noticia_form.html
        |	├── noticia_modificar.html
        |	├── panel.html
        |	├── periodista_form.html
        |	├── registro_form.html
        |	└── usuario_modificar.html
        └── templates.fragments
            ├── error-exito.html
            ├── footer.html
            ├── footer.html
            └── navbar.html


