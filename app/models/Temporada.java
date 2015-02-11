package models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by orion on 03/12/14 and edited by jailson on 05/02/15.
 */

@Entity(name = "Temporada")
public class Temporada {

    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column
    private int numero;
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private ProximoEpisodioStrategy proximoEpisodioExtrator;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<Episodio> episodios;

    @ManyToOne(cascade=CascadeType.ALL)
    private Serie serie;

    public Temporada(){
        this.episodios = new LinkedList<Episodio>();
        this.proximoEpisodioExtrator = new MaisAntigoDepoisDoUltimoAssistido();
    }

    public Temporada(int numero, Serie serie) throws Exception {
        this();
        setNumero(numero);
        setSerie(serie);
    }

    public long getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) throws Exception {
        if (numero <= 0){
            throw new Exception("O Número da temporada não pode ser menor ou igual a zero!");
        }
        this.numero = numero;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void addEpisodio(Episodio episodio) throws Exception {
        if (episodio == null){
            throw new Exception("Episódio não pode ser nulo!");
        }
        episodios.add(episodio);
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) throws Exception {
        if (serie == null){
            throw new Exception("Serie não pode ser nula!");
        }
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

    public boolean isProximoEpisodioAssistir(Episodio episodioAtual) throws Exception {
    	if (episodioAtual == null){
            throw new Exception("O Episodio não pode ser null!");
        }
        return proximoEpisodioExtrator.isProximoEpisodioAssistir(episodios, episodioAtual);
    }

	public ProximoEpisodioStrategy getProximoEpisodioExtrator() {
		return proximoEpisodioExtrator;
	}

	public void setProximoEpisodioExtrator(ProximoEpisodioStrategy proximoEpisodioExtrator) {
		this.proximoEpisodioExtrator = proximoEpisodioExtrator;
	}
    
}
