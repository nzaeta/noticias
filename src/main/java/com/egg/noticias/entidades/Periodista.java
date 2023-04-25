
package com.egg.noticias.entidades;

import java.util.ArrayList;
import javax.persistence.Entity;
import lombok.Data;


@Data
@Entity
public class Periodista extends Usuario{
    
    private ArrayList <Noticia> misNoticias;
    private Integer sueldoMensual;
    
    
}
