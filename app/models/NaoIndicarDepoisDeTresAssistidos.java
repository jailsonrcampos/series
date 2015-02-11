package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Retorna o próximo episódio da seguinte forma: Retorna o episodio mais antigo que ainda não foi assistido caso NÂO 
 * EXISTA tres próximos episódios já assistidos.
 * @author Jailson
 *
 */

@Entity
public class NaoIndicarDepoisDeTresAssistidos extends MaisAntigoNaoAssistido {
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private Temporada temporada;

	@Override
	public boolean isProximoEpisodioAssistir(List<Episodio> episodios, Episodio episodioAtual) {
		if(super.isProximoEpisodioAssistir(episodios, episodioAtual) == false) {
			return false;
		}
		int index = episodios.indexOf(episodioAtual);
		int cont = 0;
		for (int i = index + 1; i < episodios.size(); i++){
			if (episodios.get(i).isAssistido()){
				cont++;
	        }
	    }
		if(cont == 3) return false;
		return true;
	}

}
