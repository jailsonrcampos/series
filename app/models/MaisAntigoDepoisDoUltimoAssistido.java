package models;

import java.util.List;
import javax.persistence.Entity;

/**
 * Retorna o próximo episódio da seguinte forma: Retorna o episodio mais antigo depois do último que foi assistido.
 * @author Jailson
 *
 */
@Entity
public class MaisAntigoDepoisDoUltimoAssistido extends ProximoEpisodioStrategy {
	
	public MaisAntigoDepoisDoUltimoAssistido(){};

	@Override
	public boolean isProximoEpisodioAssistir(List<Episodio> episodios, Episodio episodioAtual) {
		int indexUltimoAssistido = episodios.size();
        for (int i = episodios.size() - 1; i >= 0; i--) {
            if (episodios.get(i).isAssistido()) {
                indexUltimoAssistido = i;
                break;
            }
        }
        return (indexUltimoAssistido < episodios.size() - 1) && episodios.get(indexUltimoAssistido + 1).equals(episodioAtual);
	}

	@Override
	public String toString() {
		return "O próximo depois do último assistido.";
	}

}
