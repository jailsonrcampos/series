package unidade;

import models.Serie;
import models.Temporada;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

public class SerieTest {

    Serie serie;

    @Test
    public void naoDeveCriarSerieSemNome(){
        try{
            serie = new Serie("");
            fail();
        } catch (Exception e){
            assertThat(serie).isEqualTo(null);
        }
    }
    
    @Test
    public void naoDeveCriarSerieComNomeNull(){
        try{
            serie = new Serie(null);
            fail();
        } catch (Exception e){
            assertThat(serie).isEqualTo(null);
        }
    }

    @Test
    public void deveComecarAAssistirUmaSerie() throws Exception {
        serie = new Serie("Sons of Anarchy");

        assertFalse(serie.isAssistindo());

        serie.setAssistindo(true);
        assertTrue(serie.isAssistindo());
    }

    @Test
    public void deveIniciarSerieSemTemporadas() throws Exception {
        serie = new Serie("Sons of Anarchy");

        assertThat(serie.getNome()).isEqualTo("Sons of Anarchy");
        assertThat(serie.getTemporadas()).isEmpty();
    }

    @Test
    public void deveAdicionarTemporadaNaSerie() throws Exception {
        serie = new Serie("Sons of Anarchy");
        Temporada temporada1 = new Temporada(1, serie);
        Temporada temporada2 = new Temporada(2, serie);

        serie.addTemporada(temporada1);

        assertThat(serie.getTemporadas()).isNotEmpty();
        assertThat(serie.getTemporadas().get(0).getNumero()).isEqualTo(1);

        serie.addTemporada(temporada2);
        assertThat(serie.getTemporadas().get(1).getNumero()).isEqualTo(2);

    }

}
