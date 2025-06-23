package com.FilmHunt.cl.FilmHunt;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.FilmHunt.cl.FilmHunt.model.Lista;
import com.FilmHunt.cl.FilmHunt.model.Peliculas;
import com.FilmHunt.cl.FilmHunt.model.TipoUsuario;
import com.FilmHunt.cl.FilmHunt.model.Usuario;
import com.FilmHunt.cl.FilmHunt.model.directores;
import com.FilmHunt.cl.FilmHunt.model.estadoPelicula;
import com.FilmHunt.cl.FilmHunt.model.genero;
import com.FilmHunt.cl.FilmHunt.model.resena;
import com.FilmHunt.cl.FilmHunt.repository.DirectoresRepository;
import com.FilmHunt.cl.FilmHunt.repository.EstadoPeliculaRepository;
import com.FilmHunt.cl.FilmHunt.repository.GeneroRepository;
import com.FilmHunt.cl.FilmHunt.repository.ListaRepository;
import com.FilmHunt.cl.FilmHunt.repository.TipoUsuarioRepository;
import com.FilmHunt.cl.FilmHunt.repository.UsuarioRepository;
import com.FilmHunt.cl.FilmHunt.repository.PeliculaRepository;
import com.FilmHunt.cl.FilmHunt.repository.ResenaRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component

public class DataLoader implements CommandLineRunner{

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DirectoresRepository directoresRepository;

    @Autowired
    private EstadoPeliculaRepository estadoPeliculaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ListaRepository listaRepository;


    @Override
    public void run(String... args) throws Exception {

    
    //valores fijos
    // crear tipo "Admin" si no existe
        if (!tipoUsuarioRepository.existsById(1)) {
                TipoUsuario admin = new TipoUsuario();
                admin.setIdTipoUsuario(1);
                admin.setTipoUsuario("Admin");
                tipoUsuarioRepository.save(admin);
        }

        // crear tipo "Cliente" si no existe
        if (!tipoUsuarioRepository.existsById(2)) {
                TipoUsuario cliente = new TipoUsuario();
                cliente.setIdTipoUsuario(2);
                cliente.setTipoUsuario("Cliente");
                tipoUsuarioRepository.save(cliente);
        }
    
    //datafaker
    //pequeños
        Faker faker = new Faker();
        Random random = new Random();

        //se borra el id (i+1) por que se genera
        // automaticamente y si no genera error
        for (int i = 0; i < 5; i++) {
            directores directores = new directores();
            directores.setNombreDirector(faker.name().fullName());
            directoresRepository.save(directores);
        }


        for (int i = 0; i < 5; i++) {
            estadoPelicula estadoPelicula = new estadoPelicula();
            estadoPelicula.setNombreEstado(faker.options().option("Estreno", "En cartelera", "Finalizada", "Archivada", "En producción"));
            estadoPeliculaRepository.save(estadoPelicula);
        }

        for (int i = 0; i < 5; i++) {
            genero genero = new genero();
            genero.setNombreGenero(faker.book().genre());
            generoRepository.save(genero);
        }

        List<directores> directores = directoresRepository.findAll();
        List<estadoPelicula> estadoPeliculas = estadoPeliculaRepository.findAll();
        List<genero> genero = generoRepository.findAll();


    //grandes    
        
        for (int i = 0; i < 50; i++) {
            Peliculas pelicula = new Peliculas();
            pelicula.setTituloPelicula(faker.book().title());
            pelicula.setPuntuacion(String.valueOf(faker.number().numberBetween(1, 10)));
            pelicula.setDuracion(faker.number().numberBetween(60, 180) + " min");
            pelicula.setAno(faker.number().numberBetween(1950, 2025));
            //columnas de otras tablas
            pelicula.setDirector(directores.get(random.nextInt(directores.size())));
            pelicula.setEstado(estadoPeliculas.get(random.nextInt(estadoPeliculas.size())));
            pelicula.setGenero(genero.get(random.nextInt(genero.size())));
            peliculaRepository.save(pelicula);
        }

        List<TipoUsuario> tipoUsuarios = tipoUsuarioRepository.findAll();


        for (int i = 0; i < 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(faker.name().username());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setContrasena(faker.internet().password());
            //columnas de otras tablas
            usuario.setIdTipoUsuario(tipoUsuarios.get(random.nextInt(tipoUsuarios.size())));
            usuarioRepository.save(usuario);
        }

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Peliculas> peliculas = peliculaRepository.findAll();

        for (int i = 0; i < 20; i++) {
            Lista lista = new Lista();
            lista.setTituloLista(faker.lorem().sentence(3));
            //columnas de otras tablas
            lista.setIdPelicula(peliculas.get(random.nextInt(peliculas.size())));
            lista.setIdUsuario(usuarios.get(random.nextInt(usuarios.size())));

            listaRepository.save(lista);
        }

        for (int i = 0; i < 20; i++) {
            resena resena = new resena();
            resena.setTituloResena(faker.lorem().sentence(3));
            resena.setAno(faker.number().numberBetween(2000, 2025));
            resena.setPuntuacionResena(String.valueOf(faker.number().numberBetween(1, 10)));
            //columnas de otras tablas
            resena.setIdPelicula(peliculas.get(random.nextInt(peliculas.size())));
            resena.setIdUsuario(usuarios.get(random.nextInt(usuarios.size())));
            resenaRepository.save(resena);
        }
    }
}

