package transformadoresGeometricos;

import java.util.ArrayList;
import java.util.List;

import primitivos.Ponto;

public class Rotacao {
	private List<Ponto> pontosDaFigura;
	private Ponto pontoReferencia;
	private int angulo;
	
	public Rotacao(Ponto pontoReferencia, List<Ponto> pontosDaFigura, int angulo) {
		super();
		this.setPontosDaFigura(pontosDaFigura);
		this.setPontoReferencia(pontoReferencia);
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
		
		//Rotacionar figura
//		x’= xR + (x-xR)cosA - (y-yR)senA
//		y’= yR + (x-xR)senA + (y-yR)cosA
//		Onde A é o angulo de rotação
		
		this.pontosDaFigura.forEach(pt -> {	
			double x = pontoReferencia.getx() 
						+ ((pt.getx()- pontoReferencia.getx())*Math.cos(angulo))
						- ((pt.gety() - pontoReferencia.gety())*Math.sin(angulo));
			double y = pontoReferencia.gety() 
					+ ((pt.getx()- pontoReferencia.getx())*Math.sin(angulo))
					- ((pt.gety() - pontoReferencia.gety())*Math.cos(angulo));
			pontosTransladados.add(new Ponto(x,y));
		});
		return pontosTransladados;
	}
}
