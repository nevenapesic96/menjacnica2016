package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import menjacnica.Menjacnica;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	//klasa na logickom nivou
	protected static Menjacnica sistem;
	
	public static MenjacnicaGUI frame;
	
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
	
	public static void ugasiAplikaciju(){
		int opcija = JOptionPane.showConfirmDialog(frame.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziDodajKursGUI(){
		DodajKursGUI prozor = new DodajKursGUI(frame);
		prozor.setLocationRelativeTo(frame.getContentPane());
		prozor.setVisible(true);
	}
	
	public static void prikaziObrisiKursGUI(int selektovanRed, TableModel tableModel){
		if (selektovanRed != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(tableModel);
			ObrisiKursGUI prozor = new ObrisiKursGUI(frame,
					model.vratiValutu(selektovanRed));
			prozor.setLocationRelativeTo(frame);
			prozor.setVisible(true);
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
}