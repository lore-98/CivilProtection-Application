package utente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.icu.text.SimpleDateFormat;

import clientGUI.ClientGUI;
import condivise.ClientUtenteInterface;
import condivise.Notifica;
import condivise.SistemaCentraleInterface;
import eventi.Accadimento;
import eventi.AccadimentoMeteo;
import eventi.AccadimentoTerremoto;
import eventi.Previsione;
import eventi.PrevisioneMeteo;
import eventi.PrevisioneTerremoto;

public class ClientUtente extends UnicastRemoteObject implements ClientUtenteInterface {

	private static final long serialVersionUID = 1L;
	private final SistemaCentraleInterface server;
	private final Utente utente;
	private final ClientGUI userGUI;

	public ClientUtente(String nome, int cap, ClientGUI userGUI, SistemaCentraleInterface server) throws RemoteException {
		super();
		this.utente = new Utente(nome, cap);
		this.server = server;
		this.userGUI = userGUI;
	}

	@Override
	public void update(List<Notifica> notifiche) throws RemoteException {
		List<String> allerte = new ArrayList<String>();

		for(Notifica n: notifiche) {
			String allerta = null;
			Date date= new Date(n.getTempo().getTime());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			String data = dateFormat.format(date);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("kk:mm");
			String ore = dateFormat1.format(date);

			switch(n.getTipo()) {
			case "Pioggia":
				allerta = n.getTesto() + n.getTipo() + " in data " + data + " alle ore " + ore 
				+ " con precipitazione media di " + String.valueOf(n.getParametro()) + " mm/h.";
				break;
			case "Neve":
				allerta = n.getTesto() + n.getTipo() + " in data " + data + " alle ore " + ore 
				+ " con precipitazione media di " + String.valueOf(n.getParametro()) + " cm/h.";
				break;
			case "Vento":
				allerta = n.getTesto() + n.getTipo() + " in data " + data + " alle ore " + ore 
				+ " con velocità media di " + String.valueOf(n.getParametro()) + " Km/h.";
				break;
			case "Grandine":
				allerta = n.getTesto() + n.getTipo() + " in data " + data + " alle ore " + ore 
				+ " con diametro medio per chicco di " + String.valueOf(n.getParametro()) + " mm.";
				break;
			case "Terremoto":
				allerta = n.getTesto() + n.getTipo() + " in data " + data + " alle ore " + ore 
				+ " con magnitudo " + String.valueOf(n.getParametro()) + " della scala Richter.";
				break;
			default:
				break;
			}
			allerte.add(allerta);
		}
		userGUI.notificaAllerte(allerte);
	}

	@Override
	public List<String> ottieniPrevisioni(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo)
			throws RemoteException {

		List<Previsione> risultati = server.ricercaPrev(tempoIniz, tempoFin, idCap, tipo);
		List<String> previsioniRicercate = new ArrayList<String>();

		for(Previsione p: risultati) {

			String previsioneRicercata = null;
			Date date= new Date(p.getTempo().getTime());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			String data = dateFormat.format(date);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("kk:mm");
			String ore = dateFormat1.format(date);

			if(p instanceof PrevisioneMeteo) {
				PrevisioneMeteo pm= new PrevisioneMeteo("3bMeteo");
				pm=(PrevisioneMeteo)p;

				switch(pm.getTipo()) {
				case "Pioggia":
					previsioneRicercata = "E' prevista " + pm.getTipo() + " presso il cap " + String.valueOf(pm.getCap()) + " in data " + data + " alle ore " + ore 
					+ " con precipitazione media di " + String.valueOf(pm.getPrecipitazione()) + " mm/h.";
					break;
				case "Neve":
					previsioneRicercata = "E' prevista " + pm.getTipo() + " presso il cap " + String.valueOf(pm.getCap()) + " in data " + data + " alle ore " + ore 
					+ " con precipitazione media di " + String.valueOf(pm.getPrecipitazione()) + " cm/h.";
					break;
				case "Vento":
					previsioneRicercata = "E' previsto " + pm.getTipo() + " presso il cap " + String.valueOf(pm.getCap()) + " in data " + data + " alle ore " + ore 
					+ " con velocità media di " + String.valueOf(pm.getPrecipitazione()) + " Km/h.";
					break;
				case "Grandine":
					previsioneRicercata = "E' prevista " + pm.getTipo() + " presso il cap " + String.valueOf(pm.getCap()) + " in data " + data + " alle ore " + ore 
					+ " con diametro medio per chicco di " + String.valueOf(pm.getPrecipitazione()) + " mm.";
					break;
				default:
					break;
				}

			}else {
				PrevisioneTerremoto pt= new PrevisioneTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
				pt=(PrevisioneTerremoto)p;
				previsioneRicercata = "E' previsto un " + pt.getTipo() + " presso il cap " + String.valueOf(pt.getCap()) + " in data " + data + " alle ore " + ore 
						+ " con magnitudo " + String.valueOf(pt.getMagnitudo()) + " della scala Richter";
			}
			previsioniRicercate.add(previsioneRicercata);
		}
		return previsioniRicercate;
	}

