package serverSistemaCentrale;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import condivise.ListaCap;
import condivise.Notifica;
import condivise.SistemaCentraleInterface;
import eventi.Accadimento;
import eventi.AccadimentoMeteo;
import eventi.AccadimentoTerremoto;
import eventi.Previsione;
import eventi.PrevisioneMeteo;
import eventi.PrevisioneTerremoto;
import condivise.ClientUtenteInterface;

public class ServerSistemaCentrale extends UnicastRemoteObject implements SistemaCentraleInterface, Runnable{

	private static final long serialVersionUID = 1L;

	private HashMap<Integer,List<ClientUtenteInterface>> capUtenti = new LinkedHashMap<Integer,List<ClientUtenteInterface>>();
	private static ServerSistemaCentrale instance = null;

	Thread threadServer;
	protected ServerSistemaCentrale() throws RemoteException {
		super();
		List<ListaCap> listaCap = ListaCap.getListaCap();
		for(ListaCap c: listaCap) {
			this.capUtenti.put(c.getValue(),new ArrayList<ClientUtenteInterface>());
		}
		threadServer = new Thread(this,"threadServer");
		threadServer.start();
	}

	public static ServerSistemaCentrale getInstance() throws RemoteException {
		if(instance==null) 
			instance = new ServerSistemaCentrale();
		return instance;
	}

	public static void main(String[] args) {
		try { 
			SecurityManager sm = new MySecurityManager();
			System.setSecurityManager(sm);
			SistemaCentraleInterface server = new ServerSistemaCentrale();
			Registry registry = LocateRegistry.createRegistry(12345);
			registry.rebind("sistemaCentrale", server);
			System.err.println("Server ready"); }
		catch (Exception e) { 
			System.err.println("Server exception: " + e.toString()); 
			e.printStackTrace(); }

	}


	@Override
	public void registraUtente(Integer cap, ClientUtenteInterface utente) throws RemoteException {
		for(Integer i: capUtenti.keySet()) {
			if(i.equals(cap)){
				capUtenti.get(i).add(utente);
				return;
			}
		}  
	}

