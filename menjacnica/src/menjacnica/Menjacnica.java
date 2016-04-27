package menjacnica;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import menjacnica.sistemskeOperacije.SODodajValutu;
import menjacnica.sistemskeOperacije.SOIzvrsiTransakciju;
import menjacnica.sistemskeOperacije.SOObrisiValutu;
import menjacnica.sistemskeOperacije.SOSacuvajUFajl;
import menjacnica.sistemskeOperacije.SOUcitajIzFajla;
import menjacnica.sistemskeOperacije.SOVratiKursnuListu;

public class Menjacnica implements MenjacnicaInterface{
	
	private LinkedList<Valuta> kursnaLista = new LinkedList<Valuta>();

	@Override
	public void dodajValutu(Valuta valuta) {
		SODodajValutu.izvrsi(valuta, kursnaLista);	
	}

	@Override
	public void obrisiValutu(Valuta valuta) {
		SOObrisiValutu.izvrsi(valuta, kursnaLista);
	}

	@Override
	public double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos) {
		return SOIzvrsiTransakciju.izvrsi(valuta, prodaja, iznos);
	}

	@Override
	public LinkedList<Valuta> vratiKursnuListu() {
		return SOVratiKursnuListu.izvrsi(kursnaLista);
	}

	@Override
	public void ucitajIzFajla(String putanja) {
		SOUcitajIzFajla.izvrsi(putanja, kursnaLista);
	}

	@Override
	public void sacuvajUFajl(String putanja) {
		SOSacuvajUFajl.izvrsi(putanja, kursnaLista);
	}
}
