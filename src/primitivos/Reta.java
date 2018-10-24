/**
 *
 */
package primitivos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.scene.paint.Color;

/**
 * @author ra00170556
 */

@SuppressWarnings("restriction")
@XmlRootElement
public class Reta {
	@XmlElement
	private Ponto a;
	@XmlElement
	private Ponto b;
	@XmlElement
	private Double coeficienteAngular;
	@XmlElement
	private Color cor;

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public Reta(Ponto a, Ponto b) {
		super();
		this.a = a;
		this.b = b;
		this.cor = null;
		this.calcularCoeficienteAngular(a, b);
	}
	
	public Reta(Ponto a, Ponto b, Color cor) {
		super();
		this.a = a;
		this.b = b;
		this.cor = cor;
		this.calcularCoeficienteAngular(a, b);
	}
	
	

	private void calcularCoeficienteAngular(Ponto a, Ponto b) {
		// TODO: Implementar : a.getx() - b.getx() != 0
		this.coeficienteAngular = (a.gety() - b.gety()) / (a.getx() - b.getx());
	}

	public Ponto getA() {
		return a;
	}

	public void setA(Ponto a) {
		this.a = a;
		this.calcularCoeficienteAngular(this.a, this.b);
	}

	public Ponto getB() {
		return b;
	}

	public void setB(Ponto b) {
		this.b = b;
		this.calcularCoeficienteAngular(this.a, this.b);
	}

	public Double getCoeficienteAngular() {
		return coeficienteAngular;
	}

	public void setCoeficienteAngular(Double coeficienteAngular) {
		this.coeficienteAngular = coeficienteAngular;
	}
}
