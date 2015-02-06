package models;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by orion on 03/12/14 and edited by jailson on 05/02/15.
 */
@Entity (name="Serie")
public class Serie {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String nome;

    @Column
    private boolean assistindo;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    List<Temporada> temporadas;

    public Serie() {
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
        if (nome.equals("") || nome == null){
            throw new Exception("Nome da série não deve ser vazia ou null");
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

    public void addTemporada(Temporada temporada) throws Exception {
        if (temporada == null){
            throw new Exception("Temporada não deve ser nula!");
        }
        temporadas.add(temporada);
    }

    public Temporada temporadaMaisNova(){
        return temporadas.get(temporadas.size() - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this == null || getClass() != this.getClass()) return false;
        if (obj == null || getClass() != obj.getClass()) return false;

        Serie serie = (Serie) obj;

        if (!nome.equals(serie.nome)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}
