package models;

import javax.persistence.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by orion on 03/12/14 and edited by jailson on 05/02/15.
 */

@Entity(name = "Temporada")
public class Temporada implements Comparable<Temporada> {

    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column
    private int numero;
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<Episodio> episodios;

    @ManyToOne(cascade=CascadeType.ALL)
    private Serie serie;

    public Temporada(){
        this.episodios = new LinkedList<Episodio>();
    }

    public Temporada(int numero, Serie serie) throws ArgumentoInvalidoException {
        this();
        if (numero <= 0){
            throw new ArgumentoInvalidoException("O Número da temporada não pode ser menor ou igual a zero!");
        }
        if (serie == null){
            throw new ArgumentoInvalidoException("Serie não pode ser nula!");
        }
        this.numero = numero;
        this.serie = serie;
    }

    public long getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) throws ArgumentoInvalidoException {
        if (numero <= 0){
            throw new ArgumentoInvalidoException("O Número da temporada não pode ser menor ou igual a zero!");
        }
        this.numero = numero;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void addEpisodio(Episodio episodio) throws ArgumentoInvalidoException {
        if (episodio == null){
            throw new ArgumentoInvalidoException("Episódio não pode ser nulo!");
        }
        episodios.add(episodio);
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) throws ArgumentoInvalidoException {
        if (serie == null){
            throw new ArgumentoInvalidoException("Serie não pode ser nula!");
        }
        this.serie = serie;
    }

    public int getQuantidadeDeEpisodiosAssistidos(){
        int cont = 0;
        for(Episodio episodio: episodios) {
            if (episodio.isAssistido()) {
                cont++;
            }
        }
        return cont;
    }

    public int getTotalDeEpisodios(){
        return episodios.size();
    }

    public boolean isProximoEpisodioAssistir(Episodio episodioAtual) throws ArgumentoInvalidoException {
    	if (episodioAtual == null){
            throw new ArgumentoInvalidoException("O Episodio não pode ser null!");
        }
        return serie.getProximoEpisodioExtrator().isProximoEpisodioAssistir(episodios, episodioAtual);
    }

	@Override
	public int compareTo(Temporada temporada) {
		return this.getNumero() - temporada.getNumero();
	}

}
