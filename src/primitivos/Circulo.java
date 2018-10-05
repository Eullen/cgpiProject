package primitivos;

import javafx.scene.paint.Color;

public class Circulo {
	private int raio;
	private Ponto pontoOrigem;
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
