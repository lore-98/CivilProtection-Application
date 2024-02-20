package ingv;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import condivise.SistemaCentraleInterface;
import eventi.AccadimentoTerremoto;
import eventi.PrevisioneTerremoto;

public class ClientINGV {

	public static void main(String[] args) {
		AppPrevisioneTerremoto ingv = new AppPrevisioneTerremoto();
		try { 
			Registry registry = LocateRegistry.getRegistry(12345); 
			SistemaCentraleInterface stub = (SistemaCentraleInterface) registry.lookup("sistemaCentrale"); 
			while (true) {


				long start = System.currentTimeMillis();
				long end = start + 25 * 1000; // 60 seconds * 1000 ms/sec
				while (System.currentTimeMillis() < end) {
				}
				for(int i=0;i<4;i++)
				{
					PrevisioneTerremoto pt;
					pt=ingv.elaboraPrevisioneTerremoto();
					stub.controllaDuplicatiPrev(pt);
					stub.salvaPrevisioneTerremoto(pt);
				}

				AccadimentoTerremoto at;
				long start1 = System.currentTimeMillis();
				long end1 = start1 + 5 * 1000; // 60 seconds * 1000 ms/sec
				while (System.currentTimeMillis() < end1) {
				}
				at=ingv.registraAccadimentoTerremoto();
				stub.controllaDuplicatiAcc(at);
				stub.salvaAccadimentoTerremoto(at);
			}
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString()); e.printStackTrace(); 
		}

	}
}



