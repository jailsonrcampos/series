package funcionalidade;

import base.AbstractTest;
import models.Episodio;
import models.MaisAntigoDepoisDoUltimoAssistido;
import models.MaisAntigoNaoAssistido;
import models.NaoIndicarDepoisDeTresAssistidos;
import models.Serie;
import models.Temporada;
import models.dao.GenericDAO;

import org.junit.Test;

import play.twirl.api.Html;
import views.html.index;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;


public class IndexViewTest extends AbstractTest {
    GenericDAO dao = new GenericDAO();
    List<Serie> series;

    @Test
    public void naoDevePegarDadosDoGlobal(){
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("South Park");
        assertThat(contentAsString(html)).doesNotContain("Acompanhar");
    }

    @Test
    public void deveAparecerSeriesNaoAssistidas() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).contains("Sons of Anarchy");
        assertThat(contentAsString(html)).contains("Acompanhar");
    }

    @Test
    public void naoDeveAparecerSeriesAcompanhadas() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("Já assisti!");
    }

    @Test
    public void deveAparecerTemporadaEmSeriesNaoAssistidas() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada1 = new Temporada(1, serie);
        Temporada temporada2 = new Temporada(2, serie);
        serie.addTemporada(temporada1);
        serie.addTemporada(temporada2);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("Já assisti!");
        assertThat(contentAsString(html)).contains("Temporada - 1");
        assertThat(contentAsString(html)).contains("Temporada - 2");
    }

    @Test
    public void naoDeveAparecerEpisodioEmSeriesNaoAssistidas() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episódio 1", temporada, 1);
        temporada.addEpisodio(episodio);
        serie.addTemporada(temporada);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("Episódio 1");
    }

    @Test
    public void deveComecarAAssistirSerie() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episódio 1", temporada, 1);
        temporada.addEpisodio(episodio);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("Acompanhar");
        assertThat(contentAsString(html)).contains("Episódio 1");
    }

    @Test
    public void deveAparecerEpisodiosEmSeriesAcompanhadas() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episódio 1", temporada, 1);
        temporada.addEpisodio(episodio);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).contains("Episódio 1");
        assertThat(contentAsString(html)).contains("Já assisti!");
    }

    @Test
    public void deveAssistirEpisodio() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio = new Episodio("Episódio 1", temporada, 1);
        episodio.setAssistido(true);
        temporada.addEpisodio(episodio);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).contains("<del>Episódio 1</del>");
        assertThat(contentAsString(html)).doesNotContain("Já assisti!");
    }

    @Test
    public void deveAparecerQuandoNaoViuNadaDaTemporada() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).contains("1 ª Temporada - 0/2");
    }

    @Test
    public void deveAparecerQuandoTemporadaEhIncompleta() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        episodio1.setAssistido(true);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).contains("1 ª Temporada - 1/2");
    }

    @Test
    public void deveAparecerQuandoTemporadaEhCompleta() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        episodio1.setAssistido(true);
        episodio2.setAssistido(true);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).contains("1 ª Temporada - 2/2");
    }

    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoNaoTiverAssistidoNenhumCaso1() throws Exception {
    	
    	// caso1: mais antigo depois do ultimo assistido
    	
        Serie serie = new Serie("Sons of Anarchy");
        serie.setProximoEpisodioExtrator(new MaisAntigoDepoisDoUltimoAssistido());
        
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("PRÓXIMO");
    }
    
    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoNaoTiverAssistidoNenhumCaso2() throws Exception {
    	
    	// caso 2: mais antigo
    	
        Serie serie = new Serie("Sons of Anarchy");
        serie.setProximoEpisodioExtrator(new MaisAntigoNaoAssistido());
        
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("PRÓXIMO");
    }
    
    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoNaoTiverAssistidoNenhumCaso3() throws Exception {
    	
    	// caso 3: mais antigo, nao mostrar depois de tres assistidos
    	
        Serie serie = new Serie("Sons of Anarchy");
        serie.setProximoEpisodioExtrator(new NaoIndicarDepoisDeTresAssistidos());
        
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("PRÓXIMO");
    }
    
    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoTiverTodosAssistidosCaso1() throws Exception {
    	
    	// caso1: mais antigo depois do ultimo assistido
    	
        Serie serie = new Serie("Sons of Anarchy");
        serie.setProximoEpisodioExtrator(new MaisAntigoDepoisDoUltimoAssistido());
        
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);
        episodio1.setAssistido(true);
        episodio2.setAssistido(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("PRÓXIMO");
    }
    
    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoTiverTodosAssistidosCaso2() throws Exception {
    	
    	// caso 2: mais antigo
    	
        Serie serie = new Serie("Sons of Anarchy");
        serie.setProximoEpisodioExtrator(new MaisAntigoNaoAssistido());
        
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);
        episodio1.setAssistido(true);
        episodio2.setAssistido(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("PRÓXIMO");
    }
    
    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoTiverTodosAssistidosCaso3() throws Exception {
    	
    	// caso 3: mais antigo, nao mostrar depois de tres assistidos
    	
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

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("PRÓXIMO");
    }
    
    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoTiverTresAssistidosCaso3() throws Exception {
    	
    	// caso 3: mais antigo, nao mostrar depois de tres assistidos
    	
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

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).doesNotContain("PRÓXIMO");
    }

    @Test
    public void deveAparecerOProximoEpisodio() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");
        Temporada temporada = new Temporada(1, serie);
        Episodio episodio1 = new Episodio("Episódio 1", temporada, 1);
        Episodio episodio2 = new Episodio("Episódio 2", temporada, 2);
        episodio1.setAssistido(true);
        temporada.addEpisodio(episodio1);
        temporada.addEpisodio(episodio2);
        serie.addTemporada(temporada);
        serie.setAssistindo(true);

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).contains("1 ª Temporada - 1/2");
        assertThat(contentAsString(html)).contains("Episódio 2 (PRÓXIMO)");
    }

    
}
