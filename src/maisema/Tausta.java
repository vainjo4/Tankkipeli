package maisema;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

/**
 * Neliön alaluokka. Muodostaa peli-ikkunan kokoisen neliön. 
 * Taustan ja GradientFillin avulla peli piirtää taivaan.
 * @author Johannes
 *
 */
@SuppressWarnings("serial")
public class Tausta extends Rectangle {

	private GradientFill taustaGradient;

	/**
	 * Luo uuden taustan
	 * @param gc	pelin GameContainer
	 */
	public Tausta(GameContainer gc) {
		super(0, 0, gc.getWidth(), gc.getHeight());
		
		this.taustaGradient = new GradientFill(
				(gc.getWidth()/2), 0, new Color(100, 100, 220),
				(gc.getWidth()/2), gc.getHeight(), new Color(180,180,200));
	}
	/**
	 * Antaa taivaannäköisen gradientfillin
	 * @return taustan gradient
	 */
	public GradientFill annaTaustanGradient() {
		return this.taustaGradient;
	}
/*	
	/**
	 * @return
	 *
	public Tausta annaTausta() {
		return this;
	}
*/
}
