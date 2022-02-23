package igra;

import java.awt.*;

public class Krtica extends Zivotinja {
	
	public Krtica(Rupa rupa) {
		super(rupa);
	}

	private Color boja = Color.DARK_GRAY;
	
	@Override
	public void iscrtaj() {
		Graphics g =  rupa.getGraphics();
		g.setColor(boja);
		int x = rupa.getWidth()/2;
		int y = rupa.getHeight()/2;
		double brKoraka = (double) rupa.getBrKoraka();
		double trKorak = (double) rupa.getTrKorak();
		double procenat = trKorak/brKoraka;
		g.fillOval((int)(x*(1-procenat)), (int)(y*(1-procenat)), (int)(x*2*procenat), (int)(y*2*procenat));
	}

	@Override
	public void udarena() {
		rupa.zaustavi();
	}

	@Override
	public void pobegla() {
		rupa.smanjiPovrce();
	}

}
