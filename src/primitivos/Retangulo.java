package primitivos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.scene.paint.Color;

@XmlRootElement
public class Retangulo {
	@XmlElement
	private Ponto diagonalMin;
	@XmlElement
	private Ponto diagonalMax;
	@XmlAttribute
	private Color cor;
	
	public Retangulo(Ponto diagonalMin, Ponto diagonalMax, Color cor) {
		this.diagonalMin = diagonalMin;
		this.diagonalMax = diagonalMax;
		this.setCor(cor);
	}

	public Ponto getDiagonalMin() {
		return diagonalMin;
	}
	
	public void setDiagonalMin(Ponto diagonalMin) {
		this.diagonalMin = diagonalMin;
	}
	
	public Ponto getDiagonalMax() {
		return diagonalMax;
	}
	
	public void setDiagonalMax(Ponto diagonalMax) {
		this.diagonalMax = diagonalMax;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	
}
