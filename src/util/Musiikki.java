package util;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Luokka, joka lataa efektit ja musiikin. 
 * Luo uuden saikeen, koska musiikin lataaminen on hidasta (~4 sekuntia).
 * Musiikin lopettamiseen tai saatamiseen ei ole mitaan in-game-tapaa.
 * @author 290836
 */
public class Musiikki implements Runnable {

	private static Music musiikki;
	private static Sound rajahdysaani;
	private static Sound tykkiaani;

	/**
	 * Antaa ampumisaanen
	 * @return	aaniefekti tykinlaukaukselle
	 */
	public static Sound annaTykkiaani() {
		return tykkiaani;
	}
	
	/**
	 * Antaa osumisaanen
	 * @return	aaniefekti rajahdykselle
	 */
	public static Sound annaRajahdysaani() {
		return rajahdysaani;
	}
	/**
	 * Hakee aanet ja musiikin
	 */
	@Override
	public void run() {
		
		
		try {
			Musiikki.rajahdysaani = new Sound("res/shell1.ogg");
			Musiikki.tykkiaani = new Sound("res/gun.ogg");
			Musiikki.musiikki = new Music("res/sendme.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		Musiikki.musiikki.setVolume(1/2);
		Musiikki.musiikki.loop();
		
	}
}