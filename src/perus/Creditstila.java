package perus;

import maisema.Tausta;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import aktiivit.Piippu;
import aktiivit.Tankki;

public class Creditstila extends BasicGameState {


	private int id;
	private Tausta tausta;
	private Image nappi;
	private int nappix;
	private int nappiy;
	private Rectangle nappinelio;
	
	private Tankki tankki;

	public Creditstila(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {
		
		this.nappi = new Image("res/mainmenu.png");
		
		this.nappix = gc.getWidth()/2-nappi.getWidth()/2;
		this.nappiy = gc.getHeight()-nappi.getHeight()-30;
		
		this.nappinelio = new Rectangle(
				nappix, 
				nappiy, 
				nappi.getWidth(), 
				nappi.getHeight());
		
		this.tankki = new Tankki(gc);
		//this.tankki.annaPiippu().setRotation(45);
		this.tausta = new Tausta(gc);
	}


	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {

		g.fill(this.tausta, this.tausta.annaTaustanGradient());
		
	//	Piippu piippu = this.tankki.annaPiippu();
		
		g.drawImage(this.tankki, 
				this.tankki.annaX()-(this.tankki.getWidth()/2), 
				this.tankki.annaY()-this.tankki.getHeight());
		/*
		piippu.drawCentered(piippu.annaX(),
				piippu.annaY());
	*/
		//System.out.println(piippu.annaX() + " oli x, y: " + piippu.annaY());
		
		//this.tankki.draw(this.tankki.annaX(), this.tankki.annaY());
		
		
		g.drawString(annaKrediittiString(gc), gc.getWidth()/2-125, gc.getHeight()/8);
		
		this.nappi.draw(this.nappix, this.nappiy);
		
		Tankki vaunu = this.tankki;
		/*
		g.draw(new Rectangle(vaunu.annaX()-(vaunu.getWidth()/2),
				vaunu.annaY()-vaunu.getHeight(), 3,3));
*
		g.draw(new Rectangle(piippu.annaX(),
				piippu.annaY(),3,3));
*
		g.draw(new Rectangle(piippu.annaX()+piippu.getCenterOfRotationX(),
				vaunu.annaY()+piippu.getCenterOfRotationY(),3,3));
		/*
		g.draw(new Rectangle(vaunu.annaX(),
				vaunu.annaY(),3,3));
		*/
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame peli, int delta)
			throws SlickException {
		
		this.tankki.putoa(gc);
		this.tankki.rotate((float) 0.1*delta);
//		this.tankki.annaPiippu().rotate((float) 0.2*delta);
		
		Input input = gc.getInput();
		
		if(this.nappinelio.contains(
				input.getMouseX(),
				input.getMouseY()) 
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.leave(gc, peli);
			peli.enterState(Peli.VALIKKOTILA);
		}
	}

	private String annaKrediittiString(GameContainer gc) {
		return
				"       "+"Credits \n"+
				"\n"+
				"Music: 'Send Me' by Gurdonark \n"+
				"from ccMixter.org \n" +
				"\n"+
				"Sound effects: by Mike Koenig \n"+
				"from SoundBible.com \n"+
				"\n" +
				"Original explosion effect: void \n" +
				"from Slick wiki\n" +
				"\n"+
				"Slick2d: kevglass et al.\n" +
				"\n"+
				"Music and sounds used under \n"+
				"Creative Commons Attribution 3.0.\n" +
				"\n" +
				"everything else by Johannes Vainio";
	}

	@Override
	public int getID() {
		return this.id;
	}

}
