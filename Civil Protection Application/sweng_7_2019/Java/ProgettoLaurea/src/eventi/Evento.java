package eventi;

import java.io.Serializable;
import java.sql.Timestamp;
import condivise.ListaCap;
import condivise.TipoEvento;

public abstract class Evento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idEvento;
	private Integer cap;
	private String sorgente;
	private String tipo;
	protected Timestamp tempo;

	public Evento(String sorgente) {
		super();
		this.cap = ListaCap.getCap();
		this.sorgente = sorgente;
		this.tipo = TipoEvento.getTipoEvento();

	}


	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getCap() {
		return cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}

	public String getSorgente() {
		return sorgente;
	}

	public void setSorgente(String sorgente) {
		this.sorgente = sorgente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Timestamp getTempo() {
		return tempo;
	}

	public void setTempo(Timestamp tempo) {
		this.tempo = tempo;
	}



}
