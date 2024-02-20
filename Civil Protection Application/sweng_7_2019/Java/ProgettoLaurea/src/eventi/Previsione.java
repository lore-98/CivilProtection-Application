package eventi;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public abstract class Previsione extends Evento{ //questa classe è inutile, si fa prima a mettere il flag nelle classi figlie e basta
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int livelloGravità;

	public Previsione(String sorgente) {
		super(sorgente);
		this.tempo = tempoCasuale();
	}


	public Timestamp tempoCasuale() {
		//Per generare i due range da cui pescare la data per la previsione
		Date date = new Date();
		int oneweek = 7;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR,oneweek);
		Date date1= calendar.getTime();

		// questo long currentTime genera il valore long del timestamp corrente (andrebbe sostituito a offset), bisogna trovare la soluzione di come imporre l'end che sia 1 settimana dopo il currentTime
		long currentTime = date.getTime(); 
		long offset1 = date1.getTime();
		long diff = offset1 - currentTime + 1;
		Timestamp rand = new Timestamp(currentTime + (long)(Math.random() * diff));
		return rand;
	}


	public int getLivelloGravità() {
		return livelloGravità;
	}


	public void setLivelloGravità(int livelloGravità) {
		this.livelloGravità = livelloGravità;
	}

}