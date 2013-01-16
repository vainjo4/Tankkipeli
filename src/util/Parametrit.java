package util;
/**
 * 
 * @author Johannes
 *
 */
public class Parametrit {
	
	private int tankkimaara;
	private int ammustuho;
	private int ppmp;
	private int minimivali;
	
	/*
	private  boolean nayta_numerot;
	private boolean nayta_ellipsi;
	*/
	
	//ppmp == PikseliäPerMaastoPiste
	
	public Parametrit(int tankkimaara, int ammustuho, int minimivali, int ppmp) {
		this.tankkimaara = tankkimaara;
		this.ammustuho = ammustuho;
		this.minimivali = minimivali;
		this.ppmp = ppmp;
		
	}	
	
	public int annaMinimivali() {
		return this.minimivali;
	}
	
	public int annaTankkimaara() {
		return this.tankkimaara;
	}
	
	public int annaAmmustuho() {
		return this.ammustuho;
	}
	
	public int annaPikseliaPerMaastoPiste() {
		return this.ppmp;
	}
}
