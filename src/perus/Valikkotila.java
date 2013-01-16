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

import aktiivit.Tankki;

import util.Satunnaisuus;

/**
 * GameState-luokka päävalikkoa varten.
 * @author Johannes
 *
 */
public class Valikkotila extends BasicGameState {

	private int id;

	private int napinleveys;
	private int napinkorkeus;

	private int valikonx;

	private int aloitay;
	private int creditsy;
	private int lopetay;

	private Image aloitanappi;
	private Image creditsnappi;
	private Image lopetanappi;
	private Image otsikko;

	private Rectangle aloitanelio;
	private Rectangle creditsnelio;
	private Rectangle lopetanelio;

	private Tausta tausta;
	private Maasto maasto;

	private Tankki tankki;




	public Valikkotila(int id) {
		this.id = id;
	}


	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {

		this.aloitanappi = new Image("res/start.png");
		this.creditsnappi = new Image("res/credits.png");
		this.lopetanappi = new Image("res/quit.png");
		this.otsikko = new Image("res/Menutitle_green_blur2.png");

		//samankokoiset napit
		this.napinleveys = this.aloitanappi.getWidth();
		this.napinkorkeus = this.aloitanappi.getHeight();

		//napeilla sama x-arvo
		valikonx = gc.getWidth()/2-this.napinleveys/2;

		aloitay = gc.getHeight()/2-this.napinkorkeus/2;
		creditsy = gc.getHeight()/2+this.napinkorkeus/2;
		lopetay = gc.getHeight()/2+this.napinkorkeus/2*3;

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

		this.maasto = new Maasto(gc, Satunnaisuus.annaInt(10)+10);
		this.tausta = new Tausta(gc);

		this.tankki = new Tankki(gc);
		this.tankki.asetaX(gc.getWidth()/6);
		this.tankki.asetaY(this.tankki.laskeY(this.maasto));
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {

		g.fill(this.tausta, this.tausta.annaTaustanGradient());
		g.fill(this.maasto, this.maasto.annaMaastonGradient());

		this.aloitanappi.draw(valikonx, aloitay);
		this.creditsnappi.draw(valikonx, creditsy);
		this.lopetanappi.draw(valikonx, lopetay);

		this.otsikko.drawCentered(gc.getWidth()/2, gc.getHeight()/6);


		this.tankki.draw(this.tankki.annaX()-(this.tankki.getWidth()/2), 
				this.tankki.annaY()-this.tankki.getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame peli, int delta)
			throws SlickException {

		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

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

	private void creditsnappiPainettu(GameContainer gc, StateBasedGame peli) 
			throws SlickException {

		this.leave(gc, peli);
		peli.enterState(Peli.CREDITSTILA);
	}


	private void aloitusnappiPainettu(GameContainer gc, StateBasedGame peli) 
			throws SlickException {

		this.leave(gc, peli);
		peli.enterState(Peli.ASETUSTILA);

	}

	private void lopetusnappiPainettu(GameContainer gc) {
		gc.exit();
	}

	@Override
	public int getID() {
		return this.id;
	}

}
