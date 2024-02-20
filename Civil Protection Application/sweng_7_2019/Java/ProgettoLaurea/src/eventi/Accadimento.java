package eventi;

import java.sql.Timestamp;

public abstract class Accadimento extends Evento{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Accadimento(String sorgente) {
		super(sorgente);
		this.tempo = new Timestamp(System.currentTimeMillis());
	} 

	
}