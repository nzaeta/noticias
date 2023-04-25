package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Periodista;
import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.enumeraciones.Rol;
import com.egg.noticias.excepciones.MiException;
import com.egg.noticias.repositorios.PeriodistaRepositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author NICO
 */
@Service
public class PeriodistaServicio {

    @Autowired
    private PeriodistaRepositorio periodistaRepositorio;

    @Transactional
    public void altaPeriodista(MultipartFile archivo, String nombre, String password, String password2, Integer sueldo) throws MiException {

        validar(nombre, password, password2);

        Periodista periodista = new Periodista();

        Date fechaActual = new Date();

        periodista.setNombreUsuario(nombre);
        periodista.setFechaDeAlta(fechaActual);
        periodista.setActivo(true);
        periodista.setPassword(new BCryptPasswordEncoder().encode(password));
        periodista.setSueldoMensual(sueldo);

        periodista.setRol(Rol.PER);

        periodistaRepositorio.save(periodista);
    }

    @Transactional(readOnly = true)
    public List<Periodista> listarPeriodistas() {

        List<Periodista> periodistas = new ArrayList();

        periodistas = periodistaRepositorio.findAll();
        Collections.reverse(periodistas);

        return periodistas;
    }

    private void validar(String nombre, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }

        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }

   @Transactional
    public void cambiarEstado(String id){
        Optional<Periodista> respuesta = periodistaRepositorio.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Periodista periodista = respuesta.get();
    		
    		if(periodista.isActivo()) {
    			
    		periodista.setActivo(false);
    		
    		}else if(!periodista.isActivo()) {
    			periodista.setActivo(true);
    		}
    	}
    }

}
