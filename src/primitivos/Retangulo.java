package primitivos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javafx.scene.paint.Color;

import java.io.Serializable;


@XmlRootElement(name = "Retangulo")
public class Retangulo implements Serializable {
	private Ponto diagonalMin;
	private Ponto diagonalMax;
	private Color cor;
	public Retangulo() {}

	public Retangulo(Ponto diagonalMin, Ponto diagonalMax, Color cor) {
		this.diagonalMin = diagonalMin;
		this.diagonalMax = diagonalMax;
		this.setCor(cor);
	}

	@XmlElement(name = "Ponto")
	public Ponto getDiagonalMin() {
		return diagonalMin;
	}

	public void setDiagonalMin(Ponto diagonalMin) {
		this.diagonalMin = diagonalMin;
	}

	@XmlElement(name = "Ponto")
	public Ponto getDiagonalMax() {
		return diagonalMax;
	}

	public void setDiagonalMax(Ponto diagonalMax) {
		this.diagonalMax = diagonalMax;
	}

	@XmlTransient
	public Color getCor(){
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
		this.cor = cor.toColor();
	}
	
}
