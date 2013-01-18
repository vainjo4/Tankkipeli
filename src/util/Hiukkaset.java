package util;

import java.io.File;
import java.io.IOException;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

/**
 * Luokka, joka huolehtii ParticleEmittereistä
 * @author Johannes
 * 
 */
public class Hiukkaset {

	private ParticleSystem rajahdys;

	public Hiukkaset() {
		try {
			this.rajahdys = ParticleIO.loadConfiguredSystem(new File("fx/explosion.xml"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public ParticleSystem annaRajahdys() {
		return this.rajahdys;

	}
}



