package aktiivit;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Polygon;

/**
 * Taustalle piirrettävä, tuulen mukaan rakentuva monikulmio.
 * @author Johannes
 *
 */
@SuppressWarnings("serial")
public class Tuulinuoli extends Polygon {

	private Random kone = new Random();
	private int tuulenSuuruus;
	private int x;
	private int y;

	/**
	 * Luo uuden tuulinuolen
	 * @param gc pelin GameContainer
	 */
	public Tuulinuoli(GameContainer gc) {

		this.x = gc.getWidth()/2;	
		this.y = gc.getHeight()/24;

		this.arvoSuuruus();

		this.addPoint(this.x, this.y-15);
		this.addPoint(this.x, this.y+15);
		this.addPoint(this.x+this.annaSuuruus(), this.y);

		System.out.println("TuulenSuuruus : "+this.annaSuuruus());


	}
	/**
	 * Arpoo tuulelle suuruuden. Negatiiviset arvot 
	 * ovat vasemmalle puhaltavaa tuulta. 
	 */
	private void arvoSuuruus() {
		this.tuulenSuuruus = kone.nextInt(101)-50;
	}
	/**
	 * Antaa tuulenvoimakkuuden.
	 * @return tuulenSuuruus
	 */
	public int annaSuuruus() {
		return this.tuulenSuuruus;
	}

	/**
	 * Antaa x:n
	 * @return x
	 */
	public int annaX() {
		return this.x;
	}
	/**
	 * Antaa y:n
	 * @return y
	 */
	public int annaY() {
		return this.y;
	}

}
