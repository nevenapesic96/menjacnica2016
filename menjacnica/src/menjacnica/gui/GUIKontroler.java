package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	//klasa na logickom nivou
	protected static Menjacnica sistem;
	
	public static MenjacnicaGUI frame;
	public static DodajKursGUI prozorDodajKurs;
	public static ObrisiKursGUI prozorObrisiKurs;
	public static IzvrsiZamenuGUI prozorIzvrsiZamenu;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 sistem = new Menjacnica();
					 frame= new MenjacnicaGUI();
					 frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//MenjacnicaGUI
	
	public static void ugasiAplikaciju(){
		int opcija = JOptionPane.showConfirmDialog(frame.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziDodajKursGUI(){
		prozorDodajKurs = new DodajKursGUI(frame);
		prozorDodajKurs.setLocationRelativeTo(frame.getContentPane());
		prozorDodajKurs.setVisible(true);
	}
	
	public static void prikaziObrisiKursGUI(Valuta valuta){
			prozorObrisiKurs = new ObrisiKursGUI(frame,valuta);
			prozorObrisiKurs.setLocationRelativeTo(frame.getContentPane());
			prozorObrisiKurs.setVisible(true);
	}
	
	public static void prikaziIzvrsiZamenuGUI(Valuta valuta){
			prozorIzvrsiZamenu = new IzvrsiZamenuGUI(frame,valuta);
			prozorIzvrsiZamenu.setLocationRelativeTo(frame.getContentPane());
			prozorIzvrsiZamenu.setVisible(true);
	}
	public static void ucitajIzFajla(){
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				frame.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void sacuvajUFajl(){
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(frame,
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	//DodajKursGUI
	
	public static void unesiKurs(String naziv, String skrNaziv, int sifra, double prodajni,double kupovni, double srednji){
		try {
			Valuta valuta = new Valuta();
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skrNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			// Dodavanje valute u kursnu listu
			sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			frame.prikaziSveValute();
			
			//Zatvaranje DodajValutuGUI prozora
			prozorDodajKurs.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void zatvoriProzorDodaj(){
		prozorDodajKurs.dispose();
	}
	//ObrisiKursGUI
	
	
	public static void obrisiValutu(Valuta valuta){
		try{
			sistem.obrisiValutu(valuta);
			
			frame.prikaziSveValute();
			prozorObrisiKurs.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void zatvoriProzorObrisi(){
		prozorObrisiKurs.dispose();
	}
	
	//Izvrsi zamenu
	
	public static double izvrsiZamenu(Valuta valuta){
			double konacniIznos = 
					sistem.izvrsiTransakciju(valuta,
							prozorIzvrsiZamenu.daLiJeSelektovanRdbtnProdaja(), 
							Double.parseDouble(prozorIzvrsiZamenu.vratiIznos()));
		
		return konacniIznos;
	}
	public static LinkedList<Valuta> vratiKursnuListu() {
		return sistem.vratiKursnuListu();
	}
}
