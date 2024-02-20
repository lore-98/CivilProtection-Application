package condivise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public enum TipoEvento {
	Pioggia, Neve, Grandine, Vento;

	public static String getTipoEvento() {
		List<TipoEvento> values = Collections.unmodifiableList(Arrays.asList(values()));    	  
		int size = values.size();    
		Random random = new Random();
		return values.get(random.nextInt(size)).name();
	}

	public static List<String> getListaEventi(){
		List<String> listaEventi = new ArrayList<String>();
		List<TipoEvento> values = Collections.unmodifiableList(Arrays.asList(values()));
		for(TipoEvento te: values) {
			listaEventi.add(te.name());
		}
		return listaEventi;    
	}

}