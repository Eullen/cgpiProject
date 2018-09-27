package primitivos;

import javafx.scene.paint.Color;

public class Retangulo {
	private Ponto diagonalMin;
	private Ponto diagonalMax;
	private Color cor;
	
	public Retangulo(Ponto diagonalMin, Ponto diagonalMax, Color cor) {
		this.diagonalMin = diagonalMin;
		this.diagonalMax = diagonalMax;
		this.cor = cor;
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
	
	
}
