package igra;

import java.awt.*;
import java.awt.event.*;

public class Igra extends Frame {

	public static Igra igra;

	private Basta basta;
	private Button dugme = new Button("Kreni");
	private Panel panel = new Panel();
	private Panel panel1 = new Panel();
	private Panel panel2 = new Panel();
	private Label povrce = new Label("", Label.CENTER);
	private Checkbox[] tezina = new Checkbox[3];

	private Igra() {
		super("Igra");

		setSize(500, 500);

		basta = new Basta(4, 4);

		add(basta, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				basta.zaustavi();
			}
		});

		dodajKomponente();
		add(panel, BorderLayout.EAST);
		setVisible(true);
	}

	private void dodajKomponente() {
		panel.setLayout(new GridLayout(2, 1));
		panel1.setLayout(new GridLayout(6, 1));
		panel2.setLayout(new BorderLayout());
		Label tezinaL = new Label("Tezina: ", Label.CENTER);
		tezinaL.setFont(new Font(null, Font.BOLD, 16));
		panel1.add(tezinaL);

		CheckboxGroup grupa = new CheckboxGroup();

		tezina[0] = new Checkbox("Lako", grupa, true);
		tezina[1] = new Checkbox("Srednje", grupa, false);
		tezina[2] = new Checkbox("Tesko", grupa, false);

		panel1.add(tezina[0]);
		panel1.add(tezina[1]);
		panel1.add(tezina[2]);

		dodajListener();

		basta.dodajRef(povrce, dugme, tezina);
		panel1.add(dugme);
		povrce.setFont(new Font(null, Font.BOLD, 16));
		panel2.add(povrce, BorderLayout.CENTER);
		panel.add(panel1);
		panel.add(panel2);
	}

	private void dodajListener() {
		dugme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				promeniDugme();
			}

		});

		tezina[0].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				basta.setCekanje(1000);
				basta.setBrKoraka(10);
			}

		});

		tezina[1].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				basta.setCekanje(750);
				basta.setBrKoraka(8);
			}

		});

		tezina[2].addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				basta.setCekanje(500);
				basta.setBrKoraka(6);
			}

		});
	}

	private void promeniDugme() {
		if (dugme.getLabel().equals("Kreni")) {
			basta.pokreni();
			dugme.setLabel("Stani");
			tezina[0].setEnabled(false);
			tezina[1].setEnabled(false);
			tezina[2].setEnabled(false);
		} else {
			basta.zaustavi();
			dugme.setLabel("Kreni");
			tezina[0].setEnabled(true);
			tezina[1].setEnabled(true);
			tezina[2].setEnabled(true);
		}
	}

	public static Igra getInstance() {
		if (igra == null)
			igra = new Igra();
		return igra;
	}

	public static void main(String[] args) {
		getInstance();
	}
}
