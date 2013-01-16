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
	
	/*
	public ConfigurableEmitter annaEmitteri() {
		try {
			return ParticleIO.loadEmitter(new File("smoke.xml"));
		} catch (IOException e) {
			return null;

		}
	}
	 */
	public ParticleSystem annaRajahdys() {
		return this.rajahdys;

	}
}