	@Override
	public List<String> ottieniAccadimenti(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo)
			throws RemoteException {

		List<Accadimento> risultati = server.ricercaAcc(tempoIniz, tempoFin, idCap, tipo);
		List<String> accadimentiRicercati = new ArrayList<String>();

		for(Accadimento a: risultati) {

			String accadimentoRicercato = null;
			Date date= new Date(a.getTempo().getTime());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			String data = dateFormat.format(date);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("kk:mm");
			String ore = dateFormat1.format(date);

			if(a instanceof AccadimentoMeteo) {
				AccadimentoMeteo am= new AccadimentoMeteo("3bMeteo");
				am=(AccadimentoMeteo)a;

				switch(am.getTipo()) {
				case "Pioggia":
					accadimentoRicercato = "Si è verificata " + am.getTipo() + " presso il cap " + String.valueOf(am.getCap()) + " in data " + data + " alle ore " + ore 
					+ " con precipitazione media di " + String.valueOf(am.getPrecipitazione()) + " mm/h.";
					break;
				case "Neve":
					accadimentoRicercato = "Si è verificata " + am.getTipo() + " presso il cap " + String.valueOf(am.getCap()) + " in data " + data + " alle ore " + ore 
					+ " con precipitazione media di " + String.valueOf(am.getPrecipitazione()) + " cm/h.";
					break;
				case "Vento":
					accadimentoRicercato = "Si è verificato " + am.getTipo() + " presso il cap " + String.valueOf(am.getCap()) + " in data " + data + " alle ore " + ore 
					+ " con velocità media di " + String.valueOf(am.getPrecipitazione()) + " Km/h.";
					break;
				case "Grandine":
					accadimentoRicercato = "Si è verificata " + am.getTipo() + " presso il cap " + String.valueOf(am.getCap()) + " in data " + data + " alle ore " + ore 
					+ " con diametro medio per chicco di " + String.valueOf(am.getPrecipitazione()) + " mm.";
					break;
				default:
					break;
				}

			}else {
				AccadimentoTerremoto at= new AccadimentoTerremoto("Istituto Nazionale di Geofisica e Vulcanologia");
				at=(AccadimentoTerremoto)a;
				accadimentoRicercato = "Si è verificato " + at.getTipo() + " presso il cap " + String.valueOf(at.getCap()) + " in data " + data + " alle ore " + ore 
						+ " con magnitudo " + String.valueOf(at.getMagnitudo()) + " della scala Richter";
			}
			accadimentiRicercati.add(accadimentoRicercato);
		}
		return accadimentiRicercati;
	}

	public Utente getUtente() {
		return utente;
	}


}
