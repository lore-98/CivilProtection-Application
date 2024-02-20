package condivise;

import java.util.Random;

public class RandomGaussiano {

	public static double randomGaussTerremoto(double media, double varianza) {
		Random fRandom = new Random();
		double magnitudo;
		do
		{
			magnitudo=media + fRandom.nextGaussian() * varianza;
		}
		while( magnitudo <= 0.01 || magnitudo > 13); 

		return Math.round(magnitudo * 10) / 10.0;

	}
	
	public static double randomGaussPrecipitazione(double media, double varianza, double massimo) {
		Random fRandom = new Random();
		double precipitazione;
		do
		{
			precipitazione=media + fRandom.nextGaussian() * varianza;
		}
		while( precipitazione <= 0.01 || precipitazione > massimo); 

		return Math.round(precipitazione * 10) / 10.0;

	}
}