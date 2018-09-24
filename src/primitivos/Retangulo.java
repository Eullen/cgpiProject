package primitivos;

public class Retangulo {
	Ponto DiagonalMin;
	Ponto DiagonalMax;
	
	public Retangulo(Ponto diagonalMin, Ponto diagonalMax) {
		DiagonalMin = diagonalMin;
		DiagonalMax = diagonalMax;
	}

	public Ponto getDiagonalMin() {
		return DiagonalMin;
	}
	
	public void setDiagonalMin(Ponto diagonalMin) {
		DiagonalMin = diagonalMin;
	}
	
	public Ponto getDiagonalMax() {
		return DiagonalMax;
	}
	
	public void setDiagonalMax(Ponto diagonalMax) {
		DiagonalMax = diagonalMax;
	}
	
	
}
