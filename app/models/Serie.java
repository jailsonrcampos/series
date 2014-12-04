package models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by orion on 03/12/14.
 */
@Entity
public class Serie {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String nome;

    @Column
    private boolean assistindo;

    @OneToMany
    @JoinColumn
    List<Temporada> temporadas;

    public Serie(){
        this.temporadas = new LinkedList<Temporada>();
    }

    public Serie(String nome) throws Exception {
        this.temporadas = new LinkedList<Temporada>();
        setNome(nome);
        setAssistindo(false);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome.equals("")){
            throw new Exception("Nome da série não deve ser vazio");
        }
        this.nome = nome;
    }

    public boolean isAssistindo() {
        return assistindo;
    }

    public void setAssistindo(boolean assistindo) {
        this.assistindo = assistindo;
    }

    public List<Temporada> getTemporadas() {
        return temporadas;
    }

    public void addTemporada(Temporada temporada){
        temporadas.add(temporada);
    }
}
