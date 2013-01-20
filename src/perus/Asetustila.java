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
import org.newdawn.slick.util.ResourceLoader;

import util.Parametrit;

/**
 * GameState-luokka start-napin painamisen 
 * ja pelin alkamisen valiselle parametrienkysymisnakymalle.
 * 
 * Luokassa "painonappi" tarkoittaa fullscreenin checkboxia.
 * 
 * @author 290836
 *
 */
public class Asetustila extends BasicGameState {

	private int id;

	/**
	 * Apumittoja ja -koordinaatteja
	 */
	private int okx;
	private int oky;
	private int poisx;
	private int poisy;
	private int painonappix;
	private int painonappiy;
	private int ruudunkorkeus;
	private int ruudunleveys;

	/**
	 * Nappimuuttujat
	 */
	private Image oknappi;
	private Image poisnappi;
	private Image painonappi_normi;
	private Image painonappi_pohjassa;

	/**
	 * Nappien neliot
	 */
	private Rectangle oknelio;
	private Rectangle poisnelio;
	private Rectangle painonappinelio;

	/**
	 * Tekstikentat
	 */
	private TextField tankkikentta;
	private TextField tuhokentta;
	private TextField minimivalikentta;
	private TextField ppmpkentta;

	/**
	 * Oletusvakiot
	 */
	private final int tankki_oletus = 2;
	private final int tuho_oletus = 50;
	private final int minimi_oletus = 150;
	private final int ppmp_oletus = 10;

	private boolean painonappiPainettu;	
	private Tausta tausta;

	/**
	 * @param id	GameStaten id-numero
	 */
	public Asetustila(int id) {
		this.id = id;
	}

	private String annaEsittely() {
		return "Tank Count\n\n" +
				"Ammo Damage\n\n" +
				"Minimum Tank Distance\n\n" +
				"Pixels Per Terrain Point\n\n\n\n" +
				"Fullscreen";
	}

	private String annaOhjeet() {
		return "Arrow Keys: Angle and Power" +
				"          " +
				"ENTER: Shoot\n" +
				"ESC: Pause Menu"+
				"                      "+
				"SHIFT: Faster Adjustments";
	}

	@Override
	public int getID() {
		return this.id;
	}

