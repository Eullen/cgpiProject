package primitivos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.geometry.Point2D;

@XmlRootElement
public class Ponto extends Point2D {
	
	
	Ponto() {
		super(0, 0);
	}

	public Ponto(double x, double y) {
		super(x, y);
	}

	Ponto(Ponto p) {
		super(p.getx(), p.gety());
	}

	@XmlElement(name="x")
	public double getx() {
		return getX();
	}
	
	@XmlElement(name="y")
	public double gety() {
		return getY();
	}

	public double calcularDistancia(Ponto p) {
		return distance(p);
	}

	public double calcularDistancia(double x, double y) {
		return distance(x, y);
	}
}