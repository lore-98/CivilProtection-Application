package eventi;


public class PrevisioneMeteo extends Previsione{
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

	public PrevisioneMeteo(String sorgente) {
		super(sorgente);
	}

	
	
}
