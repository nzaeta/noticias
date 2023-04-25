
package com.egg.noticias.entidades;

import com.egg.noticias.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    protected String nombreUsuario;
    protected String password;
    protected Date fechaDeAlta;
    protected boolean activo;
    
    @Enumerated(EnumType.STRING)
    protected Rol rol;

}
