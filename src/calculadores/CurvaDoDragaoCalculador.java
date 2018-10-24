package calculadores;

import java.util.ArrayList;
import java.util.List;

import primitivos.Ponto;
import primitivos.Reta;

public class CurvaDoDragaoCalculador {

	private List<Reta> retasCurva = new ArrayList<Reta>();
	private Reta retaInicial;
	private int iteracoes;

	public CurvaDoDragaoCalculador(Reta retaInicial, int iteracoes) {
		this.retaInicial = retaInicial;
		this.iteracoes = iteracoes;
		retasCurva.add(retaInicial);
	}

	private void obterRetas(Reta reta, int iteracoesT) {

		if (iteracoesT > 0) {
			int x1 = (int) Math.floor(reta.getA().getx());
			int x2 = (int) Math.floor(reta.getB().getx());
			int y1 = (int) Math.floor(reta.getA().gety());
			int y2 = (int) Math.floor(reta.getB().gety());

			int xn = ((x1 + x2) / 2) + ((y2 - y1) / 2);
			int yn = ((y1 + y2) / 2) - ((x2 - x1) / 2);

			Reta retaA = new Reta(new Ponto(x2, y2), new Ponto(xn, yn));
			Reta retaB = new Reta(new Ponto(x1, y1), new Ponto(xn, yn));

			retasCurva.remove(reta);
			obterRetas(retaA, iteracoesT - 1);
			obterRetas(retaB, iteracoesT - 1);

		} else {
			retasCurva.add(reta);
		}
	}

	public List<Reta> getRetasCurva() {
		obterRetas(retaInicial, iteracoes);
		return this.retasCurva;
	}

}
