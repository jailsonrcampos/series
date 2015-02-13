package controllers;

import models.Episodio;
import models.MaisAntigoDepoisDoUltimoAssistido;
import models.MaisAntigoNaoAssistido;
import models.NaoIndicarDepoisDeTresAssistidos;
import models.Serie;
import models.dao.GenericDAO;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;
import java.util.List;

public class Application extends Controller {

    private static final GenericDAO DAO = new GenericDAO();

    @Transactional
    public static Result index() {
    	List<Serie> series = DAO.findAllByClass(Serie.class);
        return ok(index.render(series));
    }

    @Transactional
    public static Result assistirEpisodio(long id) {
    	Episodio episodio = DAO.findByEntityId(Episodio.class, id);
    	episodio.setAssistido(true);
    	
    	DAO.merge(episodio);
    	DAO.flush();
    	return redirect("/");
    }
    
    @Transactional
    public static Result naoAssistirEpisodio(long id) {
    	Episodio episodio = DAO.findByEntityId(Episodio.class, id);
    	episodio.setAssistido(false);
    	
    	DAO.merge(episodio);
    	DAO.flush();
    	return redirect("/");
    }

    @Transactional
    public static Result acompanharSerie(long id) {
    	Serie serie = DAO.findByEntityId(Serie.class, id);
    	serie.setAssistindo(true);
    	
    	DAO.merge(serie);
    	DAO.flush();
    	return redirect("/");
    }
    
    @Transactional
    public static Result naoAcompanharSerie(long id) {
    	Serie serie = DAO.findByEntityId(Serie.class, id);
    	serie.setAssistindo(false);
    	
    	DAO.merge(serie);
    	DAO.flush();
    	return redirect("/");
    }

    @Transactional
    public static Result proximoSerie(long id, int extrator) {
        Serie serie = DAO.findByEntityId(Serie.class, id);
        if(extrator == 1) {
        	serie.setProximoEpisodioExtrator(new MaisAntigoNaoAssistido());
        } else if (extrator == 2) {
        	serie.setProximoEpisodioExtrator(new MaisAntigoDepoisDoUltimoAssistido());
        } else {
        	serie.setProximoEpisodioExtrator(new NaoIndicarDepoisDeTresAssistidos());
        }
        
        DAO.merge(serie);
        DAO.flush();
        return redirect("/");
    }
}