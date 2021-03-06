package perus;

import maisema.*;
import aktiivit.*;
import util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameState-luokka, joka hoitaa kaiken mita itse pelissa (vs. valikoissa) tapahtuu.
 * @author 290836
 *
 */
public class Pelitila extends BasicGameState {

	private final float SAATOASKEL = (float) 0.1;

	private int id;
	private int tankkimaara;
	private int vuorossaindeksi;
	private int tuhotutTankit;
	private boolean ammusIlmassa;

	private Tankkitaulukko tankkitaulukko;
	private Ammus ammus;
	private Tuulinuoli tuulinuoli;
	private Tausta tausta;
	private Kivikko kivikko;
	private Parametrit parametrit;
	private Hiukkaset hiukkaset;

	private Shape maasto;
	private Circle ammuksenKuva;
	private Sound tykkiaani;
	private Sound rajahdysaani;
	private ParticleSystem hiukkasRajahdys;

	/**
	 * @param id	GameStaten id-numero
	 */
	public Pelitila(int id) {
		this.id = id;
	}

	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * Antaa maaston
	 * @return	voimassaoleva maasto
	 */
	public Shape annaMaasto() {
		return this.maasto;
	}

	/**
	 * Kertoo, onko ammus ilmassa
	 * @return	onko ammus ilmassa
	 */
	private boolean onkoAmmusIlmassa() {
		return this.ammusIlmassa;
	}

	/**
	 * Kutsutaan ammuksen osuessa.
	 * @param gc		pelin GameContainer
	 * @param vaunu		Tankki johon osui, jos osui. Voi olla null.
	 * @param mihinOsui	1 == osui tankkiin, 2 == meni ulos, 3 == osui maahan
	 */
	private void osuma(GameContainer gc, Tankki vaunu, int mihinOsui) {
		//mihinOsui:
		//1 == tankkiin
		//2 == ulos
		//3 == maahan

		if(mihinOsui == 1) {

			//vauriota tankille
			vaunu.vaurioita(this.ammus.annaTuhovoima(), this);

			//partikkelisysteemirajahdys
			this.rajahdys(
					this.ammus.annaX(),
					this.ammus.annaY());
		}
		else if(mihinOsui == 2){
			// ei tee mitaan erityista. 
			// ehto tassa vain metodin loogisuuden vuoksi.
		} 
		else if(mihinOsui == 3) {

			this.rajahdys(
					this.ammus.annaX(), 
					this.ammus.annaY());

			this.muutaMaastoa(gc);
			this.tiputaTankkeja();
		}
		//asiat jotka tehdaan joka osumassa:

		//soitetaan efekti
		this.rajahdysaani.play();

		//poistetaan ammus
		this.tuhoaAmmus(gc);

		//vaihdetaan vuoroa
		this.edistaVuoroa(gc);
	}

	/**
	 * Muuttaa maastoa ammuksen mukaan. Uusii maasto-olion.
	 * @param gc	pelin GameContainer
	 */
	private void muutaMaastoa(GameContainer gc) {

		Circle reika = new Circle (this.ammus.annaX(), 
				this.ammus.annaY(), 
				this.ammus.annaTuhovoima()/10);

		this.kivikko.teeKivikkoonKolo(reika, gc);
		this.kivikko.tiputaKivia(gc);
		this.maasto = new Maasto(gc, this.kivikko);
	}

	/**
	 * Laskee kaikki tankit maaston pinnan tasalle.
	 */
	private void tiputaTankkeja(){
		int k = 0;
		while (k < this.tankkimaara) {
			Tankki vaunu = this.tankkitaulukko.annaTankki(k);
			vaunu.asetaY(vaunu.laskeYmaastoon(this.maasto));
			//uusitaan tormaysellipsit
			vaunu.asetaTormaysmalli(new Tankintormaysmalli(vaunu));
			k++;
		}
	}

	/**
	 * Poistaa ammuksen tietoisuudesta.
	 */
	private void tuhoaAmmus(GameContainer gc) {

		ammusOlemassa(false);
		this.ammus = null;
	}

