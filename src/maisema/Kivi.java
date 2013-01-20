package maisema;

import org.newdawn.slick.geom.Rectangle;

/**
 * Neliön alaluokka jota käytetään Kivikossa.
 * @author Johannes
 *
 */
@SuppressWarnings("serial")
public class Kivi extends Rectangle {

	private int x;
	private int y;

	/**
	 * Kivien koko kovakoodattu 3*3
	 * @param x
	 * @param y
	 */
	public Kivi(int x, int y) {
		super(x,y,3,3);

		this.x = x;
		this.y = y;

	}
	/**
	 * Antaa x: n
	 * @return	x
	 */
	public int annaX() {
		return this.x;
	}
	/**
	 * Antaa y:n
	 * @return	y
	 */
	public int annaY() {
		return this.y;
	}
}
