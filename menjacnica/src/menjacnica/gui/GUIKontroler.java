package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

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
	
	public static void prikaziObrisiKursGUI(int selektovanRed, TableModel tableModel){
		if (selektovanRed != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(tableModel);
			ObrisiKursGUI prozorObrisiKurs = new ObrisiKursGUI(frame,
					model.vratiValutu(selektovanRed));
			prozorObrisiKurs.setLocationRelativeTo(frame);
			prozorObrisiKurs.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI(int selektovanRed, TableModel tableModel){
		if (selektovanRed != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(tableModel);
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(frame,
					model.vratiValutu(selektovanRed));
			prozor.setLocationRelativeTo(frame);
			prozor.setVisible(true);
		}
	}
	public static void ucitajIzFajla(TableModel tableModel){
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute(tableModel);
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	protected static void prikaziSveValute(TableModel tableModel) {
		MenjacnicaTableModel model = (MenjacnicaTableModel)(tableModel);
		model.staviSveValuteUModel(sistem.vratiKursnuListu());

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
	
	public static void unesiKurs(String naziv, String skraceniNaziv, Object sifra, String prodajni, String kupovni, String srednji){
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra((Integer)(sifra));
			valuta.setProdajni(Double.parseDouble(prodajni));
			valuta.setKupovni(Double.parseDouble(kupovni));
			valuta.setSrednji(Double.parseDouble(srednji));
			
			// Dodavanje valute u kursnu listu
			sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			prikaziSveValute((TableModel)frame.vratiTabelu());
			
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
	
	public static void prikaziValutu(Valuta valuta){
		prozorObrisiKurs.upisiNaziv(valuta.getNaziv());
		prozorObrisiKurs.upisiSkraceniNaziv(valuta.getSkraceniNaziv());
		prozorObrisiKurs.upisiSifru(""+valuta.getSifra());
		prozorObrisiKurs.upisiProdajni(""+valuta.getProdajni());
		prozorObrisiKurs.upisiKupovni(""+valuta.getKupovni());
		prozorObrisiKurs.upisiSrednji(""+valuta.getSrednji());
	}
	
	public static void obrisiValutu(Valuta valuta){
		try{
			sistem.obrisiValutu(valuta);
			
			prikaziSveValute((TableModel)frame.vratiTabelu());
			prozorObrisiKurs.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void zatvoriProzorObrisi(){
		prozorObrisiKurs.dispose();
	}
}
