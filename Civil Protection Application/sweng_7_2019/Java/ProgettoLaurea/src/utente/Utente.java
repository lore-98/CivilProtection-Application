package utente;


public class Utente {
	private String nome;
	private Integer cap;
	public Utente(String nome, Integer cap) {
		this.nome= nome;
		this.cap=cap;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getCap() {
		return cap;
	}
	public void setCap(Integer cap) {
		this.cap = cap;
	}

}
