package hahmonakymakokeilu;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class HahmoNakyma extends JPanel {

	private ArrayList<Info> tiedot;

	//Tiedotosion arvot
	private JLabel nimi_komponentti;
	private JLabel luokka_komponentti;

	private JLabel piilota;

	private JTextArea tarina_komponentti;
	private JLabel tausta;
	private JScrollPane tarina_alue;

	//Valittu nappi
	HahmoLabel valittu;

	//Listan tiedot
	private JPanel hahmot;

	public HahmoNakyma(Dimension dim){
		super();
		tiedot = new ArrayList<>();
		this.setLayout(null);
		this.setBounds(0, 0, (int)dim.getWidth(), (int)dim.getHeight());
		this.setBackground(Color.DARK_GRAY);
		this.setVisible(true);
		luoTiedot();
		alustamuut();
		alustaLista();
		//Piilotusnappi
		piilota = new JLabel();
		piilota.setBounds(dim.width-40, 0, 40, 40);
		piilota.setVisible(true);
		piilota.setOpaque(true);
		piilota.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				asetaNakyvaksi(false);
			}
		});
		
		this.add(piilota);
		//Tausta
		tausta = new JLabel();
		tausta.setBounds(0, 0, dim.width, dim.height);
		tausta.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/bg.png"))));
		tausta.setOpaque(false);
		tausta.setVisible(true);
		this.add(tausta);
	}



	public void alustamuut(){
		//Fontit
		Font nimiFontti = new Font("Verdana", Font.HANGING_BASELINE, 40);
		Font tarinaFontti = new Font("Verdana", Font.PLAIN, 12);
		Font luokkaFontti = new Font("Verdana", Font.ROMAN_BASELINE, 14);

		nimi_komponentti = new JLabel(tiedot.get(0).annaNimi());
		nimi_komponentti.setFont(nimiFontti);
		nimi_komponentti.setOpaque(false);
		nimi_komponentti.setBounds(224,48,352,60);
		nimi_komponentti.setHorizontalAlignment(SwingConstants.CENTER);
		nimi_komponentti.setVisible(true);

		luokka_komponentti = new JLabel("Luokka: "+tiedot.get(0).annaLuokka());
		luokka_komponentti.setFont(luokkaFontti);
		luokka_komponentti.setOpaque(false);
		luokka_komponentti.setBounds(472,108,104,20);
		luokka_komponentti.setVisible(true);


		tarina_komponentti = new JTextArea(tiedot.get(0).annaTarina());
		tarina_komponentti.setCaretPosition(0);
		tarina_alue = new JScrollPane(tarina_komponentti, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tarina_komponentti.setFont(tarinaFontti);
		tarina_komponentti.setCursor(null);
		tarina_komponentti.setOpaque(false);
		tarina_komponentti.setEditable(false);
		tarina_komponentti.setWrapStyleWord(true);
		tarina_komponentti.setLineWrap(true);
		tarina_alue.setBounds(224, 108, 247, 324);
		tarina_komponentti.setVisible(true);
		tarina_komponentti.setCaretPosition(0);
		tarina_alue.getVerticalScrollBar().setUI(new OmaScrollBar());
		tarina_alue.setOpaque(false);
		tarina_alue.getViewport().setOpaque(false);
		tarina_alue.getViewport().setBackground(null);
		tarina_alue.setVisible(true);
		tarina_alue.setBorder(null);
		
		//Komponenttien lis�ys
		this.add(nimi_komponentti);
		this.add(luokka_komponentti);
		this.add(tarina_alue);
	}

	public void alustaLista(){
		ImageIcon tausta = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/hahmoNappi.png")));
		ImageIcon taustaValittu = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/hahmoNappiHover.png")));
		ImageIcon taustaHover = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/hahmoNappiValittu.png")));
		hahmot = new JPanel();
		hahmot.setLayout(new BoxLayout(hahmot, BoxLayout.Y_AXIS));
		hahmot.setBounds(64, 48,160, 384);
		hahmot.setBackground(null);
		hahmot.setVisible(true);
		Dimension empty = new Dimension(0, 6);
		for (int i = 0; i < tiedot.size(); i++) {				
			HahmoLabel nappi = new HahmoLabel(tausta, taustaValittu, taustaHover,tiedot.get(i).nimi, i);
			nappi.setText(tiedot.get(i).annaNimi());
			nappi.setVisible(true);
			nappi.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(nappi);
			nappi.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int id = nappi.annaId();
					nimi_komponentti.setText(tiedot.get(id).annaNimi());
					luokka_komponentti.setText("Luokka: "+tiedot.get(id).annaLuokka());
					tarina_komponentti.setText(tiedot.get(id).annaTarina());
					tarina_komponentti.setCaretPosition(0);
					tarina_alue.getVerticalScrollBar().setValue(tarina_alue.getVerticalScrollBar().getMaximum());
					valittu.asetaNormaaliksi();
					valittu = nappi;
					valittu.asetaValituksi();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					if(nappi != valittu){
						nappi.asetaHover();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(nappi != valittu){
						nappi.asetaNormaaliksi();
					}
				}

			});
			if(i == 0){
				nappi.asetaValituksi();
				valittu = nappi;
			}
			hahmot.add(new Box.Filler(empty, empty, empty));
			hahmot.add(nappi);

		}	
		this.add(hahmot);		
	}


	/**
	 * Alustus tietojen luomista varten, tullaan lopulta luomaan tiedostoista /res kansiossa
	 */
	public void luoTiedot(){
		tiedot.add(new Info("RagePoika", "Lahi", "tarinaaaaaaaa"));
		tiedot.add(new Info("Zelai", "Lahi", "Zelai syntyi kahden luostarilupauksen antaneen ihmisen lapsena. H�nen syntym��ns� ei katsottu hyv�ll�, joten Zelain vanhemmat p��ttiv�t paeta sen hetkiselt� alueelta. Alueen hallitsija ei sallinut t�t� ja l�hetti miehet ottamaan kolmikon kiinni. Kidutus naiselle: choke pear (katso mik� suomeksi..) ja miehelle hengilt� nylkeminen p��st� alasp�in. Zelai oltaisiin heitetty kiehuvaan �ljyyn, mutta kiduttaja otti Zelain omaksi lapseksensa. Kun Zelai oli 5:den vanha niin mies vei Zelain opettelemaan h�nen kanssansa kidutusmenetelmi�. 7-vuotiaana Zelai sai kuulla ett� mies oli kiduttanut h�nen vanhempansa hengilt� yleisen uskonnon perusteella. Zelai sy�tt�� miehelle ainetta joka heikent�� hetkellisesti tajuntaa ja suostuttelee miehen menem��n vapaehtoisesti judaksen tuoliin. Mies kuolee hitaasti ja Zelai poistuu etsim��n tarinoiden kulttia, joka yritt�� puhdistaa nykyist� uskontoa. Vuosien my�t� Zelai l�yt�� kultin ja p��see liittym��n."));
		tiedot.add(new Info("Rogue", "Lahi", "tarinaaaaaaaa"));
		tiedot.add(new Info("SexyAmatsoni", "Lahi", "tarinaaaaaaaa"));
		tiedot.add(new Info("Ponipoju", "Matka", "tarinaaaaaaaa"));
		tiedot.add(new Info("Gladiaattori", "Lahi", "tarinaaaaaaaa"));
		tiedot.add(new Info("Druidi", "Matka", "tarinaaaaaaaa"));
		tiedot.add(new Info("VoodooMuija", "Matka", "tarinaaaaaaaa"));
	}

	/**
	 * Metodi jonka avulla voidaan asettaa HahmoNakyma-paneeli n�kyv�ksi ja ei
	 * Paneelista l�ytyy JLabel, joka piilottaa t�m�n n�kym�n tarvittaessa.
	 * @param totuus
	 */
	public void asetaNakyvaksi(boolean totuus){
		this.setVisible(totuus);
	}
	
	class Info{

		private String nimi;
		private String luokka;
		private String tarina;

		public Info(String nimi, String luokka, String tarina){
			this.asetaNimi(nimi);
			this.asetaLuokka(luokka);
			this.asetaTarina(tarina);
		}

		public String annaNimi() {
			return nimi;
		}

		public String annaLuokka() {
			return luokka;
		}

		public String annaTarina() {
			return tarina;
		}

		public void asetaNimi(String nimi) {
			this.nimi = nimi;
		}

		public void asetaLuokka(String luokka) {
			this.luokka = luokka;
		}

		public void asetaTarina(String tarina) {
			this.tarina = tarina;
		}
	}


}