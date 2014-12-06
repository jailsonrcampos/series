package unidade;

import models.Episodio;
import models.Serie;
import models.Temporada;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

/**
 * Created by orion on 06/12/14.
 */
public class TemporadaTest {
    Temporada temporada;

    @Test
    public void naoDeveCriarTemporadaComNumeroNegativo(){
        try{
            Serie serie = new Serie("Sons of Anarchy");
            temporada = new Temporada(-1, serie);
        } catch (Exception e){
            assertThat(temporada).isEqualTo(null);
        }
    }

    @Test
    public void naoDeveCriarTemporadaSerieNula(){
        try{
            temporada = new Temporada(1, null);
        } catch (Exception e){
            assertThat(temporada).isEqualTo(null);
        }
    }

    @Test
    public void deveCriarTemporadaSemEpisodios() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        temporada = new Temporada(1, serie);

        assertThat(temporada.getEpisodios()).isEmpty();
    }

    @Test
    public void deveAdicionarEpisodio() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episódio 1", temporada, 1);
        temporada.addEpisodio(episodio);

        assertThat(temporada.getEpisodios()).isNotEmpty();
    }

    @Test
    public void deveContarEpisodiosAssistidos() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episódio 1", temporada, 1);

        assertThat(temporada.getQuantidadeDeEpisodiosAssistidos()).isEqualTo(0);
        assertThat(temporada.getTotalDeEpisodios()).isEqualTo(0);

        temporada.addEpisodio(episodio);

        assertThat(temporada.getTotalDeEpisodios()).isEqualTo(1);
        assertThat(temporada.getQuantidadeDeEpisodiosAssistidos()).isEqualTo(0);

        episodio.setAssistido(true);

        assertThat(temporada.getTotalDeEpisodios()).isEqualTo(1);
        assertThat(temporada.getQuantidadeDeEpisodiosAssistidos()).isEqualTo(1);
    }
    
}
