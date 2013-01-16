package aktiivit;

import org.newdawn.slick.geom.Vector2f;

/**
 * 
 * @author Johannes
 *
 */
public class Ammus {


	private final Vector2f painovoima = new Vector2f(0,(float) 0.1);

	private Vector2f nopeusvektori;
	private Vector2f tuulivoima;

	private int tuhovoima;
	
	private Tuulinuoli tuulinuoli;

	private float x;
	private float y;
	private Tankki tankki;

	public Ammus(Tuulinuoli tuulinuoli, Tankki tankki, int tuhovoima) {
		System.out.println("ammus alkaa");

		this.tuhovoima = tuhovoima;
		this.tuulinuoli = tuulinuoli;
		this.tankki = tankki;

		this.x = tankki.annaX();
		this.y = tankki.annaY()-10;

		float lahtokulma = tankki.annaPiippu().getRotation()-90;
		float lahtonopeus = tankki.annaLahtonopeus(false);

		this.nopeusvektori = new Vector2f(lahtokulma).scale(lahtonopeus);


		// Lasketaan tuulivoima, joka vaikuttaa lennon aikana. 
		// Tuuli muuttuu vain vuoron vaihtuessa, joten ammuksen 
		// elinkaareen mahtuu vain yksi tuulivoima.
		this.laskeTuulivoima();

		System.out.println("ammus luotu, alkunopeusvektori kulma :" + lahtokulma +" nopeus "+lahtonopeus);
	}

	public Vector2f annaNopeusvektori() {
		return this.nopeusvektori;
	}

	private void laskeTuulivoima() {
		/* jos tuuli on 100, se on 50% painovoimasta 
		 * -> tuulivoima == tuulenSuuruus*0.0005
		 * 0.1/2 == 0.05 == 100 * 0.0005
		 */

		float x_komponentti = (float) (tuulinuoli.annaSuuruus()*0.0005);
		System.out.println("tuulivoima : " + x_komponentti);
		this.tuulivoima = new Vector2f(x_komponentti, 0);
	}

	private Vector2f annaTuulivoima() {
		return this.tuulivoima; 
	}

	public Tankki annaAmpuja() {
		return this.tankki;
	}

	public float annaX() {
		return this.x;
	}
	public float annaY() {
		return this.y;
	}

	
	public int annaTuhovoima() {
		return this.tuhovoima;
	}

	public void lenna(float x, float y, Vector2f nopeusvektori) {

		this.nopeusvektori = annaNopeusvektori().add(painovoima).add(annaTuulivoima());

		this.x = this.x + nopeusvektori.getX();
		this.y = this.y + nopeusvektori.getY();

	}

	public static void main(String[] args) {

	}

}
