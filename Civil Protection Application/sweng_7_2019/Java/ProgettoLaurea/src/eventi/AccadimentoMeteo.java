package eventi;


public class AccadimentoMeteo extends Accadimento {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double precipitazione;

	public Double getPrecipitazione() {
		return precipitazione;
	}

	public void setPrecipitazione(Double precipitazione) {
		this.precipitazione = precipitazione;
	}

	public AccadimentoMeteo(String sorgente) {
		super(sorgente);
	}
	
	
}
