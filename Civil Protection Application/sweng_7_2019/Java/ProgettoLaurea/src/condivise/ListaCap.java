package condivise;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ListaCap {
	CAP1(23895), CAP2(22020), CAP3(22030), CAP4(20100),
	CAP5(30100), CAP6(90121), CAP7(87010), CAP8(59013),
	CAP9(71010), CAP10(10121), CAP11(80010), CAP12(50100);

	private final int value;

	private ListaCap(int value) {
		this.value = value;
	}

	public static int getCap() {
		List<ListaCap> values = Collections.unmodifiableList(Arrays.asList(values()));    	  
		int size = values.size();    
		Random random = new Random();
		return (values.get(random.nextInt(size))).value;

	}

	public static List<ListaCap> getListaCap(){

		return Collections.unmodifiableList(Arrays.asList(values()));   
	}

	public int getValue() {
		return value;
	}

}