	@Override
	public void salvaPrevisioneMeteo(PrevisioneMeteo pm) throws RemoteException {

		try {
			DataBase.inserimentoPrevMeteo(pm);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ConnectionPoolException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void salvaPrevisioneTerremoto(PrevisioneTerremoto pt) throws RemoteException {
		try {
			DataBase.inserimentoPrevTerremoto(pt);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ConnectionPoolException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void salvaAccadimentoMeteo(AccadimentoMeteo am) throws RemoteException { 
		try {
			DataBase.inserimentoAccMeteo(am);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ConnectionPoolException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void salvaAccadimentoTerremoto(AccadimentoTerremoto at) throws RemoteException {
		try {
			DataBase.inserimentoAccTerremoto(at);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ConnectionPoolException e) {
			e.printStackTrace();
		}
	}

	public void controllaDuplicatiPrev(Previsione p) throws RemoteException{
		List<Timestamp> tempi=null;

		try {

			tempi = DataBase.cercaDuplicatiPrevByCapTipo(p.getCap(), p.getTipo());

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ConnectionPoolException e) {

			e.printStackTrace();
		}

		long tref = p.getTempo().getTime();

		for(Timestamp t: tempi) {
			if(t.getTime()>tref-900000 && t.getTime()<tref+900000)
				try {
					DataBase.cancellaDuplicatiPrev(p.getCap(),p.getTipo(),t);
				} catch (SQLException e) {

					e.printStackTrace();
				} catch (ConnectionPoolException e) {

					e.printStackTrace();
				}
		}
	}

	@Override
	public void controllaDuplicatiAcc(Accadimento a) throws RemoteException {
		List<Timestamp> tempi=null;

		try {

			tempi = DataBase.cercaDuplicatiAccByCapTipo(a.getCap(), a.getTipo());

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ConnectionPoolException e) {

			e.printStackTrace();
		}

		long tref = a.getTempo().getTime();

		for(Timestamp t: tempi) {
			if(t.getTime()>tref-900000 && t.getTime()<tref+900000)
				try {
					DataBase.cancellaDuplicatiAcc(a.getCap(),a.getTipo(),t);
				} catch (SQLException e) {

					e.printStackTrace();
				} catch (ConnectionPoolException e) {

					e.printStackTrace();
				}
		}

	}

	@Override
	public List<Accadimento> ricercaAcc(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo)throws RemoteException {
		List<Accadimento> risultati = null;
		List<Accadimento> risultati1 = null; 
		List<Accadimento> risultati2 = null; 

		if(tempoIniz!=null) {

			try {
				risultati=DataBase.cercaAccByTempo(tempoIniz,tempoFin);
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (ConnectionPoolException e) {

				e.printStackTrace();
			}
		}

		if(idCap!=null) {
			if(risultati==null)
				try {
					risultati=DataBase.cercaAccByCap(idCap);
				} catch (SQLException e) {

					e.printStackTrace();
				} catch (ConnectionPoolException e) {

					e.printStackTrace();
				}
			else {
				risultati1=new ArrayList<Accadimento>();
				for(Accadimento a: risultati) {
					if(a.getCap().equals(idCap))
						risultati1.add(a);
				}
			}
		}

		if(tipo!=null) {
			if(risultati==null)
				try {
					risultati=DataBase.cercaAccByTipo(tipo);
				} catch (SQLException e) {

					e.printStackTrace();
				} catch (ConnectionPoolException e) {

					e.printStackTrace();
				}
			else if (risultati1==null){
				risultati2 = new ArrayList<Accadimento>();
				for(Accadimento a: risultati) {
					if(a.getTipo().equals(tipo))
						risultati2.add(a);
				}
			} else {
				risultati2 = new ArrayList<Accadimento>();
				for(Accadimento a: risultati1) {
					if(a.getTipo().equals(tipo))
						risultati2.add(a);     
				} 
			}

		}

		if (risultati2!=null)
			return risultati2;

		if (risultati1!=null)
			return risultati1;

		return risultati;
	}

	@Override
	public List<Previsione> ricercaPrev(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo)throws RemoteException {
		List<Previsione> risultati = null; 
		List<Previsione> risultati1 = null; 
		List<Previsione> risultati2 = null; 


		if(tempoFin!=null) {
			try {
				risultati=DataBase.cercaPrevByTempo(tempoIniz,tempoFin);
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (ConnectionPoolException e) {

				e.printStackTrace();
			}
		}

		if(idCap!=null) {
			if(risultati==null)
				try {
					risultati=DataBase.cercaPrevByCap(idCap);
				} catch (SQLException e) {

					e.printStackTrace();
				} catch (ConnectionPoolException e) {

					e.printStackTrace();
				}
			else {
				risultati1=new ArrayList<Previsione>();
				for(Previsione p: risultati) {
					if(p.getCap().equals(idCap))
						risultati1.add(p);
				}
			}

		}

		if(tipo!=null) {
			if(risultati==null)
				try {
					risultati=DataBase.cercaPrevByTipo(tipo);
				} catch (SQLException e) {

					e.printStackTrace();
				} catch (ConnectionPoolException e) {

					e.printStackTrace();
				}
			else if (risultati1==null){
				risultati2 = new ArrayList<Previsione>();
				for(Previsione p: risultati) {
					if(p.getTipo().equals(tipo))
						risultati2.add(p);
				}
			} else {
				risultati2 = new ArrayList<Previsione>();
				for(Previsione p: risultati1) {
					if(p.getTipo().equals(tipo))
						risultati2.add(p);     
				} 
			}

		}

		if (risultati2!=null)
			return risultati2;

		if (risultati1!=null)
			return risultati1;

		return risultati;
	}


	@Override
	public void inviaNotifica() throws RemoteException {

		for(Integer c: capUtenti.keySet()) {

			if(!capUtenti.get(c).isEmpty()) {

				List<Previsione> allerte = new ArrayList<Previsione>();
				try {
					allerte = DataBase.trovaAllerta(c);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ConnectionPoolException e) {
					e.printStackTrace();
				}
				List<Notifica> notifiche = new ArrayList<Notifica>();
				for(Previsione p: allerte) {
					Notifica notifica = new Notifica();
					notifica.setTempo(p.getTempo());
					notifica.setTipo(p.getTipo());

					if(p instanceof PrevisioneMeteo) {
						PrevisioneMeteo pm= new PrevisioneMeteo("3bMeteo");
						pm=(PrevisioneMeteo)p;
						notifica.setParametro(pm.getPrecipitazione());
					}else {
						PrevisioneTerremoto pt= new PrevisioneTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
						pt=(PrevisioneTerremoto)p;
						notifica.setParametro(pt.getMagnitudo());
					}

					notifica.setTesto("ATTENZIONE: si prevede ");
					notifiche.add(notifica);
				}
				for(ClientUtenteInterface u: capUtenti.get(c)) {

					u.update(notifiche);
				}
			}
		}

	}

	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(60000);
				try {
					inviaNotifica();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}

		}catch (InterruptedException e){
			System.out.println("threadServer è stato interrotto");
		}

	}


}
