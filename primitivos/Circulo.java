package primitivos;

public class Circulo {
    private int raio;
    private Ponto pontoOrigem;

    public Circulo(int raio, Ponto pontoOrigem){
        this.raio = raio;
        this.pontoOrigem = pontoOrigem;
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
}
