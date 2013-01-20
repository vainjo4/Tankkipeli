package aktiivit;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import perus.Pelitila;
import util.Satunnaisuus;


/**
 * Luokka, joka hallinnoi tankkeja.
 * @author 290836
 */
public class Tankkitaulukko {

	Tankki[] taulukko;
	int[] sijoitettujenpaikat;
	private int minimivali;

	/**
	 * Luo uuden taulukon
	 * @param gc 			pelin GameContainer
	 * @param pelitila 		pelia pyorittava GameState
	 * @param tankkimaara	montako tankkia taulukkoon tulee 
	 * @param minimivali	minimietaisyys tankkien valilla
	 * @throws SlickException 
	 */
	public Tankkitaulukko(GameContainer gc, Pelitila pelitila, int tankkimaara, int minimivali) 
			throws SlickException {
		this.minimivali = minimivali;
		this.taulukko = new Tankki[tankkimaara];
		this.sijoitettujenpaikat = new int[tankkimaara];

		/**
		 * Tehdaan tankit.
		 * silmukka voisi teoriassa olla loputon, 
		 * mutta Asetustilan minimiValiTarkastus() huolehtii, etta laillisia paikkoja on. 
		 */
		int i = 0;
		while(i < tankkimaara) {			

			int mahdollinenX = Satunnaisuus.arvoIkkunastaX(gc);

			if(onkoTassaLaillinenPaikka(mahdollinenX)) {
				Tankki vaunu = new Tankki(gc, this);
				vaunu.asetaX(mahdollinenX);
				vaunu.asetaY(vaunu.laskeYmaastoon(pelitila.annaMaasto()));
				this.tallennaTankkiTaulukoihin(i, vaunu);
				i++;
			}
		}
	}
	/**
	 * Tallettaa tankin taulukoihin.
	 * @param indeksi	tankin paikka taulukossa
	 * @param tankki	sijoitettava tankki
	 */
	private void tallennaTankkiTaulukoihin(int indeksi, Tankki tankki) {
		this.taulukko[indeksi] = tankki;
		this.sijoitettujenpaikat[indeksi] = tankki.annaX();
	}


	/**
	 * Tarkastaa x:n laillisuuden.
	 * @param x	arvo, jota kysytaan
	 * @return	onko x-arvo liian lahella muita tankkeja
	 */
	private boolean onkoTassaLaillinenPaikka(int x) {

		int k = 0;
		while(k < this.sijoitettujenpaikat.length) {
			if(this.sijoitettujenpaikat[k] != 0
					&& x > this.sijoitettujenpaikat[k] - this.minimivali 
					&& x < this.sijoitettujenpaikat[k] + this.minimivali) {
				
				//System.out.println("laitaTankki: false");
				
				return false;
			}
			k++;
		}
		return true;
	}

	/**
	 * Antaa taulukosta tankin
	 * @param indeksi	tankin indeksi
	 * @return	indeksin tankki
	 */
	public Tankki annaTankki(int indeksi) {
		return this.taulukko[indeksi];
	}
}
