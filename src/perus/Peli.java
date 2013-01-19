package perus;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import util.Musiikki;
/**
 * Tämä on pelin pääluokka. TankWars käyttää Slick2d-kirjastoa ja sen 
 * arkkitehtuuri perustuu Slickin StateBasedGame-luokkaan.
 * Pelissä on siis tiloja (GameState) joiden välillä 
 * voidaan liikkua helpoilla metodikutsuilla. Jokaisessa 
 * tilassa on keskeiset metodit init(), render() ja update().
 * 
 * init() on metodi, joka tekee sen mitä halutaan tehtäväksi 
 * ennen kuin tilaan mennään. Se on siis oikea paikka muuttujien 
 * alustamiselle ynnä muulle.
 * 
 * render()-metodissa tehdään tilan vaatima piirtäminen.
 * 
 * update()-metodissa päivitetään pelin logiikka.
 * 
 * 
 * Renderiä ja updatea kutsutaan pelisilmukassa toistuvasti. 
 * Renderiä kutsutaan joka framella eli ruudunpäivityksellä 
 * ja updatea jonkin verran tätä harvemmin tai useammin 
 * asetuksista riippuen.
 * 
 * 
 * Pelin yleisestä käyttölogiikasta & arkkitehtuurista:
 * 
 * Peli-luokan ajaminen luo GameStatet ja yhden appGameContainerin.
 * Slick ajaa kulissien takana tilaluokkien init()-metodit. 
 * Pelitila-luokassa tämä kutsu ei tee mitään. Syynä on Parametrit-luokan 
 * käyttö, lisää alla.
 * 
 * Pelistä aukeaa ensin Valikkotila: päävalikkonäkymä, jossa on 
 * Start- ja Quit-napit. Start-nappi vie Asetustilaan, jossa voidaan 
 * antaa pelille uusia parametreja.
 * 
 * Asetustilan OK-napin painaminen initialisoi Pelitilan niillä 
 * parametreilla, jotka tekstikentissä ja painonapissa (Fullscreen-checkbox) 
 * on näkyvillä. Pelitilan init() tehdään metodin luoJaLahetaParametrit() 
 * kautta.
 * 
 * Fullscreen-vaihtoehdon muuttaminen eri kuin voimassaolevaksi 
 * ajaa kaikki init()-metodit uudestaan, sillä kuvien koordinaatit ym. 
 * logiikka määräytyvät ikkunan koon mukaan.
 * 
 * Pelitilasta pääsee Pausetilaan painamalla esc-näppäintä. 
 * Pausetilasta pääsee joko jatkamaan peliä siitä mihin se jäi 
 * tai Valikkotilaan, jolloin pelitilanne pyyhkiytyy pois.
 * 
 * 
 * @author Johannes
 *
 */
public class Peli extends StateBasedGame {

	public static final int VALIKKOTILA  = 0;
	public static final int PELITILA = 1;
	public static final int ASETUSTILA = 2;
	public static final int PAUSETILA = 3;
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

		//800*600, ei fullscreen(vielä tässä vaiheessa)
		app.setDisplayMode(800, 600, false); 

		//sopivannopeuksiselle pelin kululle kokeilemalla löydetyt luvut
		//logiikanpäivitysten ero korkeintaan 25 ms, kohde fps 60
		app.setMaximumLogicUpdateInterval(25);
		app.setTargetFrameRate(60);

		//ei fps-lukemaa
		app.setShowFPS(false);

		app.start();
	}



	@Override
	public void initStatesList(GameContainer app) throws SlickException {
		//Tyhjä. Slick ajaa initit kulissien takana silti. 
	}
}
