package util;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Musiikki implements Runnable {

	private static Music musiikki;
	private static Sound rajahdysaani;
	private static Sound tykkiaani;

	public static Sound annaTykkiaani() {
		return tykkiaani;
	}
	
	public static Sound annaRajahdysaani() {
		return rajahdysaani;
	}
	
	@Override
	public void run() {

		//System.out.println("a "+System.currentTimeMillis());
		
		try {
			Musiikki.rajahdysaani = new Sound("res/shell1.ogg");
			Musiikki.tykkiaani = new Sound("res/gun.ogg");
			Musiikki.musiikki = new Music("res/sendme.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		//System.out.println("b "+System.currentTimeMillis());
		
		Musiikki.musiikki.setVolume(1/2);
		Musiikki.musiikki.loop();
		
		//System.out.println("c "+System.currentTimeMillis());
	}
}
