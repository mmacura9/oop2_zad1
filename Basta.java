package igra;

import java.awt.*;

public class Basta extends Panel implements Runnable {
	private Color boja = Color.GREEN;
	private Rupa[][] rupe;
	private int povrce = 100;
	private int cekanje = 1000;
	private int brKoraka;
	private int brVrsta, brKolona;
	private Thread nit;
	private int brSlobodnih;
	private Label labela;
	private int pocetnoCekanje = 1000;
	private Button dugme;
	private Checkbox tezina[];

	public Basta(int brVrsta, int brKolona) {
		this.brVrsta = brVrsta;
		this.brKolona = brKolona;
		setLayout(new GridLayout(brVrsta, brKolona, 20, 20));
		setBackground(boja);
		rupe = new Rupa[brVrsta][brKolona];
		for (int i = 0; i < brVrsta; i++)
			for (int j = 0; j < brKolona; j++) {
				rupe[i][j] = new Rupa(this);
				add(rupe[i][j]);
			}
		brSlobodnih = brVrsta * brKolona;
	}

	public void setCekanje(int cekanje) {
		pocetnoCekanje = cekanje;
		this.cekanje = cekanje;
	}

	public int getBrKoraka() {
		return brKoraka;
	}

	public void setBrKoraka(int brKoraka) {
		this.brKoraka = brKoraka;
		for (int i = 0; i < brVrsta; i++)
			for (int j = 0; j < brKolona; j++)
				rupe[i][j].setBrKoraka(brKoraka);
	}

	public void dodajRef(Label l, Button d, Checkbox[] t) {
		labela = l;
		dugme = d;
		tezina = t;
		postaviLabelu();
	}

	private void postaviLabelu() {
		labela.setText("Povrce: " + povrce);
	}

	public synchronized void smanjiPovrce() {
		povrce--;
	}

	private synchronized Rupa postaviZivotinju() {
		int x = (int) (brSlobodnih * Math.random()) + 1;
		if (brSlobodnih == 0)
			return null;
		int tr = 0;
		cekanje = (int) (cekanje * 0.99);
		for (int i = 0; i < brVrsta; i++) {
			for (int j = 0; j < brKolona; j++)
				if (rupe[i][j].isPokrenuta() == false) {
					tr++;
					if (tr == x) {
						return rupe[i][j];
					}
				}
		}
		return null;
	}

	public synchronized void obavesti() {
		brSlobodnih++;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(cekanje);
				Rupa x = postaviZivotinju();
				if (x != null) {
					brSlobodnih--;
					x.setZivotinja(new Krtica(x));
					x.pokreni();
				}
				postaviLabelu();
				if (povrce == 0)
					zaustavi();
			}
		} catch (InterruptedException e) {

		}
	}

	private boolean pokrenuta = false;

	public void pokreni() {
		if (pokrenuta == false) {
			nit = new Thread(this);
			pokrenuta = true;
			nit.start();
		}
	}

	public synchronized void zaustavi() {
		if (pokrenuta == true) {
			cekanje = pocetnoCekanje;
			povrce = 100;
			pokrenuta = false;
			for (int i = 0; i < brVrsta; i++)
				for (int j = 0; j < brKolona; j++)
					rupe[i][j].zaustavi();
			nit.interrupt();
			dugme.setLabel("Kreni");
			tezina[0].setEnabled(true);
			tezina[1].setEnabled(true);
			tezina[2].setEnabled(true);
		}
	}

}
