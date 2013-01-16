package aktiivit;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Polygon;

@SuppressWarnings("serial")
public class Tuulinuoli extends Polygon {

	private Random kone = new Random();
	private int tuulenSuuruus;
	private int x;
	private int y;
	

	public Tuulinuoli(GameContainer gc) {

		this.x = gc.getWidth()/2;	
		this.y = gc.getHeight()/24;
		
		arvoSuuruus();

		this.addPoint(this.x, this.y-15);
		this.addPoint(this.x, this.y+15);
		this.addPoint(this.x+this.annaSuuruus(), this.y);
	
		System.out.println("TuulenSuuruus : "+this.annaSuuruus());
	
		
	}

	private void arvoSuuruus() {
		this.tuulenSuuruus = kone.nextInt(101)-50;
	}

	public int annaSuuruus() {
		return this.tuulenSuuruus;
	}

	public int annaX() {
		return this.x;
	}
	public int annaY() {
		return this.y;
	}

	public static void main(String[] args) {

	}

}
