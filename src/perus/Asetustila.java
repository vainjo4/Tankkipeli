package perus;

import maisema.Tausta;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.Parametrit;

/**
 * GameState-luokka start-napin painamisen 
 * ja pelin alkamisen väliselle parametrienkysymisnäkymälle.
 * 
 * luokassa
 * Painonappi == Fullscreen-checkbox.
 * 
 * @author Johannes
 *
 */
public class Asetustila extends BasicGameState {

	private int id;

	private int okx;
	private int oky;
	private int poisx;
	private int poisy;
	private int painonappix;
	private int painonappiy;
	private int ruudunkorkeus;
	private int ruudunleveys;

	private Image oknappi;
	private Image poisnappi;
	private Image painonappi_normi;
	private Image painonappi_pohjassa;

	private Rectangle oknelio;
	private Rectangle poisnelio;
	private Rectangle painonappinelio;

	private TextField tankkikentta;
	private TextField tuhokentta;
	private TextField minimivalikentta;
	private TextField ppmpkentta;

	private int tankki_oletus = 2;
	private int tuho_oletus = 50;
	private int minimi_oletus = 150;
	private int ppmp_oletus = 10;

	private boolean painonappiPainettu;

	private Tausta tausta;

	public Asetustila(int id) {
		this.id = id;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {

		System.out.println("asetustila init"); 

		//tallennetaan mitat muuttujiin
		this.ruudunkorkeus = gc.getHeight();
		this.ruudunleveys = gc.getWidth();

		int kenttax = ruudunleveys/2+100;
		int	kenttay = ruudunkorkeus/6;	

		//käytetään oletusfonttia
		Font fontti = gc.getDefaultFont();

		//tavallinen tausta
		this.tausta = new Tausta(gc);

		//haetaan kuvat napeille
		this.oknappi = new Image("res/ok.png");
		this.poisnappi = new Image("res/mainmenu.png");
		this.painonappi_normi = new Image("res/checkbox.png");
		this.painonappi_pohjassa = new Image("res/checkbox_checked.png");

		//asetetaan nappien x:t ja y:t
		this.okx = this.ruudunleveys/3-this.oknappi.getWidth();
		this.poisx = (this.ruudunleveys/3)*2;
		this.painonappix = kenttax;

		this.oky = this.ruudunkorkeus-this.oknappi.getHeight()-30;			
		this.poisy = this.ruudunkorkeus-this.poisnappi.getHeight()-30;
		this.painonappiy = kenttay+180;


		//tehdään nappeja vastaavat neliöt hiirentarkkailua varten
		this.oknelio = new Rectangle(
				this.okx, 
				this.oky, 
				this.oknappi.getWidth(), 
				this.oknappi.getHeight());

		this.poisnelio = new Rectangle(
				this.poisx,
				this.poisy, 
				this.poisnappi.getWidth(), 
				this.poisnappi.getHeight());		

		this.painonappinelio = new Rectangle(
				this.painonappix, 
				this.painonappiy, 
				this.painonappi_normi.getWidth(), 
				this.painonappi_pohjassa.getHeight());

		/* Luodaan tekstikentät.
		 * konstruktori
		 * TextField(GUIContext container, Font font, int x, int y, int width, int height)
		 * nullcheckit, jotta kentät toimisivat myös reinitin jälkeen
		 */
		if(this.tankkikentta == null) {
			this.tankkikentta = new TextField(gc, fontti, 0, 0, 100,20);
		}
		if(this.tuhokentta == null) {
			this.tuhokentta = new TextField(gc, fontti, 0, 0, 100,20);
		}
		if(this.minimivalikentta == null) {
			this.minimivalikentta = new TextField(gc, fontti, 0, 0, 100,20);
		}
		if(this.ppmpkentta == null) {
			this.ppmpkentta = new TextField(gc, fontti, 0, 0, 100,20);
		}
		//asetetaan oletusarvot muuttujiin

		this.tankkikentta.setLocation(kenttax, kenttay);
		this.tuhokentta.setLocation(kenttax, kenttay+40);
		this.minimivalikentta.setLocation(kenttax, kenttay+80);
		this.ppmpkentta.setLocation(kenttax, kenttay+120);

		//oletusarvot kenttiin
		this.tankkikentta.setText(""+this.tankki_oletus);
		this.tuhokentta.setText(""+this.tuho_oletus);
		this.minimivalikentta.setText(""+this.minimi_oletus);
		this.ppmpkentta.setText(""+this.ppmp_oletus);
	//	this.asetaPainonappiPainettu(false);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {

		g.fill(this.tausta, this.tausta.annaTaustanGradient());

		this.oknappi.draw(this.okx, this.oky);
		this.poisnappi.draw(this.poisx, this.poisy);

		if(this.annaPainonappiPainettu()) {
			this.painonappi_pohjassa.draw(this.painonappix, this.painonappiy);
		}
		else {
			this.painonappi_normi.draw(this.painonappix, this.painonappiy);
		}

		g.drawString("Tank Count\n\n" +
				"Ammo Damage\n\n" +
				"Minimum Tank Distance\n\n" +
				"Pixels Per Terrain Point\n\n\n\n" +
				"Fullscreen",
				this.ruudunleveys/2-200, this.ruudunkorkeus/6);

		g.drawString("Arrow Keys: Angle and Power" +"          " +
				"ENTER: Shoot\n" +
				"ESC: Pause Menu"+"                      "+
				"SHIFT: Faster Adjustments", this.ruudunleveys/2-300, this.ruudunkorkeus/3*2-20);

		this.tankkikentta.render(gc, g);
		this.tuhokentta.render(gc, g);
		this.minimivalikentta.render(gc, g);
		this.ppmpkentta.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame peli, int delta)
			throws SlickException {

		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		if(this.oknelio.contains(mouseX, mouseY) 
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			this.oknappiPainettu(gc, peli);
			
		}
		else if(this.poisnelio.contains(mouseX, mouseY) && 
				input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			this.poisnappiPainettu(gc, peli);
		}
		
		else if(this.painonappinelio.contains(mouseX, mouseY) && 
				input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			asetaPainonappiPainettu(!annaPainonappiPainettu());
		}

	}
	
	private void poisnappiPainettu(GameContainer gc, StateBasedGame peli) 
			throws SlickException {
		this.leave(gc, peli);
		peli.enterState(Peli.VALIKKOTILA);
		
	}

	private void oknappiPainettu(GameContainer gc, StateBasedGame peli) 
			throws SlickException {
		 
		luoJaLahetaParametrit(gc, peli);

		if(gc instanceof AppGameContainer) {
			if(annaPainonappiPainettu()) {

				((AppGameContainer) gc).setDisplayMode(gc.getScreenWidth(), gc.getScreenHeight(), true);
				gc.reinit();


			}
			else if(!annaPainonappiPainettu()
					&& gc.getWidth() != 800 
					&& gc.getScreenWidth() != 800) {

				((AppGameContainer) gc).setDisplayMode(800, 600, false);
				gc.reinit();
			}
		}
		this.leave(gc, peli);
		peli.enterState(Peli.PELITILA);
		
	}

	/**
	 * asetusmetodi
	 * @param onko	uusi tila
	 */
	private void asetaPainonappiPainettu(boolean onko) {
		this.painonappiPainettu = onko;
	}
	/**
	 * 
	 * @return	painonappiPainettu arvo
	 */
	private boolean annaPainonappiPainettu() {
		return this.painonappiPainettu;
	}

	/**
	 * 
	 * @param gc
	 * @param peli
	 * @throws SlickException
	 */
	private void luoJaLahetaParametrit(GameContainer gc, StateBasedGame peli) 
			throws SlickException {
		System.out.println("luoJaLahetaParametrit");
		//oletusarvot muuttujiin, jos parseint epäonnistuu
		int tankkimaara;// = this.tankki_oletus; 
		int ammustuho;// = this.tuho_oletus;
		int minimivali;// = this.minimi_oletus;
		int ppmp;// = this.ppmp_oletus;


		try {
		//	System.out.println("111");

			tankkimaara = Integer.parseInt(this.tankkikentta.getText());
			ammustuho = Integer.parseInt(this.tuhokentta.getText());
			minimivali = Integer.parseInt(this.minimivalikentta.getText());	
			ppmp = Integer.parseInt(this.ppmpkentta.getText());

		//	System.out.println("222");
		}
		catch(NumberFormatException ex) {
			
			System.err.println("laiton syöte *******************************");

			tankkimaara = this.tankki_oletus; 
			ammustuho = this.tuho_oletus;
			minimivali = this.minimi_oletus;
			ppmp = this.ppmp_oletus;
		}

		System.out.println("tankki "+tankkimaara +
				" tuho "+ ammustuho +
				" minimi "+ minimivali +
				" ppmp "+ ppmp);

		tankkimaara = tankkiTarkastus(tankkimaara);
		minimivali = valiTarkastus(gc, tankkimaara, minimivali);

		((Pelitila)peli.getState(Peli.PELITILA)).init(gc, peli, new Parametrit(tankkimaara, ammustuho, minimivali, ppmp));
	}
	/**
	 * laillisuustarkastus
	 * @param tankkimaara 	tankkien määrä
	 * @return laillinen tankkimaaran arvo
	 */
	private int tankkiTarkastus(int tankkimaara) {
		if(tankkimaara > 12) {
			tankkimaara = 12;
		}
		else if(tankkimaara < 2) {
			tankkimaara = 2;
		}
		return tankkimaara;
	}
	/**
	 * laillisuustarkastus
	 * @param gc	pelin GameContainer
	 * @param tankkimaara	tankkien määrä
	 * @param minimivali 	tankkien väliin minimissään tuleva pikselimäärä
	 * @return laillinen minimivalin arvo
	 */
	private int valiTarkastus(GameContainer gc, int tankkimaara, int minimivali) {
		if(minimivali < 1) {
			minimivali = 1;
		}

		//varmuuden vuoksi *2.5 vaikka *2 olisi teoriassa maksimi
		int vietyTila = minimivali * tankkimaara * 5 / 2;

		if(vietyTila > gc.getWidth()) {
			minimivali = gc.getWidth()/(tankkimaara * 5 / 2);
		}
		return minimivali;
	}
}