package util;
/**
 * Sailyttaa ja jakaa eteenpain Asetustilan antamat parametrit.
 * Kuten muissakin luokissa, 
 * ppmp == PikseliaPerMaastoPiste
 * @author 290836
 */
public class Parametrit {
	
	private int tankkimaara;
	private int ammustuho;
	private int ppmp;
	private int minimivali;
	
	/**
	 * Luo olion ja asettaa parametrit.
	 * @param tankkimaara	tankkien maara
	 * @param ammustuho		ammuksen tuhovoima
	 * @param minimivali	tankkien minimivali toisistaan
	 * @param ppmp			pikselia per maastopiste eli maaston vaihtelevuus
	 */
	public Parametrit(int tankkimaara, int ammustuho, int minimivali, int ppmp) {
		this.tankkimaara = tankkimaara;
		this.ammustuho = ammustuho;
		this.minimivali = minimivali;
		this.ppmp = ppmp;
	}	
	
	/**
	 * antaa tankkien minimivalin toisistaan
	 * @return	minimivali
	 */
	public int annaMinimivali() {
		return this.minimivali;
	}
	
	/**
	 * antaa tankkien maaran
	 * @return tankkimaara
	 */
	public int annaTankkimaara() {
		return this.tankkimaara;
	}
	
	/**
	 * antaa ammusten tuhovoiman
	 * @return ammustuho
	 */
	public int annaAmmustuho() {
		return this.ammustuho;
	}
	
	/**
	 * antaa pikselia per maastopiste-arvon
	 * @return	ppmp
	 */
	public int annaPikseliaPerMaastoPiste() {
		return this.ppmp;
	}
}