	/**
	 * Asettaa uudeksi tilaksi parametrin
	 * @param olemassako	tieto, onko ammus olemassa
	 */
	private void ammusOlemassa(boolean olemassako) {
		this.ammusIlmassa = olemassako;
	}
	/**
	 * Siirtaa ja resetoi hiukkasefektin.
	 * @param x	uusi x
	 * @param y	uusi y
	 */
	private void rajahdys(float x, float y) {

		this.hiukkasRajahdys.setPosition(x, y);
		this.hiukkasRajahdys.setVisible(true);
		this.hiukkasRajahdys.reset();
	}

	/**
	 * Antaa vuoron seuraavalle.
	 * @param gc pelin GameContainer
	 */
	private void edistaVuoroa(GameContainer gc) {

		//vaihdetaan vuorossaolija
		this.vuorossaindeksi++;

		//jos meni yli niin jatketaan nollasta
		if(this.vuorossaindeksi >= this.tankkimaara) {
			this.vuorossaindeksi = 0;
		}

		//jos vuorossaolija onkin jo tuhottu, siirretaan seuraavalle
		if(this.tankkitaulukko.annaTankki(vuorossaindeksi).onkoTuhottu()) {
			edistaVuoroa(gc);
		}

		//tehdaan uusi tuulinuoli
		this.tuulinuoli = new Tuulinuoli(gc);
	}

	/**
	 * Lisaa tuhottujen tankkien laskuria.
	 */
	public void lisaaTuhottujenLaskuria() {
		this.tuhotutTankit++;
	}

	/**
	 * Antaa voittamis-Stringin piirrettavaksi
	 * @param voittajanNumero	monesko tankki voittaja on (eli indeksi+1).
	 * @return	kayttokelpoinen String
	 */
	private String annaVoittoTeksti(int voittajanNumero) {
		return "WINNER is TANK NUMBER "+ voittajanNumero +" !!!"+"\n"
				+"\t"+"press ESC to quit";
	}

	/**
	 * Antaa ampumatiedoista Stringin piirrettavaksi
	 * @param tankkivuorossa	Tankki joka vuorossa 
	 * @param moneskoTankki		tankin numero
	 * @return tankin numero, piipun kulma ja lahtonopeus
	 */
	private String annaAmpumaTiedot(Tankki tankkivuorossa, int moneskoTankki) {
		return "Tank "+ moneskoTankki +"\n" +"Shot power "+	tankkivuorossa
				.annaLahtonopeus(true)+"\n" +"Barrel angle "+ tankkivuorossa
				.annaPiippu().annaPyoristettyKulma();
	}

