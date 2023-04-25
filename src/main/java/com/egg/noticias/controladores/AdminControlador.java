
package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.entidades.Periodista;
import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.excepciones.MiException;
import com.egg.noticias.servicios.NoticiaServicio;
import com.egg.noticias.servicios.PeriodistaServicio;
import com.egg.noticias.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
   
    @Autowired
    private PeriodistaServicio periodistaServicio;
    
    @Autowired
    private NoticiaServicio noticiaServicio;
    
   @GetMapping("/dashboard")
   public String panelAdministrativo(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticias", noticias);
       return "panel.html";
   }
   
    @GetMapping("/altaper")
    public String altaper() {
        return "alta_per.html";
    }

    @PostMapping("/altaper")
    public String altaperiodista(@RequestParam String nombre, @RequestParam String password,
            String password2, Integer sueldo, ModelMap modelo, MultipartFile archivo) {

        try {
            periodistaServicio.altaPeriodista(archivo, nombre, password, password2, sueldo);

            modelo.put("exito", "Periodista registrado correctamente!");

            return "redirect:/admin/dashboard";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);


            return "alta_per.html";
        }

    }
    
    @GetMapping("/listaper")
    public String listar(ModelMap modelo){
        
        List <Periodista> periodistas = periodistaServicio.listarPeriodistas();
        
        modelo.addAttribute("periodistas", periodistas);
        
        return "per_list.html";
    }
   
//   @GetMapping("/usuarios")
//    public String listar(ModelMap modelo) {
//        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
//        modelo.addAttribute("usuarios", usuarios);
//
//        return "usuario_list";
//    }
//    
    
    @GetMapping("/estado/{id}")
    public String cambiarEstado(@PathVariable String id){
        periodistaServicio.cambiarEstado(id);
        
       return "redirect:/admin/listaper";
    }
   
}