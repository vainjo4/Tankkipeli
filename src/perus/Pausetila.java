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
import org.newdawn.slick.util.ResourceLoader;

/**
 * GameState-luokka pausevalikkoa varten.
 * @author 290836
 *
 */
public class Pausetila extends BasicGameState {

	private int id;

	private Shape maasto;

	/**
	 * nappien kuvat
	 */
	private Image eteennappi;
	private Image taaksenappi;

	/**
	 * nappien neliot
	 */
	private Rectangle eteennelio;
	private Rectangle taaksenelio;

	/**
	 * valikkoruutu
	 */
	private Rectangle ruutu;

	/**
	 * apumittoja ja -koordinaatteja
	 */
	private int eteenx;
	private int eteeny;
	private int taaksex;
	private int taaksey;
	private int ruudunkorkeus;
	private int ruudunleveys;

	private Tausta tausta;

	/**
	 * Luo pausetilan
	 * @param id	tilan id-numero
	 */
	public Pausetila(int id) {
		this.id = id;

	}

	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {

		//alustetaan mitat apumuuttujiin
		this.ruudunkorkeus = gc.getHeight();
		this.ruudunleveys = gc.getWidth();

		//alustetaan valikkoruutu
		this.ruutu = new Rectangle(
				ruudunleveys/3, 
				ruudunkorkeus/3, 
				ruudunleveys/3, 
				ruudunkorkeus/3);

		//alustetaan napit
		this.eteennappi = new Image(
				ResourceLoader.getResource("res/smallmainmenu.png").getPath());
		this.taaksenappi = new Image(
				ResourceLoader.getResource("res/smallback.png").getPath());

		//alustetaan x:t
		this.eteenx = this.ruudunleveys/2-this.eteennappi.getWidth()/2;
		this.taaksex = this.ruudunleveys/2-this.taaksenappi.getWidth()/2;

		//alustetaan y:t
		this.eteeny = this.ruudunkorkeus/2-this.eteennappi.getHeight()*3/4;
		this.taaksey = this.ruudunkorkeus/2+this.taaksenappi.getHeight()/2;	

		//alustetaan neliot
		this.eteennelio = new Rectangle(
				this.eteenx,
				this.eteeny,
				this.eteennappi.getWidth(),
				this.eteennappi.getHeight());

		this.taaksenelio = new Rectangle(
				this.taaksex, 
				this.taaksey,
				this.taaksenappi.getWidth(), 
				this.taaksenappi.getHeight());

		//alustetaan tausta
		this.tausta = new Tausta(gc);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {

		//piirretaan tausta
		g.fill(this.tausta, this.tausta.annaTaustanGradient());

		//piirretaan maasto
		if(this.maasto instanceof Maasto) {
			g.fill(this.maasto, ((Maasto) this.maasto).annaMaastonGradient());
		}

		//piirretaan valikkoruutu
		g.fill(this.ruutu, new GradientFill(
				0, this.ruutu.getMinY(), new Color(200, 200, 200),
				gc.getWidth(), gc.getHeight(), new Color(0,0,0)));

		//piirretaan napit
		g.drawImage(this.eteennappi, eteenx, eteeny);
		g.drawImage(this.taaksenappi, taaksex, taaksey);

		//piirretaan PAUSED-teksti
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

		//nappeja voi painaa.
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
	 * Asettaa maaston.
	 * @param maasto	Maasto, jonka Pausetila tallettaa.
	 */
	public void asetaMaasto(Shape maasto) {
		this.maasto = maasto;
	}
}
