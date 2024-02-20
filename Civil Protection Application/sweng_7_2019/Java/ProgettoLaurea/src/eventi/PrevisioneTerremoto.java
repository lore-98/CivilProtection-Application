package eventi;

import condivise.RandomGaussiano;

public class PrevisioneTerremoto extends Previsione{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double magnitudo;

	public PrevisioneTerremoto(String sorgente) {
		super(sorgente);
		this.magnitudo = RandomGaussiano.randomGaussTerremoto(4,1);
	}

	public Double getMagnitudo() {
		return magnitudo;
	}

	public void setMagnitudo(Double magnitudo) {
		this.magnitudo = magnitudo;
	}
	

}
