package perus;
import maisema.Maasto;
import maisema.Tausta;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * GameState-luokka pausevalikkoa varten.
 * @author Johannes
 *
 */
public class Pausetila extends BasicGameState {
		
	private static Shape maasto;
	
	private Image eteennappi;
	private Image taaksenappi;
	
	private Rectangle eteennelio;
	private Rectangle taaksenelio;
	
	private Rectangle ruutu;
	private int id;

	private int eteenx;
	private int eteeny;
	private int taaksex;
	private int taaksey;
	
	private int ruudunkorkeus;
	private int ruudunleveys;
	private Tausta tausta;
	
	public Pausetila(int id) {
	this.id = id;
		
	}
	
	

	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {

		this.ruudunkorkeus = gc.getHeight();
		this.ruudunleveys = gc.getWidth();
		
		this.ruutu = new Rectangle(
				ruudunleveys/3, 
				ruudunkorkeus/3, 
				ruudunleveys/3, 
				ruudunkorkeus/3);
		
		this.eteennappi = new Image("res/smallmainmenu.png");
		this.taaksenappi = new Image("res/smallback.png");
		
		this.eteenx = this.ruudunleveys/2-this.eteennappi.getWidth()/2;
		this.taaksex = this.ruudunleveys/2-this.taaksenappi.getWidth()/2;
		
		this.eteeny = this.ruudunkorkeus/2-this.eteennappi.getHeight()*3/4;
		this.taaksey = this.ruudunkorkeus/2+this.taaksenappi.getHeight()/2;	
		
		this.eteennelio = new Rectangle(this.eteenx, this.eteeny, this.eteennappi.getWidth(), this.eteennappi.getHeight());
		this.taaksenelio = new Rectangle(this.taaksex, this.taaksey, this.taaksenappi.getWidth(), this.taaksenappi.getHeight());
		
		this.tausta = new Tausta(gc);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {
		
			g.fill(this.tausta, this.tausta.annaTaustanGradient());
		
			g.fill(Pausetila.maasto, ((Maasto) Pausetila.maasto).annaMaastonGradient());
			
			g.fill(ruutu, new GradientFill(
					0, this.ruutu.getMinY(), new Color(200, 200, 200),
					gc.getWidth(), gc.getHeight(), new Color(0,0,0)));
			
			this.eteennappi.draw(eteenx, eteeny);
			this.taaksenappi.draw(taaksex, taaksey);
			g.drawString("PAUSED",
					this.ruudunleveys/2-30, 
					this.ruudunkorkeus/2-this.eteennappi.getHeight()*4/3);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame peli, int delta)
			throws SlickException {
		
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		if(this.eteennelio.contains(mouseX, mouseY) 
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			this.leave(gc, peli);
			
			peli.enterState(Peli.VALIKKOTILA);	
		}
		else if(this.taaksenelio.contains(mouseX, mouseY) 
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			this.leave(gc, peli);
			peli.enterState(Peli.PELITILA);	
		}
	}

	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * Kutsutaan Pelitilassa esciä painettaessa. Staattinen, 
	 * koska Pelitilalla ei viittausta Pausetilaan ja Slick-metodit eivät 
	 * @param maasto	Maasto, jonka Pausetila laittaa parametrikseen.
	 */
	public static void otaMaasto(Shape maasto) {
		Pausetila.maasto = maasto;
	}
}
