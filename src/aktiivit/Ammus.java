package aktiivit;

import org.newdawn.slick.geom.Vector2f;

/**
 * Luokka joka hoitaa ammuksen tiedot ja lentämisen.
 * @author Johannes
 *
 */
public class Ammus {

/**
 * painovoimavakio
 */
	private final Vector2f painovoima = new Vector2f(0,(float) 0.1);

	private Vector2f nopeusvektori;
	private Vector2f tuulivoima;

	private int tuhovoima;

	private float x;
	private float y;
	
	private Tankki ampuja;

	/**
	 * 
	 * @param tuulinuoli	vallitseva Tuulinuoli
	 * @param ampuja		Ammuksen ampunut Tankki
	 * @param tuhovoima		Ammuksen tuhovoima
	 */
	public Ammus(Tuulinuoli tuulinuoli, Tankki ampuja, int tuhovoima) {
		System.out.println("ammus alkaa");

		this.tuhovoima = tuhovoima;
		this.ampuja = ampuja;

		this.x = ampuja.annaX();
		this.y = ampuja.annaY()-10;

		float lahtokulma = ampuja.annaPiippu().getRotation()-90;
		float lahtonopeus = ampuja.annaLahtonopeus(false);

		this.nopeusvektori = new Vector2f(lahtokulma).scale(lahtonopeus);


		// Lasketaan tuulivoima, joka vaikuttaa lennon aikana. 
		// Tuuli muuttuu vain vuoron vaihtuessa, joten ammuksen 
		// elinkaareen mahtuu vain yksi tuulivoima.
		this.laskeTuulivoima(tuulinuoli);

		System.out.println("ammus luotu, alkunopeusvektori kulma :" + lahtokulma +" nopeus "+lahtonopeus);
	}

	/**
	 * Antaa Ammuksen nopeusvektorin
	 * @return	nopeusvektori
	 */
	public Vector2f annaNopeusvektori() {
		return this.nopeusvektori;
	}
/**
 * Laskee ja tallentaa tuulivoima-vektorin
 * @param tuulinuoli 	voimassa oleva Tuulinuoli
 */
	private void laskeTuulivoima(Tuulinuoli tuulinuoli) {
		/* jos tuuli olisi 100, se on 50% painovoimasta 
		 * -> tuulivoima == tuulenSuuruus*0.0005
		 * 0.1/2 == 0.05 == 100 * 0.0005
		 */
		float x_komponentti = (float) (tuulinuoli.annaSuuruus()*0.0005);
		
		//System.out.println("tuulivoima : " + x_komponentti);
		
		this.tuulivoima = new Vector2f(x_komponentti, 0);
	}
/**
 * Antaa tuulivoimavektorin
 * @return	tuulivoimavektori
 */
	private Vector2f annaTuulivoima() {
		return this.tuulivoima; 
	}
/**
 * Antaa ampujan
 * @return	ampuja
 */
	public Tankki annaAmpuja() {
		return this.ampuja;
	}
/**
 * Antaa x:n
 * @return x
 */
	public float annaX() {
		return this.x;
	}
	/**
	 * Antaa y:n
	 * @return y
	 */
	public float annaY() {
		return this.y;
	}
/**
 * antaa ammuksen tuhovoiman
 * @return	tuhovoima
 */
	public int annaTuhovoima() {
		return this.tuhovoima;
	}
/**
 * Lennättää ammusta eli päivittää 
 * nopeusvektorin ja koordinaatit 
 * @param x		tämänhetkinen x-koordinaatti
 * @param y		tämänhetkinen y-koordinaatti
 * @param nopeusvektori		tämänhetkinen nopeusvektori
 */
	public void lenna(float x, float y, Vector2f nopeusvektori) {

		this.nopeusvektori = annaNopeusvektori().add(this.painovoima).add(annaTuulivoima());

		this.x = this.x + nopeusvektori.getX();
		this.y = this.y + nopeusvektori.getY();

	}
}
