package perus;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import util.Musiikki;
/**
 * Tama on pelin paaluokka. TankWars kayttaa Slick2d-kirjastoa ja sen 
 * arkkitehtuuri perustuu Slickin StateBasedGame-luokkaan.
 * Pelissa on siis tiloja (GameState) joiden valilla 
 * voidaan liikkua helpoilla metodikutsuilla. Jokaisessa 
 * tilassa on keskeiset metodit init(), render() ja update().
 * 
 * init() on metodi, joka tekee sen mita halutaan tehtavaksi 
 * ennen kuin tilaan mennaan. Se on siis oikea paikka muuttujien 
 * alustamiselle ynna muulle.
 * 
 * render()-metodissa tehdaan tilan vaatima piirtaminen.
 * 
 * update()-metodissa paivitetaan pelin logiikka.
 * 
 * 
 * Renderia ja updatea kutsutaan pelisilmukassa toistuvasti. 
 * Renderia kutsutaan joka framella eli ruudunpaivityksella 
 * ja updatea jonkin verran tata harvemmin tai useammin 
 * asetuksista riippuen.
 * 
 * 
 * Pelin yleisesta kayttologiikasta & arkkitehtuurista:
 * 
 * Peli-luokan ajaminen luo GameStatet ja yhden appGameContainerin.
 * Slick ajaa kulissien takana tilaluokkien init()-metodit. 
 * Pelitila-luokassa tama kutsu ei tee mitaan. Syyna on Parametrit-luokan 
 * kaytto, lisaa alla.
 * 
 * Pelista aukeaa ensin Valikkotila: paavalikkonakyma, jossa on 
 * Start- ja Quit-napit. Start-nappi vie Asetustilaan, jossa voidaan 
 * antaa pelille uusia parametreja.
 * 
 * Asetustilan OK-napin painaminen initialisoi Pelitilan niilla 
 * parametreilla, jotka tekstikentissa ja painonapissa (Fullscreen-checkbox) 
 * on nakyvilla. Pelitilan init() tehdaan metodin luoJaLahetaParametrit() 
 * kautta.
 * 
 * Fullscreen-vaihtoehdon muuttaminen eri kuin voimassaolevaksi 
 * ajaa kaikki init()-metodit uudestaan, silla kuvien koordinaatit ym. 
 * logiikka maaraytyvat ikkunan koon mukaan.
 * 
 * Pelitilasta paasee Pausetilaan painamalla esc-nappainta. 
 * Pausetilasta paasee joko jatkamaan pelia siita mihin se jai 
 * tai Valikkotilaan, jolloin pelitilanne pyyhkiytyy pois.
 * 
 * 
 * @author 290836
 *
 */
public class Peli extends StateBasedGame {

	/**
	 * Tilan id-numero
	 */
	public static final int VALIKKOTILA  = 0;
	/**
	 * Tilan id-numero
	 */
	public static final int PELITILA = 1;
	/**
	 * Tilan id-numero
	 */
	public static final int ASETUSTILA = 2;
	/**
	 * Tilan id-numero
	 */
	public static final int PAUSETILA = 3;
	/**
	 * Tilan id-numero
	 */
	public static final int CREDITSTILA = 4;

	/**
	 * @throws SlickException
	 */
	public Peli() throws SlickException  {
		super("TankWars");

		new Thread(new Musiikki()).start();

		this.addState(new Valikkotila(VALIKKOTILA));
		this.addState(new Pelitila(PELITILA));
		this.addState(new Asetustila(ASETUSTILA));
		this.addState(new Pausetila(PAUSETILA));
		this.addState(new Creditstila(CREDITSTILA));

		this.enterState(VALIKKOTILA);
	}

	/**
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {		

		AppGameContainer app = new AppGameContainer(new Peli());

		//800*600, ei fullscreen(viela tassa vaiheessa)
		app.setDisplayMode(800, 600, false); 

		//sopivannopeuksiselle pelin kululle kokeilemalla loydetyt luvut
		//logiikanpaivitysten ero korkeintaan 25 ms, kohde fps 60
		app.setMaximumLogicUpdateInterval(25);
		app.setTargetFrameRate(60);

		//ei fps-lukemaa
		app.setShowFPS(false);

		app.start();
	}



	@Override
	public void initStatesList(GameContainer app) throws SlickException {
		//Tyhja. Slick ajaa initit kulissien takana silti. 
	}
}
