package maisema;

import org.newdawn.slick.geom.Rectangle;

@SuppressWarnings("serial")
public class Kivi extends Rectangle {

	private int x;
	private int y;

	/**
	 * Pikselivakio määrää neliöiden koon
	 * @param x
	 * @param y
	 */
	public Kivi(int x, int y) {
		super(x,y,Kivikko.PIKSELIVAKIO,Kivikko.PIKSELIVAKIO);

		this.x = x;
		this.y = y;
		
		//System.out.println("luotu kivi x: " +x+ " y: "+y);
	}

	public int annaX() {
		return this.x;
	}
	public int annaY() {
		return this.y;
	}
}
