package maisema;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Kivikko {

	public static final int PIKSELIVAKIO = 3; 

	private Shape maasto;
	private Kivi[][] kivitaulu;

	public Kivikko(GameContainer gc, Shape maasto) {
		this.maasto = maasto;

		int leveys = gc.getWidth();
		int korkeus = gc.getHeight();

		this.kivitaulu = new Kivi[leveys][korkeus];

		System.out.println("neliötaulun korkeus: "+annaKorkeus() + " leveys :" + annaLeveys());

		this.teeKivikko(gc, leveys, korkeus);

	}
	/**
	 * 
	 * @param gc
	 * @param leveys
	 * @param korkeus
	 */
	public void teeKivikko(GameContainer gc, int leveys, int korkeus) {
		int y;
		int x = 0;
		while (x < leveys) {

			y = 0;
			while (y < korkeus) {

				if(this.maasto.contains(x,y)) {
					this.kivitaulu[x][y] = new Kivi(x,y);
				}
				y = y+PIKSELIVAKIO;
			}
			x = x+PIKSELIVAKIO;
		}
	}

	public Kivi annaTaulukosta(int x, int y) {
		return this.kivitaulu[x][y];
	}
	
	public void poistaTaulukosta(int x, int y) {
		if(y < this.annaKorkeus()-10) {
			this.kivitaulu[x][y] = null;
		}
	}
	public void lisaaTaulukkoon(int x, int y) {
		this.kivitaulu[x][y] = new Kivi(x,y);	
	}

	public int annaKorkeus() {
		return this.kivitaulu[0].length;
	}

	public int annaLeveys() {
		return this.kivitaulu.length;
	}

	public void teeKivikkoonKolo(Circle reika, GameContainer gc) {
		
		int leveys = this.annaLeveys();
		int korkeus = this.annaKorkeus();

		int x = 0;
		int y;
		while(x < leveys) {
			y = 0;
			while(y < korkeus) {
				if (this.annaTaulukosta(x,y) != null 
						&& reika.intersects(this.annaTaulukosta(x,y))) {
					this.poistaTaulukosta(x,y);
					System.out.println("poisto");

				}
				y++;
			}
			x++;
		}
	}
	
	public void tiputaKivia(GameContainer gc) {
		//loopit aloittavat alhaalta jotta toimii kerralla.

		// -1 välttää oob-poikkeuksen
		int x = this.annaLeveys()-1;
		int y;
		int uusiY;
		while(x >= 0) {
			// -1 välttää oob:n ja -3 pitää y+3:n taulukossa 
			y = this.annaKorkeus()-4;
			while(y >= 0) {

				//+3 on kovakoodattu pikselivakio
				if(this.annaTaulukosta(x,y) != null 
						&& this.annaTaulukosta(x,y+3) == null) {

					//tämä silmukka tiputtaa pystyrivin kaikki kivet kerralla
					uusiY = y;
					if(uusiY < this.annaKorkeus()-10) {
						while(this.annaTaulukosta(x,uusiY+3) == null) {
							this.poistaTaulukosta(x, uusiY);
							this.lisaaTaulukkoon(x, uusiY+3);
							uusiY = uusiY+3;
						}	
					}
				}
				y--;
			}
			x--;
		}
	}

}
