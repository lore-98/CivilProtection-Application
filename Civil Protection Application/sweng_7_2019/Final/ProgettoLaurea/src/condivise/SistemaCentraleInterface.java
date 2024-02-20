package condivise;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;
import eventi.Accadimento;
import eventi.AccadimentoMeteo;
import eventi.AccadimentoTerremoto;
import eventi.Previsione;
import eventi.PrevisioneMeteo;
import eventi.PrevisioneTerremoto;


public interface SistemaCentraleInterface extends Remote{
	public void inviaNotifica() throws RemoteException; //cambiare il tipo che dovrebbe essere notifica :)
	public void salvaPrevisioneMeteo(PrevisioneMeteo pm) throws RemoteException;
	public void salvaPrevisioneTerremoto(PrevisioneTerremoto pt) throws RemoteException;
	public void salvaAccadimentoMeteo(AccadimentoMeteo am) throws RemoteException;
	public void salvaAccadimentoTerremoto(AccadimentoTerremoto at) throws RemoteException;
	public void controllaDuplicatiPrev(Previsione p) throws RemoteException;
	public void controllaDuplicatiAcc(Accadimento a) throws RemoteException;
	public List<Accadimento> ricercaAcc(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo) throws RemoteException;
	public List<Previsione> ricercaPrev(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo) throws RemoteException;
	public void registraUtente(Integer cap, ClientUtenteInterface utente) throws RemoteException;
}