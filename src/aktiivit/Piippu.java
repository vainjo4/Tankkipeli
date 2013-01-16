package aktiivit;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Piippu extends Image {

	
	private Tankki tankki;
	
	
	public Piippu(Tankki tankki) throws SlickException {
	super("res/piippu.png");
	//this.setCenterOfRotation(this.width/2, BOTTOM_LEFT+this.width/2);
	this.tankki = tankki;
	
	System.out.println("piippu luotu & sijoitettu");
	
	
	}
	/**
	 * double koska vain printissä
	 * @return
	 */
	public double annaPyoristettyKulma() {
		return (Math.floor(Math.abs(this.getRotation()*100)))/100;
	}
	
	public void sijoitaPiippu(Tankki tankki) {
	
		this.tankki = tankki;
	}

	/**
	 * En tiedä miksi, mutta ilman getCenterOfRotation-kutsuja 
	 * tämä ei toimi. Slickin bugi.
	 */
	public void asetaSarana() {
		
		this.getCenterOfRotationX();
		this.getCenterOfRotationY();
		setCenterOfRotation(10, 20);
	}
	
	public int annaX() {
		return tankki.annaX()-10;
	}
	public int annaY() {
		return tankki.annaY()-30;
	}

	public static void main(String[] args) {
	
	}

}
