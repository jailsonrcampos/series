package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *  Cria uma estratégia para obter o próximo episódio de uma temporada
 */@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class ProximoEpisodioStrategy {
	
	@Id
	@Column
	@GeneratedValue
	private Long id;
	
	public ProximoEpisodioStrategy() {}
	
	public abstract boolean isProximoEpisodioAssistir( List<Episodio> episodios, Episodio episodioAtual);
	
	public abstract String toString();

	public Long getId() {
		return id;
	}
	
}
