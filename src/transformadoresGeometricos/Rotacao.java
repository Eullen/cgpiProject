package transformadoresGeometricos;

import java.util.ArrayList;
import java.util.List;

import primitivos.Ponto;

public class Rotacao {
	private List<Ponto> pontosDaFigura;
	private Ponto pontoReferencia;
	private int angulo;
	
	public Rotacao(List<Ponto> pontosDaFigura, Ponto pontoReferencia, int angulo) {
		super();
		this.setPontosDaFigura(pontosDaFigura);
		this.setPontoReferencia(pontoReferencia);
		this.setAngulo(angulo);
	}
	

	public Rotacao(List<Ponto> pontosDaFigura, int angulo) {
		super();
		this.setPontosDaFigura(pontosDaFigura);
		this.setPontoReferencia(new Ponto(0,0));
		this.setAngulo(angulo);
	}
	
	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

	public Ponto getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(Ponto pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public List<Ponto> getPontosDaFigura() {
		return pontosDaFigura;
	}

	public void setPontosDaFigura(List<Ponto> pontosDaFigura) {
		this.pontosDaFigura = pontosDaFigura;
	}
	
	public List<Ponto> aplicarRotacao(){
		List<Ponto> pontosTransladados = new ArrayList<>();
		this.pontosDaFigura.forEach(pt -> {	
			double x = pt.getx();
			double y = pt.gety();
			pontosTransladados.add(new Ponto(x,y));
		});
		return pontosTransladados;
	}
}
