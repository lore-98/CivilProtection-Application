package condivise;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notifica implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String testo;
	private Timestamp tempo;
	private String tipo;
	private double parametro;

	public Notifica() {
		super();	
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public Timestamp getTempo() {
		return tempo;
	}
	public void setTempo(Timestamp tempo) {
		this.tempo = tempo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getParametro() {
		return parametro;
	}
	public void setParametro(double parametro) {
		this.parametro = parametro;
	}


}
