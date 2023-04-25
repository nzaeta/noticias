package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.entidades.Periodista;
import com.egg.noticias.excepciones.MiException;
import com.egg.noticias.servicios.NoticiaServicio;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/noticia") //localhost:8080/autor
public class NoticiaControlador {
    
    @Autowired
    private NoticiaServicio noticiaServicio;
       
    @GetMapping("/nueva") //localhost:8080/noticia/nueva
    public String nueva(){
        return "noticia_form.html";
    }
    
    
    @PostMapping("/creando")
    public String creando(@RequestParam String titulo, @RequestParam String cuerpo, @RequestParam("archivo") MultipartFile archivo, ModelMap modelo, HttpSession session){
        
        String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());  
                int index = nombreArchivo.indexOf(".");
                String extension = "." + nombreArchivo.substring(index+1);
                nombreArchivo = Calendar.getInstance().getTimeInMillis() + extension;
        
        
        
        Periodista creador = (Periodista) session.getAttribute("usuariosession");
        
        try {
//            archivo.transferTo(new File("/fotos/" + nombreArchivo));
            archivo.transferTo(new File("C:/Users/nicoc/Documents/Dev/FULL STACK EGG/Java/Ejercicios/Noticias/src/main/resources/static/fotos/" + nombreArchivo));
            noticiaServicio.crearNoticia(titulo, cuerpo, nombreArchivo, creador);
            
            modelo.put("exito", "La noticia fue creada correctamente!");
        } catch (MiException | IOException | IllegalStateException ex) {
                      
            modelo.put("error", ex.getMessage());
            return "noticia_form.html";
        }
            return "noticia_form.html";       
//        return "index.html";        
    }
    
   
    
    @GetMapping("/{id}") //localhost:8080/noticia/id
    public String verNoticia(@PathVariable String id, ModelMap modelo){
        
        modelo.put("noticia", noticiaServicio.getOne(id));
        return "noticia_vista.html"; 
    }
    
 
    
    
    
    
    
    
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List <Noticia> noticias = noticiaServicio.listarNoticias();
        
        modelo.addAttribute("noticias", noticias);
        
        return "noticia_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("noticia", noticiaServicio.getOne(id));
        
        return "noticia_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String titulo, String cuerpo, ModelMap modelo){
        try {
            noticiaServicio.modificarNoticia(titulo, cuerpo, id);
            
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_modificar.html";
        }
        
    }
    

    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws MiException{

        try {
            noticiaServicio.eliminar(id);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:../lista";
        }

    }
    
    
}
