package primitivos;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.scene.paint.Color;

@XmlRootElement
public class Circulo {
	@XmlElement
	private int raio;
	@XmlElement
	private Ponto pontoOrigem;
	@XmlElement
	private Color cor;

	public Circulo(int raio, Ponto pontoOrigem, Color cor) {
		this.raio = raio;
		this.pontoOrigem = pontoOrigem;
		this.setCor(cor);
	}

	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}

	public Ponto getPontoOrigem() {
		return pontoOrigem;
	}

	public void setPontoOrigem(Ponto pontoOrigem) {
		this.pontoOrigem = pontoOrigem;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}
}
