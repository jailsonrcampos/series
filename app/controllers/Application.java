package controllers;

import models.Serie;
import models.dao.GenericDAO;
import play.*;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {

    private static final GenericDAO dao = new GenericDAO();

    public static Result index() {
        List<Serie> series = dao.findAllByClass(Serie.class);
        return ok(index.render(series));
    }

}
