package maisema;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;

import util.Satunnaisuus;

/**
 * Monikulmion alaluokka, joka on maaston graafinen esitys.
 * @author 290836
 *
 */
@SuppressWarnings("serial")
public class Maasto extends Polygon {

	private GradientFill maastoGradient;

	/**
	 * konstruktori, jota kaytetaan satunnaislukutaulukon kanssa luomaan "esimaasto"
	 * @param gc	pelin GameContainer
	 * @param pikseliaPerMaastoPiste
	 */
	public Maasto(GameContainer gc, int pikseliaPerMaastoPiste) {

		int ikkunanleveys = gc.getWidth(); 
		int ikkunankorkeus = gc.getHeight();
		int maara = (ikkunanleveys/pikseliaPerMaastoPiste)+2;

		int[] maanpinta = Satunnaisuus.satunnaisPolku(maara, ikkunankorkeus);
		int i = 0;

		while(i < maara) {

			this.addPoint(i * pikseliaPerMaastoPiste, maanpinta[i]);
			i++;
		}

		this.addPoint(ikkunanleveys, ikkunankorkeus);
		this.addPoint(0, ikkunankorkeus);
		
		//System.out.println("maastossa pisteita " + this.getPointCount());

		if(this.maastoGradient == null) {
			teeGradient(gc);
		}
	}
	/**
	 * Konstruktori, jolla luodaan kivikon mukaan muotoutuva maasto
	 * @param gc
	 * @param kivikko
	 */
	public Maasto(GameContainer gc, Kivikko kivikko) {
		int x = kivikko.annaLeveys()-1;
		int y;
		while(x > 0) {
			y = kivikko.annaKorkeus()-4; 
			while(y > 0) {
				if(kivikko.annaTaulukosta(x, y) != null) {
					if(kivikko.annaTaulukosta(x, y-3) == null){
						this.addPoint(x,y);
					}
				}
				y--;
			}
			x--;
		}
		this.addPoint(-30, gc.getHeight());
		this.addPoint(gc.getWidth()+30, gc.getHeight());

		if(this.maastoGradient == null) {
			teeGradient(gc);
		}
	}
	/**
	 * alustaa maastojen kayttaman GradientFillin
	 * @param gc	pelin GameContainer
	 */
	private void teeGradient(GameContainer gc) {
		this.maastoGradient = new GradientFill(
				0, this.getMinY()/2, new Color(150, 200, 150),
				gc.getWidth(), gc.getHeight(), new Color(50,90,50));

	}
	/**
	 * antaa maaston GradientFillin
	 * @return	maastoGradient
	 */
	public GradientFill annaMaastonGradient() {
		return this.maastoGradient;
	}
}
