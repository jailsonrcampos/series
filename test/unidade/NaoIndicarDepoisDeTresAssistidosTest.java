package unidade;

import static org.junit.Assert.*;
import models.Episodio;
import models.NaoIndicarDepoisDeTresAssistidos;
import models.Serie;
import models.Temporada;

import org.junit.Test;

public class NaoIndicarDepoisDeTresAssistidosTest {

	@Test
	public void NaoDeveMostrarProximoQuandoNenhumEpisodioForAssistido() throws Exception {
		Serie serie = new Serie("Sons of Anarchy");
		serie.setProximoEpisodioExtrator(new NaoIndicarDepoisDeTresAssistidos());
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);
        
        assertFalse(temporada.isProximoEpisodioAssistir(episodio1));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio2));
	}
	
	@Test
	public void NaoDeveMostrarProximoQuandoTodosEpisodioForemAssistidos() throws Exception {
		Serie serie = new Serie("Sons of Anarchy");
		serie.setProximoEpisodioExtrator(new NaoIndicarDepoisDeTresAssistidos());
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);
        episodio1.setAssistido(true);
        episodio2.setAssistido(true);
        
        assertFalse(temporada.isProximoEpisodioAssistir(episodio1));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio2));
	}
	
	@Test
	public void NaodeveMostrarProximoQuandoMaisDeTresEpisodiosAssistidosDepois() throws Exception {
		Serie serie = new Serie("Sons of Anarchy");
		serie.setProximoEpisodioExtrator(new NaoIndicarDepoisDeTresAssistidos());
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        Episodio episodio3 = new Episodio("Episódio 3", temporada, 3);
        Episodio episodio4 = new Episodio("Episódio 4", temporada, 4);
        temporada.addEpisodio(episodio3);
        temporada.addEpisodio(episodio4);
        Episodio episodio5 = new Episodio("Episódio 5", temporada, 5);
        Episodio episodio6 = new Episodio("Episódio 6", temporada, 6);
        temporada.addEpisodio(episodio5);
        temporada.addEpisodio(episodio6);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);
        episodio1.setAssistido(true);
        episodio4.setAssistido(true);
        episodio5.setAssistido(true);
        episodio6.setAssistido(true);
        
        assertFalse(temporada.isProximoEpisodioAssistir(episodio1));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio2));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio3));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio4));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio5));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio6));
	}
	
	@Test
	public void deveMostrarProximo() throws Exception {
		Serie serie = new Serie("Sons of Anarchy");
		serie.setProximoEpisodioExtrator(new NaoIndicarDepoisDeTresAssistidos());
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        Episodio episodio3 = new Episodio("Episódio 3", temporada, 3);
        Episodio episodio4 = new Episodio("Episódio 4", temporada, 4);
        temporada.addEpisodio(episodio3);
        temporada.addEpisodio(episodio4);
        Episodio episodio5 = new Episodio("Episódio 5", temporada, 5);
        Episodio episodio6 = new Episodio("Episódio 6", temporada, 6);
        temporada.addEpisodio(episodio5);
        temporada.addEpisodio(episodio6);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);
        episodio1.setAssistido(true);
        episodio4.setAssistido(true);
        episodio5.setAssistido(true);
        
        assertFalse(temporada.isProximoEpisodioAssistir(episodio1));
        assertTrue(temporada.isProximoEpisodioAssistir(episodio2));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio3));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio4));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio5));
        assertFalse(temporada.isProximoEpisodioAssistir(episodio6));
	}

}
