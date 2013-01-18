package aktiivit;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Tankin liikkuva osa, joka osoittaa sinne minne tankki tähtää.
 * @author Johannes
 */
public class Piippu extends Image {

	private Tankki tankki;
	
	/**
	 * Luo piipun.
	 * @param tankki 	Tankki, jolle piippu kuuluu
	 * @throws SlickException
	 */
	public Piippu(Tankki tankki) throws SlickException {
	super(new Image (ResourceLoader.getResource("res/barrel.png").getPath()));

	this.tankki = tankki;
	}
	
	/**
	 * Antaa piipun kiertymän vain muutaman numeron tarkkuudella  
	 * @return pyöristetty kiertymä
	 */
	public double annaPyoristettyKulma() {
		return (Math.floor(Math.abs(this.getRotation()*100)))/100;
	}
	
	/**
	 * Asettaa piipun kiertoakselin. En tiedä miksi, 
	 * mutta ilman getCenterOfRotation-kutsuja tämä ei 
	 * toimi. 
	 * Todennäköisesti Slickin bugi.
	 */
	public void asetaSarana() {
		
		this.getCenterOfRotationX();
		this.getCenterOfRotationY();
		setCenterOfRotation(10, 20);
	}
	/**
	 * Antaa piipun x:n.
	 * @return piipun x
	 */
	public int annaX() {
		return tankki.annaX()-10;
	}
	/**
	 * Antaa piipun y:n
	 * @return	piipun y
	 */
	public int annaY() {
		return tankki.annaY()-30;
	}
}
