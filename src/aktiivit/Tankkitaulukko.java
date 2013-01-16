package aktiivit;



public class Tankkitaulukko {

	Tankki[] taulukko;
	int[] sijoitettujenpaikat;
	private int minimivali;

	public Tankkitaulukko(int tankkimaara, int minimivali) {
		this.minimivali = minimivali;
		taulukko = new Tankki[tankkimaara];
		sijoitettujenpaikat = new int[tankkimaara];
	}

	public void laitaTankki(int indeksi, Tankki tankki) {
		this.taulukko[indeksi] = tankki;
		this.sijoitettujenpaikat[indeksi] = tankki.annaX();
	}
	
	public boolean laillinenPaikka(int x) {
	
		
		//this.minimivali = 200;
	
		int k = 0;
		while(k < sijoitettujenpaikat.length) {
			if(x > this.sijoitettujenpaikat[k] - minimivali 
					&& x < this.sijoitettujenpaikat[k] + minimivali) {
				System.out.println("laitaTankki: false");
				return false;
			}
			k++;
		}
		return true;
	}

	public Tankki annaTankki(int indeksi) {
		return this.taulukko[indeksi];
	}
}
