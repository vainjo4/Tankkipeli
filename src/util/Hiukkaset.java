package util;

import java.io.File;
import java.io.IOException;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Luokka, joka huolehtii ParticleEmittereista
 * @author 290836
 * 
 */
public class Hiukkaset {

	private ParticleSystem rajahdys;

	/**
	 * Luo olion ja lataa xml-tiedostosta efektin tiedot
	 */
	public Hiukkaset() {
		try {
			this.rajahdys = ParticleIO.loadConfiguredSystem(
					new File("fx/explosion.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Antaa efektin
	 * @return	rajahdysefekti
	 */
	public ParticleSystem annaRajahdys() {
		return this.rajahdys;
	}
}