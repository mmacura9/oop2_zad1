package igra;

import java.awt.*;
import java.awt.event.*;

public class Rupa extends Canvas implements Runnable {
	
	private Basta basta;
	private Zivotinja zivotinja;
	private Thread nit;
	private Color boja;
	private int brKoraka = 10;
	private int trKorak = 0;
	
	public Rupa(Basta basta) {
		super();
		this.basta = basta;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				zgazi();
			}
		});
		boja = new Color(102,51,0);
		setBackground(boja);
	}

	public Zivotinja getZivotinja() {
		return zivotinja;
	}

	public void setZivotinja(Zivotinja zivotinja) {
		this.zivotinja = zivotinja;
	}
	
	public int getBrKoraka() {
		return brKoraka;
	}

	public synchronized void setBrKoraka(int brKoraka) {
		this.brKoraka = brKoraka;
	}
	
	public int getTrKorak() {
		return trKorak;
	}
	
	public synchronized void zgazi() {
		if(zivotinja != null)
			zivotinja.udarena();
	}
	
	@Override
	public void paint(Graphics g) {
		if(zivotinja!=null)
			zivotinja.iscrtaj();
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				if(trKorak == brKoraka) {
					Thread.sleep(2000);
					zivotinja.pobegla();
					zaustavi();
				}
				else {
					Thread.sleep(100);
					trKorak++;
					repaint();
				}
			}
		}
		catch(InterruptedException e) { }
	}
	private boolean pokrenuta = false;
	
	public void stvori() {
		nit = new Thread(this);
	}
	
	public void pokreni() {
		if(pokrenuta == false) {
			pokrenuta = true;
			nit= new Thread(this);
			nit.start();
		}
	}

	public synchronized void zaustavi() {
		trKorak = 0;
		zivotinja = null;
		if(pokrenuta == true) {
			basta.obavesti();
			pokrenuta = false;
			nit.interrupt();
		}
		repaint();
	}
	
	public boolean isPokrenuta() {
		return pokrenuta;
	}
	
	public synchronized void smanjiPovrce() {
		basta.smanjiPovrce();
	}
	
}
