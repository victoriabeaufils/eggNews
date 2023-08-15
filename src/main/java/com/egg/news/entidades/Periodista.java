
package com.egg.news.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "periodista")
//JoinTable()
public class Periodista extends Usuario {
    
    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL)
    private List<Noticia>MisNoticias= new ArrayList<>();
    private Integer sueldoMensual;

    public Periodista() {
    }

    public List<Noticia> getMisNoticias() {
        return MisNoticias;
    }

    public void setMisNoticias(List<Noticia> MisNoticias) {
        this.MisNoticias = MisNoticias;
    }


    public Integer getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(Integer sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }
    
    
}
