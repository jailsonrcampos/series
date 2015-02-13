package models;

import javax.persistence.*;

/**
 * Created by orion on 03/12/14 and edited by jailson on 05/02/15.
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

    @ManyToOne(cascade=CascadeType.ALL)
    private Temporada temporada;

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
        if (nome.equals("") || nome == null){
            throw new Exception("Nome do episódio não deve ser vazio!");
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

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) throws Exception {
        if(temporada == null){
            throw new Exception("Temporada não deve ser nula!");
        }
        this.temporada = temporada;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) throws Exception {
        if (numero < 0){
            throw new Exception("O número do episódio não deve ser menor que zero!");
        }
        this.numero = numero;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + numero;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Episodio)) {
			return false;
		}
		Episodio temp = (Episodio) obj;
		return this.nome.equals(temp.getNome()) && this.numero == temp.getNumero();
	}
	
}