	/**
	 * Kuormitettu metodi, jotta saadaan aikaan pelitilan initti 
	 * vasta kun parametrit on tiedossa
	 * @param gc	pelin GameContainer
	 * @param peli	peli itse
	 * @param parametrit	Parametrit-olio, jolla uuden pelitilan parametrit
	 * @throws SlickException
	 */
	public void init(GameContainer gc, StateBasedGame peli, Parametrit parametrit) 
			throws SlickException {

		this.parametrit = parametrit;
		this.init(gc, peli);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {

		//estetaan liian aikainen initti
		if(this.parametrit == null) {
			return;
		}

		//alussa ei ole tuhottuja tankkeja
		this.tuhotutTankit = 0;

		//alussa ammusta ei ole ilmassa
		this.ammusOlemassa(false);

		//tehdaan tausta
		this.tausta = new Tausta(gc);

		//tehdaan maasto, haetaan ppmp-parametri Parametri-oliolta
		this.maasto = new Maasto(gc, this.parametrit.annaPikseliaPerMaastoPiste());

		//maaston paalle tehdaan kivikko
		this.kivikko = new Kivikko(gc, this.maasto);

		//kivikon mukaan lopullinen maasto
		this.maasto = new Maasto(gc, this.kivikko);

		//haetaan tankkiparametri Parametri-oliolta
		this.tankkimaara = this.parametrit.annaTankkimaara();

		//tehdaan tankkitaulukko
		this.tankkitaulukko = new Tankkitaulukko(gc, this,
				tankkimaara, this.parametrit.annaMinimivali());

		//tehdaan ammuksen "kuva"
		this.ammuksenKuva = new Circle(0,0,2);

		//tehdaan tuulinuoli
		this.tuulinuoli = new Tuulinuoli(gc);

		//vuorot alkavat 1. tankista
		this.vuorossaindeksi = 0;

		//ladataan efektit jos ei jo ole
		if(this.rajahdysaani == null) {
			this.rajahdysaani = Musiikki.annaRajahdysaani();
		}
		if(this.tykkiaani == null) {
			this.tykkiaani = Musiikki.annaTykkiaani();
		}
		//luodaan partikkeliefektiluokka, jos ei ole
		if(this.hiukkaset == null) {
			this.hiukkaset = new Hiukkaset();
		}
		//haetaan rajahdyksen ParticleSystem
		if(this.hiukkasRajahdys == null) {
			this.hiukkasRajahdys = this.hiukkaset.annaRajahdys();
			hiukkasRajahdys.setVisible(false);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {

		Tankki tankkivuorossa = this.tankkitaulukko
				.annaTankki(this.vuorossaindeksi);

		int monesko = this.vuorossaindeksi + 1;

		//taytetaan tausta gradientfillilla
		g.fill(this.tausta, this.tausta.annaTaustanGradient());

		//taytetaan maasto gradientfillilla
		if(this.maasto instanceof Maasto) {
			g.fill(this.maasto, ((Maasto) this.maasto).annaMaastonGradient());
		}
		//piirretaan tuulinuoli ja teksti, jos on olemassa
		if(this.tuulinuoli != null) {
			g.fill(this.tuulinuoli);

			g.drawString("Wind "+this.tuulinuoli.annaSuuruus(), 
					this.tuulinuoli.annaX(),
					this.tuulinuoli.annaY()+30);
		}

		//vuorossaolija- ja lahtonopeus-Stringin piirto
		g.drawString(annaAmpumaTiedot(tankkivuorossa, monesko), 20, 10);

		//piiretaan hitpoint-taulukon otsikko
		g.drawString("Hitpoints:", gc.getWidth()-180, 10);

		//jos ammus on ilmassa, paivitetaan ja piirretaan ammuksenKuva
		if(onkoAmmusIlmassa()) {

			this.ammuksenKuva.setCenterX(this.ammus.annaX());
			this.ammuksenKuva.setCenterY(this.ammus.annaY());
			g.fill(this.ammuksenKuva);
		}



		//piirretaan vaunut ja piiput, jos ei tuhottuna
		int i = 0;
		Tankki vaunu = null;
		Piippu piippu = null;
		while (i < tankkimaara) {
			vaunu = this.tankkitaulukko.annaTankki(i);
			piippu = vaunu.annaPiippu();

			//piiretaan hitpoint-taulukon rivit
			monesko = i+1;
			g.drawString("Tank "+monesko+" "+
					vaunu.annaKunto() + "%", gc.getWidth()-150, 30+i*20);

			if(!vaunu.onkoTuhottu()) {

				//vaunun numero piirretaan sen viereen
				g.drawString(monesko+"", vaunu.annaX()-3, vaunu.annaY());

				//vaunut ja piiput piirretaan omille paikoilleen
				g.drawImage(vaunu, 
						vaunu.annaX()-(vaunu.getWidth()/2), 
						vaunu.annaY()-vaunu.getHeight());
				g.drawImage(piippu,
						piippu.annaX(),
						piippu.annaY());
			}
			i++;
		}

		//jos vain 1 tankki jaljella, piirretaan voitonhuudahdus
		if(this.tuhotutTankit == this.tankkimaara-1) {
			g.drawString(this.annaVoittoTeksti(this.vuorossaindeksi+1),
					gc.getWidth()/2-150, gc.getHeight()/2-100);
		}

		//rajahdyksen piirto
		this.hiukkasRajahdys.render();

		//	vuorossa olijan tormaysellipsin piirto
		//	g.draw(this.tankkitaulukko.annaTankki(vuorossaindeksi).annaTormaysmalli());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame peli, int delta)
			throws SlickException {

		Input input = gc.getInput();

		//rajahdyksen paivitys
		this.hiukkasRajahdys.update(delta);

		//jos ammus on ilmassa
		if(onkoAmmusIlmassa()) {

			/*
			 * Tankkiinosumissetti.
			 *
			 * Logiikka:
			 * kaydaan taulukko lapi
			 * jos tankkitaulun tankin ellipsi sisaltaa ammuksen koordit
			 * ja se ei ole ammuksen ampuja
			 * ja se on ehja
			 * ->
			 * vauriota tankille, tuhotaan ammus.
			 * 
			 * Return.
			 */
			int k = 0;
			while (k < this.tankkimaara) {
				Tankki vaunu = this.tankkitaulukko.annaTankki(k);

				if (vaunu.annaTormaysmalli().contains(
						this.ammus.annaX(), this.ammus.annaY())		
						&& !this.ammus.annaAmpuja().equals(vaunu)
						&& !vaunu.onkoTuhottu()) {

					this.osuma(gc, vaunu, 1);

					//System.out.println("OSUMA TANKKIIN");
					return;
				}
				k++;
			}

			//Out of bounds-setti eli ammus ulos ruudulta-tapaus. Return.
			if(this.ammus.annaY() > gc.getHeight() 
					|| this.ammus.annaY() < -2000
					|| this.ammus.annaX() < -100 
					|| this.ammus.annaX() > gc.getWidth() +100) {

				this.osuma(gc, null, 2);

				//System.out.println("OUT OF BOUNDS");
				return;
			}
			//Osuma maastoon. Verrataan Maasto-olioon.
			if(this.maasto.contains(this.ammus.annaX(), this.ammus.annaY())) {
				this.osuma(gc, null, 3);
				return;
			}

			//Muuten ammus lentaa.
			this.ammus.lenna(
					this.ammus.annaX(), 
					this.ammus.annaY(), 
					this.ammus.annaNopeusvektori());
		}
		//onkoammusilmassa-lohkon loppu

		// escilla paasee pause-valikkoon ammuksesta riippumatta
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			GameState pause = peli.getState(Peli.PAUSETILA);
			if(pause instanceof Pausetila) {
				((Pausetila) peli.getState(Peli.PAUSETILA)).asetaMaasto(this.maasto);
				peli.enterState(Peli.PAUSETILA);
				return;
			}
		}	

		/*
		 * jos ammusta ei ole ilmassa:
		 * nuolinappaimilla saadetaan kulmaa ja lahtonopeutta
		 * enterilla ammutaan
		 * shiftilla saadaan rotaatioon ja nopeuden saatoon nopeutta
		 */
		if(!onkoAmmusIlmassa()) {

			int shift = 1;
			Tankki tankkivuorossa = this.tankkitaulukko
					.annaTankki(this.vuorossaindeksi);

			if(input.isKeyDown(Input.KEY_RSHIFT) 
					|| input.isKeyDown(Input.KEY_LSHIFT)) {
				//shiftin pohjassa pitaminen 10-kertaistaa 
				//kaikkien saatojen saatoaskelen
				shift = 10;
			}

			if(input.isKeyDown(Input.KEY_RIGHT)) {
				tankkivuorossa.annaPiippu().rotate(SAATOASKEL*shift*delta);	
			}

			if(input.isKeyDown(Input.KEY_LEFT)) {
				//360 koska muuten tulee negatiivisia kulmalukuja
				tankkivuorossa.annaPiippu().rotate(360-SAATOASKEL*shift*delta);		
			}

			if(input.isKeyDown(Input.KEY_UP)) {	
				tankkivuorossa.muutaLahtonopeutta(gc, (SAATOASKEL/200)*shift*delta);


			}
			if(input.isKeyDown(Input.KEY_DOWN)) {
				tankkivuorossa.muutaLahtonopeutta(gc, (-SAATOASKEL/200)*shift*delta);

			}

			//laukaus.
			//vuoronsiirto vasta osuessa.
			if(input.isKeyDown(Input.KEY_ENTER) 
					&& input.isKeyPressed(Input.KEY_ENTER) && !onkoAmmusIlmassa()) {

				this.ammusOlemassa(true);
				this.ammus = new Ammus(this.tuulinuoli, 
						tankkivuorossa,
						this.parametrit.annaAmmustuho());

				this.tykkiaani.play();

			}
			//!onkoammusilmassa-lohkon loppu
		}
	}
}
