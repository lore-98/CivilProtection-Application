package trebmeteo;

import condivise.RandomGaussiano;
import eventi.AccadimentoMeteo;
import eventi.PrevisioneMeteo;

public class AppPrevisioneMeteo {
	private static AppPrevisioneMeteo instance = null;
	private String nome = "3bMeteo";

	protected AppPrevisioneMeteo() {}

	public static AppPrevisioneMeteo getInstance() {
		if(instance==null) 
			instance = new AppPrevisioneMeteo();
		return instance;
	}

	public PrevisioneMeteo elaboraPrevisioneMeteo() {

		PrevisioneMeteo previsioneMeteo = new PrevisioneMeteo(this.nome);
		switch(previsioneMeteo.getTipo()) {
		case "Vento": //valori espressi in Km\h
			double vento = RandomGaussiano.randomGaussPrecipitazione(15,15,230);
			previsioneMeteo.setPrecipitazione(vento);
			if(vento>0 && vento<10)
				previsioneMeteo.setLivelloGravità(1);
			if(vento>=10 && vento<20)
				previsioneMeteo.setLivelloGravità(2);
			if(vento>=20 && vento<30)
				previsioneMeteo.setLivelloGravità(3);
			if(vento>=30 && vento<40)
				previsioneMeteo.setLivelloGravità(4);
			if(vento>=40 && vento<50)
				previsioneMeteo.setLivelloGravità(5);
			if(vento>=50 && vento<60)
				previsioneMeteo.setLivelloGravità(6);
			if(vento>=60 && vento<70)
				previsioneMeteo.setLivelloGravità(7);
			if(vento>=70 && vento<90)
				previsioneMeteo.setLivelloGravità(8);
			if(vento>=90 && vento<110)
				previsioneMeteo.setLivelloGravità(9);
			if(vento>=110 && vento<230)
				previsioneMeteo.setLivelloGravità(10);
			break;
		case "Pioggia": //valori espressi in mm\h
			double pioggia = RandomGaussiano.randomGaussPrecipitazione(12,9,60);
			previsioneMeteo.setPrecipitazione(pioggia);
			if(pioggia>0 && pioggia<1)
				previsioneMeteo.setLivelloGravità(1);
			if(pioggia>=1 && pioggia<6)
				previsioneMeteo.setLivelloGravità(2);
			if(pioggia>=6 && pioggia<10)
				previsioneMeteo.setLivelloGravità(3);
			if(pioggia>=10 && pioggia<15)
				previsioneMeteo.setLivelloGravità(4);
			if(pioggia>=15 && pioggia<23)
				previsioneMeteo.setLivelloGravità(5);
			if(pioggia>=23 && pioggia<30)
				previsioneMeteo.setLivelloGravità(6);
			if(pioggia>=30 && pioggia<39)
				previsioneMeteo.setLivelloGravità(7);
			if(pioggia>=40 && pioggia<46)
				previsioneMeteo.setLivelloGravità(8);
			if(pioggia>=46 && pioggia<52)
				previsioneMeteo.setLivelloGravità(9);
			if(pioggia>=52 && pioggia<60)
				previsioneMeteo.setLivelloGravità(10);
			break;
		case "Neve": //valori espressi in cm\h
			double neve = RandomGaussiano.randomGaussPrecipitazione(1,1,2.5);
			previsioneMeteo.setPrecipitazione(neve);
			if(neve>0.0 && neve<0.1)
				previsioneMeteo.setLivelloGravità(1);
			if(neve>=0.1 && neve<0.25)
				previsioneMeteo.setLivelloGravità(2);
			if(neve>=0.25 && neve<0.4)
				previsioneMeteo.setLivelloGravità(3);
			if(neve>=0.4 && neve<0.6)
				previsioneMeteo.setLivelloGravità(4);
			if(neve>=0.6 && neve<0.8)
				previsioneMeteo.setLivelloGravità(5);
			if(neve>=0.8 && neve<1)
				previsioneMeteo.setLivelloGravità(6);
			if(neve>=1 && neve<1.3)
				previsioneMeteo.setLivelloGravità(7);
			if(neve>=1.3 && neve<1.5)
				previsioneMeteo.setLivelloGravità(8);
			if(neve>=1.5 && neve<2)
				previsioneMeteo.setLivelloGravità(9);
			if(neve>=2 && neve<2.5)
				previsioneMeteo.setLivelloGravità(10);
			break;
		case "Grandine": //valori espressi in diametro[mm] del chicco
			double grandine = RandomGaussiano.randomGaussPrecipitazione(12,3,125);
			previsioneMeteo.setPrecipitazione(grandine);
			if(grandine>0 && grandine<7)
				previsioneMeteo.setLivelloGravità(1);
			if(grandine>=7 && grandine<12)
				previsioneMeteo.setLivelloGravità(2);
			if(grandine>=12 && grandine<15)
				previsioneMeteo.setLivelloGravità(3);
			if(grandine>=15 && grandine<20)
				previsioneMeteo.setLivelloGravità(4);
			if(grandine>=20 && grandine<30)
				previsioneMeteo.setLivelloGravità(5);
			if(grandine>=30 && grandine<45)
				previsioneMeteo.setLivelloGravità(6);
			if(grandine>=45 && grandine<60)
				previsioneMeteo.setLivelloGravità(7);
			if(grandine>=60 && grandine<80)
				previsioneMeteo.setLivelloGravità(8);
			if(grandine>=80 && grandine<100)
				previsioneMeteo.setLivelloGravità(9);
			if(grandine>=100 && grandine<125)
				previsioneMeteo.setLivelloGravità(10);
			break;
		default: break;
		}
		return previsioneMeteo;

	}

	public AccadimentoMeteo registraAccadimentoMeteo() {

		AccadimentoMeteo accadimentoMeteo = new AccadimentoMeteo(this.nome);
		switch(accadimentoMeteo.getTipo()) {
		case "Vento": //valori espressi in Km\h
			double vento = RandomGaussiano.randomGaussPrecipitazione(15,15,230);
			accadimentoMeteo.setPrecipitazione(vento);

			break;
		case "Pioggia": //valori espressi in mm\h
			double pioggia = RandomGaussiano.randomGaussPrecipitazione(12,9,60);
			accadimentoMeteo.setPrecipitazione(pioggia);

			break;
		case "Neve": //valori espressi in cm\h
			double neve = RandomGaussiano.randomGaussPrecipitazione(1,1,2.5);
			accadimentoMeteo.setPrecipitazione(neve);

			break;
		case "Grandine": //valori espressi in diametro[mm] del chicco
			double grandine = RandomGaussiano.randomGaussPrecipitazione(12,3,125);
			accadimentoMeteo.setPrecipitazione(grandine);

			break;
		default: break;
		}

		return accadimentoMeteo;
	}

}
