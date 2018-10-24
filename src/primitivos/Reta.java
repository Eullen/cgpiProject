/**
 *
 */
package primitivos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javafx.scene.paint.Color;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @author ra00170556
 */

@SuppressWarnings("restriction")
@XmlRootElement(name = "Reta")
public class Reta implements Serializable {
	private Ponto a;
	private Ponto b;
	private Double coeficienteAngular;
	private Color cor;

	public Reta(){
		a = new Ponto();
		b = new Ponto();
	}

	@XmlTransient
	public Color getCor() {
		return cor;
	}
	public void setCor(Color cor) {
		this.cor = cor;
	}


	@XmlElement(name = "Cor")
	public Cor getCustomColor(){
		return new Cor(this.cor.getRed(), this.cor.getGreen(), this.cor.getBlue());
	}
	public void setCustomColor(Cor cor){
		this.cor = new Color(cor.r, cor.g, cor.b,1);
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

	@XmlElement(name = "Ponto")
	public Ponto getA() {
		return a;
	}

	public void setA(Ponto a) {
		this.a = a;
		this.calcularCoeficienteAngular(this.a, this.b);
	}

	@XmlElement(name = "Ponto")
	public Ponto getB() {
		return b;
	}

	public void setB(Ponto b) {
		this.b = b;
		this.calcularCoeficienteAngular(this.a, this.b);
	}

	@XmlElement(name = "CoeficienteAngular")
	public Double getCoeficienteAngular() {
		return coeficienteAngular;
	}

	public void setCoeficienteAngular(Double coeficienteAngular) {
		this.coeficienteAngular = coeficienteAngular;
	}
}
