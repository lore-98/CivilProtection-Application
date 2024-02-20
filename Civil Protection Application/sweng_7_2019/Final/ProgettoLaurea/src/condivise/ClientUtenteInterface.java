package condivise;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

public interface ClientUtenteInterface extends Remote {
	public void update(List<Notifica> notifiche) throws RemoteException;
	public List<String> ottieniPrevisioni(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo) throws RemoteException;
	public List<String> ottieniAccadimenti(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo) throws RemoteException;
}
