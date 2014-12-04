package models;

import javax.persistence.*;

/**
 * Created by orion on 03/12/14.
 */

@Entity(name = "Episodio")
public class Episodio {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String nome;

    @Column
    private int numero;

    @Column
    private boolean assistido;

    @ManyToOne
    Temporada temporada;

    public Episodio(String nome, Temporada temporada, int numero) throws Exception {
        setNome(nome);
        setTemporada(temporada);
        setNumero(numero);
        this.assistido = false;
    }

    public Episodio(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome.equals("")){
            throw new Exception("Nome não deve ser vazio!");
        }
        this.nome = nome;
    }

    public boolean isAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) throws Exception {
        if (numero <= 0){
            throw new Exception("Número da temporada não deve ser menor que 0!");
        }
        this.numero = numero;
    }
}
