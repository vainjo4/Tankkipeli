package perus;
import maisema.Maasto;
import maisema.Tausta;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import aktiivit.Tankki;

import util.Satunnaisuus;

/**
 * GameState-luokka paavalikkoa varten.
 * @author 290836
 *
 */
public class Valikkotila extends BasicGameState {

	private int id;
	
	/**
	 * apumitat ja -koordinaatit
	 */
	private int napinleveys;
	private int napinkorkeus;
	private int valikonx;
	private int aloitay;
	private int creditsy;
	private int lopetay;

	/**
	 * napit
	 */
	private Image aloitanappi;
	private Image creditsnappi;
	private Image lopetanappi;
	private Image otsikko;

	/**
	 * nappien neliot
	 */
	private Rectangle aloitanelio;
	private Rectangle creditsnelio;
	private Rectangle lopetanelio;

	private Tausta tausta;
	private Maasto maasto;
	private Tankki tankki;

	/**
	 * 
	 * @param id	GameStaten id-numero
	 */
	public Valikkotila(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {

		//ladataan kuvat
		this.aloitanappi = new Image(
				(ResourceLoader.getResource("res/start.png").getPath()));
		this.creditsnappi = new Image(
				(ResourceLoader.getResource("res/credits.png").getPath()));
		this.lopetanappi = new Image(
				(ResourceLoader.getResource("res/quit.png").getPath()));
		this.otsikko = new Image(
				(ResourceLoader.getResource("res/menutitle_green_blur2.png").getPath()));

		//kaikki napit samankokoisia
		this.napinleveys = this.aloitanappi.getWidth();
		this.napinkorkeus = this.aloitanappi.getHeight();

		//kaikilla napeilla sama x-arvo
		valikonx = gc.getWidth()/2-this.napinleveys/2;

		//napit kiinni toisiinsa
		aloitay = gc.getHeight()/2-this.napinkorkeus/2;
		creditsy = gc.getHeight()/2+this.napinkorkeus/2;
		lopetay = gc.getHeight()/2+this.napinkorkeus/2*3;

		//hiirentarkkailuneliot
		this.aloitanelio = new Rectangle(
				this.valikonx, 
				this.aloitay, 
				this.napinleveys, 
				this.napinkorkeus);

		this.creditsnelio = new Rectangle(
				this.valikonx, 
				this.creditsy, 
				this.napinleveys, 
				this.napinkorkeus);


		this.lopetanelio = new Rectangle(
				this.valikonx, 
				this.lopetay, 
				this.napinleveys, 
				this.napinkorkeus);

		//tehdaan maisemaksi satunnainen maasto
		this.maasto = new Maasto(gc, Satunnaisuus.annaInt(10)+10);

		//normaali tausta
		this.tausta = new Tausta(gc);

		//alustetaan ja sijoitetaan koristetankki 
		this.tankki = new Tankki(gc);
		this.tankki.asetaX(gc.getWidth()/6);
		this.tankki.asetaY(this.tankki.laskeYmaastoon(this.maasto));

	}

	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {

		//piirretaan taustat ja maastot
		g.fill(this.tausta, this.tausta.annaTaustanGradient());
		g.fill(this.maasto, this.maasto.annaMaastonGradient());

		//piirretaan napit
		g.drawImage(this.aloitanappi, valikonx, aloitay);
		g.drawImage(this.creditsnappi, valikonx, creditsy);
		g.drawImage(this.lopetanappi, valikonx, lopetay);

		//piirretaan otsikko ja koristetankki
		this.otsikko.drawCentered(gc.getWidth()/2, gc.getHeight()/6);

		g.drawImage(this.tankki, this.tankki.annaX()-(this.tankki.getWidth()/2), 
				this.tankki.annaY()-this.tankki.getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame peli, int delta)
			throws SlickException {

		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		//nappienpainamissetti

		if(this.aloitanelio.contains(mouseX, mouseY) 
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			this.aloitusnappiPainettu(gc, peli);
		}

		else if(this.creditsnelio.contains(mouseX, mouseY) 
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			this.creditsnappiPainettu(gc, peli);
		}

		else if(this.lopetanelio.contains(mouseX, mouseY) 
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			this.lopetusnappiPainettu(gc);
		}
	}
	/**
	 * Menee Creditstilaan
	 * @param gc	pelin GameContainer
	 * @param peli	peli itse
	 * @throws SlickException
	 */
	private void creditsnappiPainettu(GameContainer gc, StateBasedGame peli) 
			throws SlickException {

		this.leave(gc, peli);
		peli.enterState(Peli.CREDITSTILA);
	}

	/**
	 * Menee Asetustilaan
	 * @param gc	pelin GameContainer
	 * @param peli	peli itse
	 * @throws SlickException
	 */
	private void aloitusnappiPainettu(GameContainer gc, StateBasedGame peli) 
			throws SlickException {

		this.leave(gc, peli);
		peli.enterState(Peli.ASETUSTILA);

	}
	/**
	 * Lopettaa pelin
	 * @param gc	pelin GameContainer
	 */
	private void lopetusnappiPainettu(GameContainer gc) {
		gc.exit();
	}

	@Override
	public int getID() {
		return this.id;
	}

}
