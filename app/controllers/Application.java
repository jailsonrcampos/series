package controllers;

import models.Episodio;
import models.Serie;
import models.dao.GenericDAO;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {

    private static final GenericDAO dao = new GenericDAO();
    private static Form<Serie> seriesForm = new Form<Serie>(Serie.class);


    @Transactional
    public static Result index() {
        return series();
    }

    @Transactional
    public static Result series() {
        List<Serie> series = dao.findAllByClass(Serie.class);
        return ok(index.render(series));
    }


    @Transactional
    public static Result assistirEpisodio(long id) {
        Form<Serie> filledForm = seriesForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            List<Serie> result = dao.findAllByClass(Serie.class);

            return badRequest(views.html.index.render(result));
        } else {
            Episodio episodio = dao.findByEntityId(Episodio.class, id);

            episodio.setAssistido(true);

            Logger.debug("Assistindo episodio: " + filledForm.data().toString() + " como " + episodio.getNome());

            dao.merge(episodio);

            dao.flush();

            return index();
        }

    }

    @Transactional
    public static Result assistirSerie(long id) {
        Form<Serie> filledForm = seriesForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            List<Serie> result = dao.findAllByClass(Serie.class);

            return badRequest(views.html.index.render(result));
        } else {
            Serie serie = dao.findByEntityId(Serie.class, id);

            serie.setAssistindo(true);

            Logger.debug("Assistindo serie: " + filledForm.data().toString() + " como " + serie.getNome());

            dao.merge(serie);

            dao.flush();

            return index();
        }
    }


}