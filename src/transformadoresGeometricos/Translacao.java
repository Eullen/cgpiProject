package transformadoresGeometricos;

import java.util.ArrayList;
import java.util.List;

import primitivos.Ponto;

public class Translacao {
	
	private List<Ponto> pontosDaFigura;
	private Ponto pontoReferencia;
	
	public Translacao(List<Ponto> pontosDaFigura, Ponto pontoReferencia) {
		super();
		this.setPontosDaFigura(pontosDaFigura);
		this.setPontoReferencia(pontoReferencia);
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
	
	public List<Ponto> aplicarTranslacao(){
		List<Ponto> pontosTransladados = new ArrayList<>();
		Ponto ptRefCalculo = pontosDaFigura.get(0);
		double tx = pontoReferencia.getx() - ptRefCalculo.getx();
		double ty = pontoReferencia.gety() - ptRefCalculo.gety();;
		this.pontosDaFigura.forEach(pt -> {
			//Referência
//			x’=x+tx, 
//			y’= y+ty
			double x = pt.getx()+tx;
			double y = pt.gety()+ty;
			pontosTransladados.add(new Ponto(x,y));
		});
		return pontosTransladados;
	}
	
}
