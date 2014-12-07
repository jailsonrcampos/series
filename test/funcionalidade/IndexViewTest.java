package funcionalidade;

import base.AbstractTest;
import models.Episodio;
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
        assertThat(contentAsString(html)).doesNotContain("Começar a assistir");
    }

    @Test
    public void deveAparecerSeriesNaoAssistidas() throws Exception {
        Serie serie = new Serie("Sons of Anarchy");

        dao.persist(serie);
        series = dao.findAllByClass(Serie.class);

        Html html = index.render(series);

        assertThat(contentAsString(html)).contains("Sons of Anarchy");
        assertThat(contentAsString(html)).contains("Começar a assistir");
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

        assertThat(contentAsString(html)).doesNotContain("Começar a assistir");
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

        assertThat(contentAsString(html)).contains("1 Temporada - 0/2");
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

        assertThat(contentAsString(html)).contains("1 Temporada - 1/2");
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

        assertThat(contentAsString(html)).contains("1 Temporada - 2/2");
    }

    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoNaoTiverAssistidoNenhum() throws Exception {
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

        assertThat(contentAsString(html)).contains("1 Temporada - 1/2");
        assertThat(contentAsString(html)).contains("Episódio 2 (PRÓXIMO)");
    }

    @Test
    public void naoDeveAparecerOProximoEpisodioQuandoTiverAssistidoTodos() throws Exception {
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

        assertThat(contentAsString(html)).doesNotContain("PRÓXIMO");
    }
}
