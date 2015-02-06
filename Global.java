import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import play.*;
import play.db.jpa.JPA;
import models.Episodio;
import models.Serie;
import models.Temporada;
import models.dao.GenericDAO;

public class Global extends GlobalSettings {
	
	private static final GenericDAO dao = new GenericDAO();

<<<<<<< HEAD
	@Override
	public void onStart(Application app) {
		Logger.info("inicializada...");
=======
    private static GenericDAO dao = new GenericDAO();

    @Override
    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                //iniciaBanco();
                
                Serie serie = new Serie("South Park");
                Temporada temporada =  new Temporada(1, serie);
                Episodio episodio = new Episodio("Cartman Gets an Anal Probe", temporada, 1);
                temporada.addEpisodio(episodio);
                serie.addTemporada(temporada);
                dao.persist(serie);
                dao.persist(temporada);
                dao.persist(episodio);
                dao.flush();
            }});
    }

    public void iniciaBanco() throws Exception{

        String csvFile = Play.application().getFile("seriesFinalFile.csv").getAbsolutePath();
        //String csvFile = "seriesFinalFile.csv";
        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            String[] listFromCSV = line.split(",");
            Serie serie = new Serie("South Park");
            Temporada temporada =  new Temporada(1, serie);
            Episodio episodio = new Episodio("Cartman Gets an Anal Probe", temporada, 1);
            temporada.addEpisodio(episodio);
            serie.addTemporada(temporada);
            line = br.readLine();

            while ((line = br.readLine()) != null) {
                listFromCSV = line.split(",");
                if (listFromCSV.length>=4) {
                    episodio = new Episodio(listFromCSV[3], serie.temporadaMaisNova(), parseInt(listFromCSV[2]));
                } else {
                    episodio = new Episodio("Sem Título", serie.temporadaMaisNova(), parseInt(listFromCSV[2]));
                }

                if (serie.getNome().replace("'", "").equals(listFromCSV[0].replace("'", ""))){
>>>>>>> origin/master

		JPA.withTransaction(new play.libs.F.Callback0() {
			
			public void invoke() throws Throwable {
				
				List<Serie> seriesDB = dao.findAllByClass(Serie.class);
				if(seriesDB.size() == 0) {
					
					CSVReader reader = new CSVReader(new FileReader("seriesFinalFile.csv"));
			        String [] nextLine;
			        
			        List<Serie> series = new ArrayList<Serie>();
			        int temporadaAtual = 1;
			        
			        while ((nextLine = reader.readNext()) != null) {
			        	String nomeEpisodio = nextLine[3].equals("") ? "Sem Título" : nextLine[3];			        	
			        	Serie serie  = new Serie(nextLine[0]);
			        	int i = series.indexOf(serie);
			        	if(i >= 0) {
			        		if(Integer.parseInt(nextLine[1]) == temporadaAtual) {
			        			Temporada temporada = series.get(i).getTemporadas().get(temporadaAtual-1);
			        			Episodio episodio = new Episodio(nomeEpisodio, temporada, Integer.parseInt(nextLine[2]));
			        			temporada.addEpisodio(episodio);
				                dao.persist(series.get(i));
				                dao.persist(temporada);
				                dao.persist(episodio);
			        		} else {
			        			Temporada temporada =  new Temporada(Integer.parseInt(nextLine[1]), series.get(i));
			        			Episodio episodio = new Episodio(nomeEpisodio, temporada, Integer.parseInt(nextLine[2]));
				                temporada.addEpisodio(episodio);
				                series.get(i).addTemporada(temporada);
				                dao.persist(series.get(i));
				                dao.persist(temporada);
				                dao.persist(episodio);
				                temporadaAtual = Integer.parseInt(nextLine[1]); 
			        		}
			        	} else {
			        		Temporada temporada =  new Temporada(Integer.parseInt(nextLine[1]), serie);
				            Episodio episodio = new Episodio(nomeEpisodio, temporada, Integer.parseInt(nextLine[2]));
				            temporada.addEpisodio(episodio);
				            serie.addTemporada(temporada);
				            dao.persist(serie);
				            dao.persist(temporada);
				            dao.persist(episodio);
			        		series.add(serie);
			        		temporadaAtual = 1;
			        	}
			        }
			         
			        reader.close();
				}
				
			}
		});
	}
	
	public void onStop(Application app) {
		Logger.info("desligada...");
	}

}
