package util;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Luokka, joka lataa efektit ja musiikin. 
 * Luo uuden säikeen, koska musiikin lataaminen on hidasta (~4 sekuntia).
 * Musiikin lopettamiseen tai säätämiseen ei ole mitään in-game-tapaa.
 * @author Johannes
 */
public class Musiikki implements Runnable {

	private static Music musiikki;
	private static Sound rajahdysaani;
	private static Sound tykkiaani;

	/**
	 * Antaa ampumisäänen
	 * @return	ääniefekti
	 */
	public static Sound annaTykkiaani() {
		return tykkiaani;
	}
	
	/**
	 * Antaa osumisäänen
	 * @return
	 */
	public static Sound annaRajahdysaani() {
		return rajahdysaani;
	}
	
	@Override
	public void run() {
		
		//hakee äänet ja musiikin
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