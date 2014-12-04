package models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by orion on 03/12/14.
 */

@Entity(name = "Temporada")
public class Temporada {

    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column
    private int numero;

    @OneToMany
    @JoinColumn
    List<Episodio> episodios;

    @ManyToOne
    Serie serie;

    public Temporada(){
        this.episodios = new LinkedList<Episodio>();
    }

    public Temporada(int numero, Serie serie) throws Exception {
        this.episodios = new LinkedList<Episodio>();
        setNumero(numero);
        setSerie(serie);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) throws Exception {
        if (numero <= 0){
            throw new Exception("Número da temporada deve ser maior que 0!");
        }
        this.numero = numero;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void addEpisodios(Episodio episodio) {
        episodios.add(episodio);
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public int getQuantidadeAssistida(){
        int quantidade = 0;
        for (Episodio episodio: episodios){
            if (episodio.isAssistido()){
                quantidade++;
            }
        }
        return quantidade;
    }
}
