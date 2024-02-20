package ingv;


import eventi.AccadimentoTerremoto;
import eventi.PrevisioneTerremoto;


public class AppPrevisioneTerremoto {
	private static AppPrevisioneTerremoto instance = null;
	private String nome = "Istituto Nazionale di Geofisica e Vulcanologia";


	protected AppPrevisioneTerremoto() {}

	public static AppPrevisioneTerremoto getInstance() {
		if(instance==null) 
			instance = new AppPrevisioneTerremoto();
		return instance;
	}

	public 	PrevisioneTerremoto elaboraPrevisioneTerremoto() {
		PrevisioneTerremoto previsioneTerremoto= new PrevisioneTerremoto(this.nome);
		previsioneTerremoto.setTipo("Terremoto");
		Double magnitudo = previsioneTerremoto.getMagnitudo();
		if(magnitudo>0 && magnitudo<1)
			previsioneTerremoto.setLivelloGravità(1);
		if(magnitudo>=1 && magnitudo<2)
			previsioneTerremoto.setLivelloGravità(2);
		if(magnitudo>=2 && magnitudo<3)
			previsioneTerremoto.setLivelloGravità(3);
		if(magnitudo>=3 && magnitudo<4)
			previsioneTerremoto.setLivelloGravità(4);
		if(magnitudo>=4 && magnitudo<5)
			previsioneTerremoto.setLivelloGravità(5);
		if(magnitudo>=5 && magnitudo<6)
			previsioneTerremoto.setLivelloGravità(6);
		if(magnitudo>=6 && magnitudo<7)
			previsioneTerremoto.setLivelloGravità(7);
		if(magnitudo>=7 && magnitudo<9)
			previsioneTerremoto.setLivelloGravità(8);
		if(magnitudo>=9 && magnitudo<10)
			previsioneTerremoto.setLivelloGravità(9);
		if(magnitudo>=10 && magnitudo<13)
			previsioneTerremoto.setLivelloGravità(10);

		return previsioneTerremoto;
	}

	public AccadimentoTerremoto registraAccadimentoTerremoto() {

		AccadimentoTerremoto accadimentoTerremoto = new AccadimentoTerremoto(this.nome);
		accadimentoTerremoto.setTipo("Terremoto");

		return accadimentoTerremoto;
	}
}

