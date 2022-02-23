package igra;

public abstract class Zivotinja {
	protected Rupa rupa;

	public Zivotinja(Rupa rupa) {
		super();
		this.rupa = rupa;
	}
	
	public abstract void iscrtaj();
	
	public abstract void udarena();
	
	public abstract void pobegla();
	
}
