package aktiivit;
import org.newdawn.slick.geom.Ellipse;

/**
 * Ellipsin alaluokka. Tankintörmäysmallia käytetään hyväksi 
 * ammuksen ja tankin välisessä törmäyksentunnistuksessa.
 * @author Johannes
 *
 */
@SuppressWarnings("serial")
public class Tankintormaysmalli extends Ellipse {

	/**
	 * Luo tankintormaysmallin.
	 * @param tankki	tankki, jolle malli luodaan
	 */
	public Tankintormaysmalli(Tankki tankki) {
		super(tankki.annaX(), tankki.annaY()-10, 15, 8);
		//Ellipse(float centerPointX, float centerPointY, float radius1, float radius2) 
	}
}
