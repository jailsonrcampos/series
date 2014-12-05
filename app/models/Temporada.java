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

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    List<Episodio> episodios;

    @ManyToOne(cascade=CascadeType.ALL)
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
        this.numero = numero;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void addEpisodio(Episodio episodio) {
        episodios.add(episodio);
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public int getQuantidadeDeEpisodiosAssistidos(){
        int cont = 0;
        for(Episodio episodio: episodios){
            if (episodio.isAssistido()){
                cont++;
            }
        }
        return cont;
    }

    public int getTotalDeEpisodios(){
        return episodios.size();
    }

    public boolean ehOProximoASerAssistido(long id){
        int indexUltimo = episodios.size();
        for (int i = episodios.size() - 1; i >= 0; i--){
            if (episodios.get(i).isAssistido()){
                indexUltimo = i;
                break;
            }
        }

        if (indexUltimo >= episodios.size() - 1){
            return false;
        } else {
            return episodios.get(indexUltimo + 1).getId() == id;
        }
    }
}
