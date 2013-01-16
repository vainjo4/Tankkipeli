package aktiivit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import perus.Pelitila;
import util.Satunnaisuus;


/**
 * 
 * @author Johannes
 *
 */
public class Tankki extends Image {

	private Piippu piippu;
	private int x;
	private int y;
	private float lahtonopeus;	
	private Tankintormaysmalli tormaysmalli;
	private boolean tuhottu = false;
	private int kunto;
	private GameContainer gc;

	public Tankki(GameContainer gc) throws SlickException {
		super(new Image("res/static_tank.png"));

		this.x = arvoX(gc);
		this.y = 0;
	}

	public Tankki(GameContainer gc, Pelitila pelitila, Tankkitaulukko tankkitaulukko) throws SlickException {
		super(new Image("res/tankki.png"));

		//this.gc = gc;

		sijoitaTankki(gc, pelitila, tankkitaulukko);

		this.piippu = new Piippu(this);	
		this.piippu.asetaSarana();
		asetaTormaysmalli(new Tankintormaysmalli(this));
		this.kunto = 100;  
		this.lahtonopeus = (float) 7;	

	}

	public void asetaTormaysmalli(Tankintormaysmalli tormaysmalli) {
		this.tormaysmalli = tormaysmalli;
	}

	public void sijoitaTankki(GameContainer gc, Pelitila pelitila, Tankkitaulukko tankkitaulukko) {

		Shape maasto = pelitila.annaMaasto();
		int tempX = arvoX(gc);

		if(!tankkitaulukko.laillinenPaikka(tempX)) {
			sijoitaTankki(gc, pelitila, tankkitaulukko);
		}

		this.asetaX(tempX);
		this.asetaY(this.laskeY(maasto));
	}

	public Tankintormaysmalli annaTormaysmalli() {
		return this.tormaysmalli;
	}

	public int arvoX(GameContainer gc) {
		return Satunnaisuus.annaInt(gc.getWidth());
	}

	public int laskeY(Shape maasto) {
		int tempY = 0;

		while(!maasto.contains(this.annaX(), tempY)) {
			tempY++;
		}
		return tempY;
	}

	public void asetaY(int y) {
		this.y = y;
	}

	public int annaX() {
		return this.x;
	}
	public int annaY() {
		return this.y;
	}

	public void muutaLahtonopeutta(GameContainer gc, float muutos) {
		this.lahtonopeus = this.lahtonopeus + muutos;

		int bonusvoima;
		if(gc.getWidth() > 800) {
			bonusvoima = 2;
		}
		else {
			bonusvoima = 1;
		}

		if(annaLahtonopeus(false) > 10*bonusvoima) {
			this.lahtonopeus = 10*bonusvoima;
		}
		else if (annaLahtonopeus(false) < 1) {
			this.lahtonopeus = 1;
		}
	}

	public void vaurioita(int paljonko, Pelitila pelitila) {
		this.muutaKuntoa(-paljonko);
		if(annaKunto() <= 0) {
			this.tuhoa(pelitila);
		}
	}

	public int annaKunto() {
		return this.kunto;
	}

	public void muutaKuntoa(int muutos) {
		asetaKunto(this.kunto + muutos);
	}

	private void asetaKunto(int kunto) {
		this.kunto = kunto;

		if(this.kunto < 0) {
			this.kunto = 0;
		}
	}

	/**
	 * tarkoitettu Credittilan jutulle
	 * @param gc
	 */
	public void putoa(GameContainer gc) {
		if(this.annaY() < gc.getHeight()+40) {
			this.asetaY(this.annaY()+3);
		}
		else {
			this.asetaX(this.arvoX(gc));
			this.asetaY(0);
		}
	}
	public void asetaX(int x) {
		this.x = x;

	}

	public void tuhoa(Pelitila pelitila) {
		this.tuhottu  = true;
		pelitila.lisaaTuhottujenLaskuria();
	}

	public boolean onkoTuhottu() {
		return this.tuhottu;
	}

	public Piippu annaPiippu() {
		return this.piippu;
	}

	public float annaLahtonopeus(boolean pyorista) {
		if (!pyorista) {
			return this.lahtonopeus;
		}
		else {
			return (float) (Math.floor(this.lahtonopeus*100)/100);
		}
	}


	public static void main(String[] args) {

	}

}
