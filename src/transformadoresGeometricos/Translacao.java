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
	
	public Translacao(List<Ponto> pontosDaFigura) {
		super();
		this.setPontosDaFigura(pontosDaFigura);
		this.setPontoReferencia(new Ponto(0,0));
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
		this.pontosDaFigura.forEach(pt -> {
			// TODO: Ajustar AQUI
			double x = pt.getx()+10;
			double y = pt.gety()+10;
			pontosTransladados.add(new Ponto(x,y));
		});
		return pontosTransladados;
	}
	
}
