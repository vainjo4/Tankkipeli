package maisema;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;


/**
 * Luokka, joka huolehtii Kivista.
 * 
 * Kivien koko on kautta ohjelman kovakoodattu olemaan 3*3.
 * Yritin Pikselivakion kayttoa, mutta suuren lapikaymisurakan
 * (mm. Pelitilan update-metodi) vuoksi lisaoperaatiot aiheuttivat 
 * vain lisarasitteen laskentateholle.
 * 
 * @author 290836
 */
public class Kivikko {

	private Kivi[][] kivitaulu;

	/**
	 * Luo Kivikon Maaston mukaan.
	 * @param gc	pelin GameContainer.
	 * @param maasto	Maasto jonka mukaan tehdaan.
	 */
	public Kivikko(GameContainer gc, Shape maasto) {


		int leveys = gc.getWidth();
		int korkeus = gc.getHeight();

		this.kivitaulu = new Kivi[leveys][korkeus];
		this.teeKivikko(gc, leveys, korkeus, maasto);
	}
	/**
	 * Tayttaa Kivikon Kivilla.
	 * @param gc pelin GameContainer
	 * @param leveys	taulukon leveys
	 * @param korkeus	taulukon korkeus
	 * @param maasto	Maasto, jonka mukaan Kivikko tehdaan
	 */
	public void teeKivikko(GameContainer gc, int leveys, 
			int korkeus, Shape maasto) {
		//3 on nelion sivun pituus
		int x = 0;
		while (x < leveys) {

			int y = 0;
			while (y < korkeus) {

				if(maasto.contains(x,y)) {
					this.kivitaulu[x][y] = new Kivi(x,y);
				}
				y = y+3;
			}
			x = x+3;
		}
	}

	/**
	 * Antaa Kiven koordinaattien perusteella.
	 * @param x	annettavan x-koordinaatti
	 * @param y	annettavan y-koordinaatti
	 * @return Kivi koordinaateissa
	 */
	public Kivi annaTaulukosta(int x, int y) {
		return this.kivitaulu[x][y];
	}

	/**
	 * Poistaa taulukosta Kiven.
	 * @param x	poistettavan x-koordinaatti
	 * @param y	poistettavan y-koordinaatti
	 */
	public void poistaTaulukosta(int x, int y) {
		//ehto ei salli kivien poistamista aivan alareunasta
		if(y < this.annaKorkeus()-10) {
			this.kivitaulu[x][y] = null;
		}
	}

	/**
	 * Lisaa taulukkoon Kiven
	 * @param x	lisattavan x-koordinaatti
	 * @param y	lisattavan y-koordinaatti
	 */
	public void lisaaTaulukkoon(int x, int y) {
		this.kivitaulu[x][y] = new Kivi(x,y);	
	}
	/**
	 * Antaa taulukon korkeuden.
	 * @return	taulukon y-suuntainen pituus
	 */
	public int annaKorkeus() {
		return this.kivitaulu[0].length;
	}
	/**
	 * Antaa taulukon leveyden.
	 * @return	taulukon x-suuntainen pituus
	 */
	public int annaLeveys() {
		return this.kivitaulu.length;
	}

	/**
	 * Tekee Kivikkoon reika-muotoisen reian. 
	 * @param reika	poistettava muoto
	 * @param gc	pelin GameContainer
	 */
	public void teeKivikkoonKolo(Shape reika, GameContainer gc) {

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
				}
				y = y+3;
			}
			x = x+3;
		}
	}
	/**
	 * Poistaa ja lisaa kivia siten, 
	 * etta y-suuntaiset taulukot ovat yhtenaisia,
	 * eli "tiputtaa" kivia.
	 * @param gc pelin GameContainer
	 */
	public void tiputaKivia(GameContainer gc) {
		//loopit aloittavat alhaalta jotta toimii kerralla.

		// -1 valttaa oob-poikkeuksen
		int x = this.annaLeveys()-1;
		int y;
		int uusiY;
		while(x >= 0) {
			// -1 jotta valttaa oob:n ja -3 pitaa y+3:n taulukossa, 

			y = this.annaKorkeus()-4;
			while(y >= 0) {

				//+3 on nelion sivun pituus
				if(this.annaTaulukosta(x,y) != null 
						&& this.annaTaulukosta(x,y+3) == null) {

					//silmukka tiputtaa pystyrivin kaikki kivet kerralla
					uusiY = y;
					if(uusiY < this.annaKorkeus()-10) {
						while(this.annaTaulukosta(x,uusiY+3) == null) {
							this.poistaTaulukosta(x, uusiY);
							this.lisaaTaulukkoon(x, uusiY+3);
							uusiY = uusiY+3;
						}	
					}
				}
				//inkrementti (tai dekrementti) 
				//== 1 varmatoimisuuden vuoksi
				y--;
			}
			x--;
		}
	}
}
