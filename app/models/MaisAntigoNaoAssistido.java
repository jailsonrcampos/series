package models;

import java.util.List;
import javax.persistence.Entity;

/**
 * Retorna o próximo episódio da seguinte forma: Retorna o episodio mais antigo que ainda não foi assistido.
 * @author Jailson
 *
 */

@Entity
public class MaisAntigoNaoAssistido extends ProximoEpisodioStrategy {
	
	public MaisAntigoNaoAssistido(){}

	@Override
	public boolean isProximoEpisodioAssistir(List<Episodio> episodios, Episodio episodioAtual) {
		boolean hasAssistido = false;
		for(Episodio episodio: episodios) {
            if (episodio.isAssistido()) {
            	hasAssistido = true;
            	break;
            }
        }
		if(!hasAssistido) {
			return false;
		}
		for(Episodio episodio: episodios) {
            if (!episodio.isAssistido()) {
            	return episodio.equals(episodioAtual);
            }
        }
		return false;
	}

	@Override
	public String toString() {
		return "O mais antigo não assistido";
	}
}
