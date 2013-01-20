package perus;

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

/**
 * GameState-luokka Credits-nakymaa varten.
 * @author 290836
 *
 */
public class Creditstila extends BasicGameState {

	private int id;
	private Tausta tausta;
	private Image nappi;
	private int nappix;
	private int nappiy;
	private Rectangle nappinelio;
	
	private Tankki tankki;

	/**
	 * 
	 * @param id	GameStaten id-numero
	 */
	public Creditstila(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {
		
		//ladataan kuva
		this.nappi = new Image(ResourceLoader.getResource("res/mainmenu.png").getPath());
		
		//napin koordinaatit
		this.nappix = gc.getWidth()/2-nappi.getWidth()/2;
		this.nappiy = gc.getHeight()-nappi.getHeight()-30;
		
		//napin hiirinelio
		this.nappinelio = new Rectangle(
				nappix, 
				nappiy, 
				nappi.getWidth(), 
				nappi.getHeight());
		
		//tankiksi koristetankki
		this.tankki = new Tankki(gc);
		
		//tausta kuten yleensa
		this.tausta = new Tausta(gc);
	}


	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {

		//piirretaan tausta
		g.fill(this.tausta, this.tausta.annaTaustanGradient());
		
		//piirretaan tankki
		g.drawImage(this.tankki, 
				this.tankki.annaX()-(this.tankki.getWidth()/2), 
				this.tankki.annaY()-this.tankki.getHeight());
		
		g.drawString(annaKrediittiString(gc), gc.getWidth()/2-125, gc.getHeight()/8);
		
		g.drawImage(this.nappi, this.nappix, this.nappiy);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame peli, int delta)
			throws SlickException {
		
		this.tankki.putoa(gc);
		this.tankki.rotate((float) 0.1*delta);
		
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
				"everything else by 290836";
	}

	@Override
	public int getID() {
		return this.id;
	}

}
