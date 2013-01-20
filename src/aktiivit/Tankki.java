package aktiivit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.ResourceLoader;

import perus.Pelitila;
import util.Satunnaisuus;


/**
 * Luokka joka määrittää pelaajan ohjaamat tankit.
 * @author Johannes
 *
 */
public class Tankki extends Image {

	private Piippu piippu;
	private int x;
	private int y;
	private float lahtonopeus;	
	private Tankintormaysmalli tormaysmalli;
	private boolean tuhottu;
	private int kunto;

	
	/**
	 * Pelissä olevat "oikeat" tankit luodaan tällä.
	 * @param gc	pelin GameContainer
	 * @param tankkitaulukko	tankkitaulukko, joka manageroi tankkeja
	 * @throws SlickException
	 */
	public Tankki(GameContainer gc,
			Tankkitaulukko tankkitaulukko) throws SlickException {
		super(new Image("res/tank.png"));

		//this.laskeYmaastoon(maasto);
		
		this.tuhottu = false;
		this.kunto = 100;  
		this.lahtonopeus = (float) 7;	

		this.piippu = new Piippu(this);	
		this.piippu.asetaSarana();

		this.asetaTormaysmalli(new Tankintormaysmalli(this));

	}
	
	
	/**
	 * Valikkokoristeena käytettävä toiminnaton malli tankista.
	 * @param gc
	 * @throws SlickException
	 */
	public Tankki(GameContainer gc) throws SlickException {
		super(new Image(ResourceLoader.getResource("res/static_tank.png").getPath()));

		//creditsin putoavalle tankille alkukoordinaatit.
		//päävalikkotankille nämä ylikirjoitetaan.
		this.x = Satunnaisuus.arvoIkkunastaX(gc);
		this.y = -10;
	}

	
	
	/**
	 * Asettaa uuden törmäysmallin
	 * @param tormaysmalli	uusi törmäysmalli
	 */
	public void asetaTormaysmalli(Tankintormaysmalli tormaysmalli) {
		this.tormaysmalli = tormaysmalli;
	}

	/**
	 * palauttaa tankin törmäysmallin
	 * @return this.tormaysmalli
	 */
	public Tankintormaysmalli annaTormaysmalli() {
		return this.tormaysmalli;
	}
	


	/**
	 * Laskee tankin maaston tasalle.
	 * @param maasto	voimassaoleva Maasto
	 * @return	uusi y-koordinaatti
	 */
	public int laskeYmaastoon(Shape maasto) {
		int tempY = 0;

		while(!maasto.contains(this.annaX(), tempY)) {
			tempY++;
		}
		return tempY;
	}
	/**
	 * Asettaa uuden y:n
	 * @param y	uusi y
	 */
	public void asetaY(int y) {
		this.y = y;
	}
	/**
	 * Antaa x:n
	 * @return x
	 */
	public int annaX() {
		return this.x;
	}
	/**
	 * Antaa y:n
	 * @return y
	 */
	public int annaY() {
		return this.y;
	}
	/**
	 * Muuttaa lähtönopeutta
	 * @param gc pelin GameContainer
	 * @param muutos muutos, voi olla negatiivinen
	 */
	public void muutaLahtonopeutta(GameContainer gc, float muutos) {
		this.lahtonopeus = this.lahtonopeus + muutos;
		//bonusvoima on voimassa, jos ikkunakoko > 800
		int bonusvoima;
		if(gc.getWidth() > 800) {
			bonusvoima = 2;
		}
		else {
			bonusvoima = 1;
		}
		//yläraja 10 tai 20
		if(annaLahtonopeus(false) > 10*bonusvoima) {
			this.lahtonopeus = 10*bonusvoima;
		}
		//alaraja 1
		else if (annaLahtonopeus(false) < 1) {
			this.lahtonopeus = 1;
		}
	}
	/**
	 * Antaa tankin kunnon
	 * @return tankin kunto
	 */
	public int annaKunto() {
		return this.kunto;
	}
	/**
	 * Laskee tankin kuntoa ja huolehtii tuhoamisesta.
	 * @param paljonko	paljonko vauriota
	 * @param pelitila	peliä pyörittävä pelitila
	 */
	public void vaurioita(int paljonko, Pelitila pelitila) {
		this.muutaKuntoa(-paljonko);
		if(annaKunto() <= 0) {
			this.tuhoa(pelitila);
		}
	}
	/**
	 * Muuttaa tankin kuntoa.
	 * @param muutos	muutoksen määrä, voi olla negatiivinen
	 */
	public void muutaKuntoa(int muutos) {
		asetaKunto(this.kunto + muutos);
	}
	/**
	 * Asettaa uuden kunto-arvon
	 * @param kunto	uusi kunto
	 */
	private void asetaKunto(int kunto) {
		this.kunto = kunto;

		//alaraja 0
		if(this.kunto < 0) {
			this.kunto = 0;
		}
	}

	/**
	 * Pudottaa tankkia kolmella pikselillä. Jos tippuu 
	 * ulos ruudulta, tiputtaa satunnaisesta kohdasta ylhäältä.
	 * Tarkoitettu Credits-tilan putoavalle tankille.
	 * @param gc pelin GameContainer
	 */
	public void putoa(GameContainer gc) {
		if(this.annaY() < gc.getHeight()+40) {
			this.asetaY(this.annaY()+3);
		}
		else {
			this.asetaX(Satunnaisuus.arvoIkkunastaX(gc));
			this.asetaY(-10);
		}
	}

	/**
	 * Asettaa x:n
	 * @param x	uusi x
	 */
	public void asetaX(int x) {
		this.x = x;
	}

	/**
	 * Tuhoaa tämän tankin.
	 * @param pelitila	Pelitila, joka pyörittää peliä
	 */
	public void tuhoa(Pelitila pelitila) {
		this.tuhottu  = true;
		pelitila.lisaaTuhottujenLaskuria();
	}

	/**
	 * Antaa tuhottu-booleanin arvon
	 * @return	tuhottu
	 */
	public boolean onkoTuhottu() {
		return this.tuhottu;
	}

	/**
	 * Antaa tämän tankin piipun
	 * @return	piippu
	 */
	public Piippu annaPiippu() {
		return this.piippu;
	}

	/**
	 * Antaa lähtönopeuden arvon
	 * @param pyorista	kertoo halutaanko tulos pyöristettynä vai ei
	 * @return	lähtönopeus
	 */
	public float annaLahtonopeus(boolean pyorista) {
		if (!pyorista) {
			return this.lahtonopeus;
		}
		else {
			return (float) (Math.floor(this.lahtonopeus*100)/100);
		}
	}
}
