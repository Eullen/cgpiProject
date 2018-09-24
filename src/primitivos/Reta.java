/**
 *
 */
package primitivos;

/**
 * @author ra00170556
 */

public class Reta {

	Ponto a;
	Ponto b;
	Double coeficienteAngular;

	public Reta(Ponto a, Ponto b) {
		super();
		this.a = a;
		this.b = b;
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
