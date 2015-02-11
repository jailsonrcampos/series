package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Retorna o próximo episódio da seguinte forma: Retorna o episodio mais antigo que ainda não foi assistido.
 * @author Jailson
 *
 */

@Entity
public class MaisAntigoNaoAssistido extends ProximoEpisodioStrategy {
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private Temporada temporada;

	@Override
	public boolean isProximoEpisodioAssistir(List<Episodio> episodios, Episodio episodioAtual) {
		for(Episodio episodio: episodios){
            if (!episodio.isAssistido()) {
            	return episodio.equals(episodioAtual);
            }
        }
		return false;
	}

}
