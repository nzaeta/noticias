package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.entidades.Periodista;
import com.egg.noticias.excepciones.MiException;
import com.egg.noticias.repositorios.NoticiaRepositorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NoticiaServicio {
    
    @Autowired
    NoticiaRepositorio noticiaRepositorio;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo, String foto, Periodista creador) throws MiException{
              
        validar(titulo, cuerpo);
        
        Noticia noticia = new Noticia();

        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setFoto(foto);
        noticia.setCreador(creador);


        noticiaRepositorio.save(noticia);

    }
    
    @Transactional(readOnly = true)
    public List<Noticia> listarNoticias() {

        List<Noticia> noticias = new ArrayList();

        noticias = noticiaRepositorio.findAll();
        Collections.reverse(noticias);
        
        return noticias;
    }
    
    @Transactional
    public void modificarNoticia(String titulo, String cuerpo, String id) throws MiException{
        
        validar(titulo, cuerpo);
        
        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);

            noticiaRepositorio.save(noticia);

        }
    }
    
    @Transactional
    public void eliminar(String id) throws MiException{
        
        Noticia noticia = noticiaRepositorio.getById(id);
        
        noticiaRepositorio.delete(noticia);

    }

    
    public Noticia getOne(String id){
        
        return noticiaRepositorio.getOne(id);

    }    
     private void validar(String titulo, String cuerpo) throws MiException {
        
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el título no puede ser nulo o estar vacío");
        }
        
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("el cuerpo no puede ser nulo o estar vacío");
        }
    }
}

