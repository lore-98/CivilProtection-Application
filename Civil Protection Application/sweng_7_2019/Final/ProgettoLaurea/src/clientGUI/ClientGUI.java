package clientGUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Color;

import com.ibm.icu.util.Calendar;
import com.toedter.calendar.JDateChooser;
import condivise.ListaCap;
import condivise.SistemaCentraleInterface;
import condivise.TipoEvento;
import utente.ClientUtente;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.beans.PropertyChangeEvent;

public class ClientGUI extends JFrame implements Serializable{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameTextField;
	JPanel LoginPage ;
	JPanel HomePage ;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultListModel<String> storicoAllerte = new DefaultListModel<String>();
	private DefaultListModel<String> previsioniCercate = new DefaultListModel<String>();
	private DefaultListModel<String> accadimentiCercati = new DefaultListModel<String>();
	private ClientUtente client = null;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registry r = LocateRegistry.getRegistry(12345);
					SistemaCentraleInterface server = (SistemaCentraleInterface)r.lookup("sistemaCentrale");
					ClientGUI frame = new ClientGUI(server);
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Impossibile Connettersi al server");
					e.printStackTrace();
				}
			}
		});
	}

	public ClientGUI(SistemaCentraleInterface server) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 849, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		JPanel LoginPage = new JPanel();

		LoginPage.setBackground(new Color(176, 224, 230));
		contentPane.add(LoginPage, "login");
		LoginPage.setLayout(null);

		usernameTextField = new JTextField();
		usernameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		usernameTextField.setToolTipText("Inserisci username");
		usernameTextField.setBounds(316, 210, 137, 20);
		LoginPage.add(usernameTextField);
		usernameTextField.setColumns(10);


		JLabel lblLoginTitle = new JLabel("BENVENUTO NELL'APP");
		lblLoginTitle.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblLoginTitle.setBounds(188, 68, 417, 43);
		LoginPage.add(lblLoginTitle);

		JLabel lblProtezioneCivile = new JLabel("Protezione Civile");
		lblProtezioneCivile.setFont(new Font("Tahoma", Font.ITALIC, 30));
		lblProtezioneCivile.setBounds(270, 122, 243, 26);
		LoginPage.add(lblProtezioneCivile);


		JLabel lblUsername = new JLabel("Inserisci il tuo username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsername.setBounds(263, 184, 243, 14);
		LoginPage.add(lblUsername);

		JLabel lblCapLogin = new JLabel("Inserisci il cap di residenza");
		lblCapLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblCapLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCapLogin.setBounds(263, 245, 250, 23);
		LoginPage.add(lblCapLogin);
		JLabel lblLogoLogin = new JLabel("");
		lblLogoLogin.setIcon(new ImageIcon(ClientGUI.class.getResource("/meteo.png")));
		lblLogoLogin.setBounds(689, 11, 100, 100);
		LoginPage.add(lblLogoLogin);

		JPanel HomePage = new JPanel();
		HomePage.setBackground(new Color(176, 224, 230));
		contentPane.add(HomePage, "home");
		HomePage.setLayout(null);

		JLabel lblUsernameHome = new JLabel("");
		lblUsernameHome.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblUsernameHome.setBounds(111, 30, 136, 14);
		HomePage.add(lblUsernameHome);

		JLabel lblBenvenuto = new JLabel("Benvenuto");
		lblBenvenuto.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBenvenuto.setBounds(27, 30, 85, 14);
		HomePage.add(lblBenvenuto);




		JComboBox<Integer> combo_CapLogin = new JComboBox<Integer>();
		combo_CapLogin.setToolTipText("Seleziona cap");
		combo_CapLogin.setBounds(316, 279, 137, 20);
		LoginPage.add(combo_CapLogin);
		List<Integer> cap = new ArrayList<Integer>();
		List<ListaCap> listaCap = ListaCap.getListaCap();
		for(ListaCap c: listaCap) {
			cap.add(c.getValue());
		}
		for(Integer i: cap) {
			combo_CapLogin.addItem(i);
		}

		combo_CapLogin.setSelectedIndex(-1);

		ClientGUI that = this;

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameTextField.getText();
				Integer cap = (Integer) combo_CapLogin.getSelectedItem();
				if(!username.equals("") && cap != null) {
					try {
						client = new ClientUtente(username, cap, that, server);
						server.registraUtente(cap, client);
					}catch(RemoteException e1){
						JOptionPane.showMessageDialog(LoginPage, "Impossibile Connettersi al server");
						e1.printStackTrace();
					}
					CardLayout layout = (CardLayout) contentPane.getLayout();
					lblUsernameHome.setText(username + " (" + cap + ")");
					layout.show(contentPane,"home");
				}

				else {
					JOptionPane.showMessageDialog(LoginPage, "Completare tutti i campi.");
				}
			}
		});
		btnLogin.setBounds(351, 330, 67, 23);
		LoginPage.add(btnLogin);




		JLabel lblRicerca = new JLabel("Ricerca");
		lblRicerca.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRicerca.setBounds(83, 231, 65, 14);
		HomePage.add(lblRicerca);

		JScrollPane scollAllerte = new JScrollPane();
		scollAllerte.setBounds(27, 74, 764, 144);
		HomePage.add(scollAllerte);




		JScrollPane scrollPrevisioni = new JScrollPane();
		scrollPrevisioni.setBounds(199, 264, 592, 111);
		HomePage.add(scrollPrevisioni);

		JLabel lblAllarmi = new JLabel("Allarmi in evidenza");
		lblAllarmi.setForeground(new Color(0, 0, 0));
		lblAllarmi.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAllarmi.setBounds(341, 27, 172, 14);
		HomePage.add(lblAllarmi);

		JLabel lblPrevisioni = new JLabel("Previsioni");
		lblPrevisioni.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPrevisioni.setBounds(448, 239, 87, 14);
		HomePage.add(lblPrevisioni);

		JScrollPane scrollAccadimenti = new JScrollPane();
		scrollAccadimenti.setBounds(199, 411, 592, 111);
		HomePage.add(scrollAccadimenti);

		JLabel lblAccadimenti = new JLabel("Accadimenti/Storico");
		lblAccadimenti.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAccadimenti.setBounds(413, 386, 172, 14);
		HomePage.add(lblAccadimenti);

		JDateChooser dateChooser = new JDateChooser();

		dateChooser.setBounds(47, 272, 116, 22);
		HomePage.add(dateChooser);
		Timestamp tc = new Timestamp(System.currentTimeMillis());
		dateChooser.setMinSelectableDate(tc);
		dateChooser.setDate(tc);

		JDateChooser dateChooser_1 = new JDateChooser();

		dateChooser_1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				dateChooser.setMaxSelectableDate(dateChooser_1.getDate());  
			}
		});

		dateChooser_1.setBounds(47, 320, 116, 22);
		HomePage.add(dateChooser_1);

		dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				dateChooser_1.setMinSelectableDate(dateChooser.getDate());
			}
		});
		JComboBox<String> combo_TipoEvento = new JComboBox<String>();
		combo_TipoEvento.setBounds(47, 368, 116, 22);
		HomePage.add(combo_TipoEvento);
		List<String> listaEventi = TipoEvento.getListaEventi();
		combo_TipoEvento.addItem(null);
		for(String s: listaEventi) {
			combo_TipoEvento.addItem(s);
		}


		combo_TipoEvento.addItem("Terremoto");
		combo_TipoEvento.setSelectedIndex(0);

		JLabel lblDataInizio = new JLabel("Data inizio");
		lblDataInizio.setBounds(47, 255, 65, 14);
		HomePage.add(lblDataInizio);

		JLabel lblDataFine = new JLabel("Data fine");
		lblDataFine.setBounds(47, 305, 65, 14);
		HomePage.add(lblDataFine);

		JLabel lblTipoEvento = new JLabel("Tipo evento");
		lblTipoEvento.setBounds(47, 351, 70, 14);
		HomePage.add(lblTipoEvento);

		JLabel lblCapRicerca = new JLabel("Cap");
		lblCapRicerca.setBounds(47, 401, 46, 14);
		HomePage.add(lblCapRicerca);

		JComboBox<Integer> combo_CapHome = new JComboBox<Integer>();
		combo_CapHome.setBounds(47, 417, 116, 22);
		HomePage.add(combo_CapHome);
		combo_CapHome.addItem(null);
		for(Integer i: cap) {
			combo_CapHome.addItem(i);
		}


		combo_CapHome.setSelectedIndex(0);

		JRadioButton rdbtnPrevisioni = new JRadioButton("Previsioni");
		rdbtnPrevisioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateChooser.setDate(tc);
				dateChooser_1.setDate(null);
				dateChooser.setMinSelectableDate(tc);
				dateChooser_1.setMaxSelectableDate(null);
			}
		});
		rdbtnPrevisioni.setSelected(true);
		rdbtnPrevisioni.setBackground(new Color(176, 224, 230));
		rdbtnPrevisioni.setBounds(47, 448, 109, 23);
		HomePage.add(rdbtnPrevisioni);

		JRadioButton rdbtnAccadimenti = new JRadioButton("Accadimenti");
		rdbtnAccadimenti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateChooser.setDate(null);
				dateChooser_1.setDate(tc);
				dateChooser.setMinSelectableDate(null);
				dateChooser_1.setMaxSelectableDate(tc);
			}
		});

		rdbtnAccadimenti.setBackground(new Color(176, 224, 230));
		rdbtnAccadimenti.setBounds(47, 479, 109, 23);
		HomePage.add(rdbtnAccadimenti);

		buttonGroup.add(rdbtnAccadimenti);
		buttonGroup.add(rdbtnPrevisioni);
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)  
			{  
				if((dateChooser.getDate() == null)
						&&( dateChooser_1.getDate() == null)
						&& (combo_TipoEvento.getSelectedItem() == null)
						&& (combo_CapHome.getSelectedItem() == null)
						) {
					JOptionPane.showMessageDialog(HomePage, "Per ricercare inserire almeno un filtro di ricerca.");
				}

				else if(rdbtnPrevisioni.isSelected()) {
					previsioniCercate.removeAllElements();

					if(dateChooser.getDate() != null && dateChooser_1.getDate() != null) {
						//CONVERSIONE IN TIMESTAMP DI DATA INIZIO E DATA FINE//
						Date utilDate = dateChooser.getDate();
						Calendar cal = Calendar.getInstance();
						cal.setTime(utilDate);
						cal.set(Calendar.MILLISECOND, 0);

						Date utilDate1 = dateChooser_1.getDate();
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(utilDate1);
						cal1.set(Calendar.MILLISECOND, 0);

						Timestamp tInizio = new Timestamp(cal.getTimeInMillis());
						Timestamp tFine = new Timestamp(cal1.getTimeInMillis());
						try {
							that.notificaPrevisioni(tInizio,tFine,(Integer) combo_CapHome.getSelectedItem(),(String) combo_TipoEvento.getSelectedItem());
						} catch (Exception e) {
							e.printStackTrace();
						}      
					} else if(dateChooser.getDate() == null && dateChooser_1.getDate() == null){
						try {
							that.notificaPrevisioni(null, null,(Integer) combo_CapHome.getSelectedItem(),(String) combo_TipoEvento.getSelectedItem());
						} catch (Exception e) {
							e.printStackTrace();
						}      
					}else {
						JOptionPane.showMessageDialog(HomePage, "Per ricercare previsioni per tempo, inserire entrambe le date.");
					}
				}
				else {
					accadimentiCercati.removeAllElements();
					if(dateChooser.getDate() != null && dateChooser_1.getDate() != null){
						//CONVERSIONE IN TIMESTAMP DI DATA INIZIO E DATA FINE//
						Date utilDate = dateChooser.getDate();
						Calendar cal = Calendar.getInstance();
						cal.setTime(utilDate);
						cal.set(Calendar.MILLISECOND, 0);

						Date utilDate1 = dateChooser_1.getDate();
						Calendar cal1 = Calendar.getInstance();
						cal1.setTime(utilDate1);
						cal1.set(Calendar.MILLISECOND, 0);

						Timestamp tInizio = new Timestamp(cal.getTimeInMillis());
						Timestamp tFine = new Timestamp(cal1.getTimeInMillis());

						try {
							that.notificaAccadimenti(tInizio,tFine,(Integer) combo_CapHome.getSelectedItem(),(String) combo_TipoEvento.getSelectedItem());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if(dateChooser_1.getDate() == null && dateChooser.getDate() == null){
						try {
							that.notificaAccadimenti( null, null,(Integer) combo_CapHome.getSelectedItem(),
									(String) combo_TipoEvento.getSelectedItem());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else { 
						JOptionPane.showMessageDialog(HomePage, "Per ricercare accadimenti per tempo, inserire entrambe le date.");
					}
				}
			}
		});
		btnSearch.setBounds(60, 509, 83, 23);
		HomePage.add(btnSearch);

		JLabel lblLogoHome = new JLabel("");
		lblLogoHome.setIcon(new ImageIcon(ClientGUI.class.getResource("/meteoHome.png")));
		lblLogoHome.setBounds(741, 11, 50, 50);
		HomePage.add(lblLogoHome);

		JLabel lblLogoRicerca = new JLabel("");
		lblLogoRicerca.setIcon(new ImageIcon(ClientGUI.class.getResource("/ricerca2.png")));
		lblLogoRicerca.setBounds(60, 229, 25, 25);
		HomePage.add(lblLogoRicerca);

		JLabel lblLogoAllarme = new JLabel("");
		lblLogoAllarme.setIcon(new ImageIcon(ClientGUI.class.getResource("/warning.png")));
		lblLogoAllarme.setBounds(291, 11, 40, 40);
		HomePage.add(lblLogoAllarme);



		JList<String> listaAllerte = new JList<String>(storicoAllerte);
		listaAllerte.setFont(new Font("Tahona", Font.BOLD, 14));
		listaAllerte.setBackground(new Color(255, 153, 153));
		scollAllerte.setViewportView(listaAllerte);
		JList<String> listaPrevisioni = new JList<String>(previsioniCercate);
		scrollPrevisioni.setViewportView(listaPrevisioni);
		JList<String> listaAccadimenti = new JList<String>(accadimentiCercati);
		scrollAccadimenti.setViewportView(listaAccadimenti);
	}

	public void notificaAllerte(List<String> allerte) {
		storicoAllerte.removeAllElements();
		for(String s: allerte) {
			storicoAllerte.addElement(s);
		}
	}

	public void notificaPrevisioni(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo) {
		List<String> previsioni = new ArrayList<String>();
		try {
			previsioni = client.ottieniPrevisioni(tempoIniz, tempoFin, idCap, tipo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(!previsioni.isEmpty()) {
			for(String s: previsioni) {
				previsioniCercate.addElement(s);
			}
		}else {
			previsioniCercate.addElement("Nessun accadimento trovato corrispondente ai parametri di ricerca");
		}


	}

	public void notificaAccadimenti(Timestamp tempoIniz, Timestamp tempoFin, Integer idCap, String tipo) {
		List<String> accadimenti = new ArrayList<String>();
		try {
			accadimenti = client.ottieniAccadimenti(tempoIniz, tempoFin, idCap, tipo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if(!accadimenti.isEmpty()) {
			for(String s: accadimenti) {
				accadimentiCercati.addElement(s);
			}
		}else {
			accadimentiCercati.addElement("Nessun accadimento trovato corrispondente ai parametri di ricerca");
		}
	}
}