	/**
	 * laillisuustarkastus
	 * @param tankkimaara 	tankkien maara
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
	 * @param tankkimaara	tankkien maara
	 * @param minimivali 	tankkien valiin minimissaan tuleva x-suuntainen pikselimaara
	 * @return laillinen minimivalin arvo
	 */
	private int minimiValiTarkastus(GameContainer gc, int tankkimaara, int minimivali) {
		if(minimivali < 1) {
			minimivali = 1;
		}

		//varmuuden vuoksi *2.5 vaikka *2 olisi teoriassa maksimi
		float vietyTila = minimivali * tankkimaara * 5 / 2;

		if(vietyTila > gc.getWidth()) {
			minimivali = Math.round(gc.getWidth()/(tankkimaara * 5 / 2));
		}
		return minimivali;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame peli)
			throws SlickException {

		//System.out.println("asetustila init"); 

		//tallennetaan mitat muuttujiin
		this.ruudunkorkeus = gc.getHeight();
		this.ruudunleveys = gc.getWidth();

		//kenttien asetteluun apumuuttujat
		int kenttax = ruudunleveys/2+100;
		int	kenttay = ruudunkorkeus/6;	

		//kaytetaan oletusfonttia
		Font fontti = gc.getDefaultFont();

		//tavallinen tausta
		this.tausta = new Tausta(gc);

		//haetaan kuvat napeille
		this.oknappi = new Image(
				ResourceLoader.getResource("res/ok.png").getPath());
		this.poisnappi = new Image(
				ResourceLoader.getResource("res/mainmenu.png").getPath());
		this.painonappi_normi = new Image(
				ResourceLoader.getResource("res/checkbox.png").getPath());
		this.painonappi_pohjassa = new Image(
				ResourceLoader.getResource("res/checkbox_checked.png").getPath());

		//asetetaan nappien x:t
		this.okx = this.ruudunleveys/3-this.oknappi.getWidth();
		this.poisx = (this.ruudunleveys/3)*2;
		this.painonappix = kenttax;

		//asetetaan nappien y:t
		this.oky = this.ruudunkorkeus-this.oknappi.getHeight()-30;			
		this.poisy = this.ruudunkorkeus-this.poisnappi.getHeight()-30;
		this.painonappiy = kenttay+180;

		//tehdaan nappeja vastaavat neliot hiirentarkkailua varten
		this.oknelio = new Rectangle(
				this.okx, 
				this.oky, 
				this.oknappi.getWidth(), 
				this.oknappi.getHeight());

		//main menu-napille nelio
		this.poisnelio = new Rectangle(
				this.poisx,
				this.poisy, 
				this.poisnappi.getWidth(), 
				this.poisnappi.getHeight());		

		//checkboxille nelio
		this.painonappinelio = new Rectangle(
				this.painonappix, 
				this.painonappiy, 
				this.painonappi_normi.getWidth(), 
				this.painonappi_pohjassa.getHeight());

		/* Luodaan tekstikentat.
		 * konstruktori
		 * TextField(GUIContext container, Font font, int x, int y, int width, int height)
		 * nullcheckit, jotta kentat toimisivat myos reinitin jalkeen
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

		//asetetaan kentat paikoilleen
		this.tankkikentta.setLocation(kenttax, kenttay);
		this.tuhokentta.setLocation(kenttax, kenttay+40);
		this.minimivalikentta.setLocation(kenttax, kenttay+80);
		this.ppmpkentta.setLocation(kenttax, kenttay+120);

		//oletusarvot kenttiin
		this.tankkikentta.setText(""+this.tankki_oletus);
		this.tuhokentta.setText(""+this.tuho_oletus);
		this.minimivalikentta.setText(""+this.minimi_oletus);
		this.ppmpkentta.setText(""+this.ppmp_oletus);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame peli, Graphics g)
			throws SlickException {

		//taytetaan tausta
		g.fill(this.tausta, this.tausta.annaTaustanGradient());

		//piirretaan napit
		g.drawImage(this.oknappi, this.okx, this.oky);
		g.drawImage(this.poisnappi, this.poisx, this.poisy);

		//valitaan kumpi kuva piirretaan
		if(this.annaPainonappiPainettu()) {
			g.drawImage(this.painonappi_pohjassa, 
					this.painonappix, this.painonappiy);
		}
		else {
			g.drawImage(this.painonappi_normi, 
					this.painonappix, this.painonappiy);
		}

		//piirretaan tekstit
		g.drawString(this.annaEsittely(),
				this.ruudunleveys/2-200, this.ruudunkorkeus/6);

		g.drawString(this.annaOhjeet(), 
				this.ruudunleveys/2-300, this.ruudunkorkeus/3*2-20);

		//piirretaan tekstikentat
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

		//napinpainaminen.
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

			//vaihtaa toiseksi
			this.asetaPainonappiPainettu(!this.annaPainonappiPainettu());
		}
	}
	/**
	 * Suorittaa main menu-nappia painettaessa tapahtuvat asiat.
	 * @param gc	pelin GameContainer
	 * @param peli	Peli-olio
	 * @throws SlickException
	 */
	private void poisnappiPainettu(GameContainer gc, StateBasedGame peli) 
			throws SlickException {
		this.leave(gc, peli);
		peli.enterState(Peli.VALIKKOTILA);

	}

	/**
	 * Suorittaa ok-nappia painettaessa tapahtuvat asiat.
	 * @param gc	pelin GameContainer
	 * @param peli	Peli-olio
	 * @throws SlickException
	 */
	private void oknappiPainettu(GameContainer gc, StateBasedGame peli) 
			throws SlickException {

		luoJaLahetaParametrit(gc, peli);

		if(gc instanceof AppGameContainer) {
			if(annaPainonappiPainettu()) {
				//fullscreen.
				((AppGameContainer) gc).setDisplayMode(
						gc.getScreenWidth(), 
						gc.getScreenHeight(), true);
				
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
	 * Asetusmetodi.
	 * @param onko	uusi tila
	 */
	private void asetaPainonappiPainettu(boolean onko) {
		this.painonappiPainettu = onko;
	}
	/**
	 * 
	 * @return	painonappiPainettu-arvo
	 */
	private boolean annaPainonappiPainettu() {
		return this.painonappiPainettu;
	}

	/**
	 * 
	 * @param gc	pelin GameContainer
	 * @param peli	peli itse
	 * @throws SlickException
	 */
	private void luoJaLahetaParametrit(GameContainer gc, StateBasedGame peli) 
			throws SlickException {

		//System.out.println("luoJaLahetaParametrit");


		//oletusarvot muuttujiin, jos parseInt epaonnistuu
		int tankkimaara = this.tankki_oletus; 
		int ammustuho = this.tuho_oletus;
		int minimivali = this.minimi_oletus;
		int ppmp = this.ppmp_oletus;

		try {
			tankkimaara = Integer.parseInt(this.tankkikentta.getText());
			ammustuho = Integer.parseInt(this.tuhokentta.getText());
			minimivali = Integer.parseInt(this.minimivalikentta.getText());	
			ppmp = Integer.parseInt(this.ppmpkentta.getText());
		}
		catch(NumberFormatException ex) {
			System.err.println("laiton syote *******************************");
		}

		/*
		System.out.println("tankki "+tankkimaara +
				" tuho "+ ammustuho +
				" minimi "+ minimivali +
				" ppmp "+ ppmp);
		 */

		tankkimaara = this.tankkiTarkastus(tankkimaara);
		minimivali = this.minimiValiTarkastus(gc, tankkimaara, minimivali);

		((Pelitila)peli.getState(Peli.PELITILA)).init(gc, peli, new Parametrit(tankkimaara, ammustuho, minimivali, ppmp));
	}
	
}