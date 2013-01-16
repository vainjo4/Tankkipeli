package util;
import java.util.Random;

/**
 * Luokka, jossa pelin tarvitsemaa satunnaisuutta
 * @author Johannes
 *
 */
public class Satunnaisuus {

	private static Random kone = new Random();
	
	/**
	 * Toimii kuten Random.nextInt-metodi.
	 * @param mihinAsti	luku, jota pienempi halutaan.
	 * @return	satunnaiskokonaisluku, joka < mihinAsti.
	 */
	public static int annaInt(int mihinAsti) {
		return kone.nextInt(mihinAsti);
	}
	
	/**
	 * Muodostaa taulukon jonka arvot on satunnaisesta kahdesti tasoitettuja. 
	 * @param lukumaara	taulukon haluttu pituus
	 * @param korkeus
	 * @return
	 */
	public static int[] satunnaisPolku(int lukumaara, int korkeus) {

		int[] taulukko = new int[lukumaara];
		int i = 0;

		// tallennetaan joka paikkaan satunnaisluku
		while (i < lukumaara) {
			taulukko[i] = korkeus/2+kone.nextInt(korkeus/2);
			i++;		
		}	
		i = 1;

		//tasoituskierros 1
		while (i < lukumaara-1) {
			taulukko[i] = taulukko[i]/2 + taulukko[i-1]/4 + taulukko[i+1]/4;
			i++;
		}		
		i = 2;
		
		//tasoituskierros 2
		while(i < lukumaara-2) {
			taulukko[i] = (taulukko[i]/3 + taulukko[i-1]/5 + taulukko[i+1]/5 + taulukko[i+2]/8 + taulukko[i-2]/8);
			i++;
		}

		return taulukko;
	}
}