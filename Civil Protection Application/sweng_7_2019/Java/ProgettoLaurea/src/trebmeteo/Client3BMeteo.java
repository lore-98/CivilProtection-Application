package trebmeteo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import condivise.SistemaCentraleInterface;
import eventi.AccadimentoMeteo;
import eventi.PrevisioneMeteo;

public class Client3BMeteo {

	public static void main(String[] args) {
		AppPrevisioneMeteo trebmeteo = new AppPrevisioneMeteo();
		try { 
			Registry registry = LocateRegistry.getRegistry(12345); 
			SistemaCentraleInterface stub = (SistemaCentraleInterface) registry.lookup("sistemaCentrale"); 
			while (true) {


				long start = System.currentTimeMillis();
				long end = start + 20 * 1000; 
				while (System.currentTimeMillis() < end) {
				}
				for(int i=0;i<4;i++)
				{
					PrevisioneMeteo pm;
					pm=trebmeteo.elaboraPrevisioneMeteo();
					stub.controllaDuplicatiPrev(pm);
					stub.salvaPrevisioneMeteo(pm);
				}

				AccadimentoMeteo am;
				long start1 = System.currentTimeMillis();
				long end1 = start1 + 5 * 1000; 
				while (System.currentTimeMillis() < end1) {
				}
				am=trebmeteo.registraAccadimentoMeteo();
				stub.controllaDuplicatiAcc(am);
				stub.salvaAccadimentoMeteo(am);
			}
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString()); e.printStackTrace(); 
		}

	}
}
