package primitivos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import gui.TelaPrincipal;
import javafx.scene.paint.Color;

import java.io.Serializable;

@XmlRootElement(name = "Circulo")
public class Circulo implements Serializable {
	private int raio;
	private Ponto pontoOrigem;
	private Color cor;

	public Circulo(){}
	public Circulo(int raio, Ponto pontoOrigem, Color cor) {
		this.raio = raio;
		this.pontoOrigem = pontoOrigem;
		this.setCor(cor);
	}

	@XmlTransient
	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}
	
	@XmlElement(name = "Raio")
	public double getRaioNormalizado() {
		return (this.raio + 0.0) / TelaPrincipal.LARGURA_CANVAS;
	}

	public void setRaioNormalizado(double raioNormalizado) {
		this.raio = (int) (raioNormalizado*TelaPrincipal.LARGURA_CANVAS);
	}

	@XmlElement(name = "Ponto")
	public Ponto getPontoOrigem() {
		return pontoOrigem;
	}

	public void setPontoOrigem(Ponto pontoOrigem) {
		this.pontoOrigem = pontoOrigem;
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
		this.cor = cor.toColor();
	}
}
