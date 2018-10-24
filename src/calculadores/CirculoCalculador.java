package calculadores;

import java.util.ArrayList;
import java.util.List;
import primitivos.Circulo;
import primitivos.Ponto;

@SuppressWarnings("restriction")
public class CirculoCalculador {

	public static List<Ponto> obterPontos(Circulo circulo) {
		List<Ponto> pontos = new ArrayList<Ponto>();
		final int raio = circulo.getRaio();
		final Ponto pontoOrigem = circulo.getPontoOrigem();

		for (double i = 0; i <= 360; i++) {
			double angleInRadian = Math.toRadians(i);
			int x = (int) (raio * Math.cos(angleInRadian));
			int y = (int) (raio * Math.sin(angleInRadian));
			x += pontoOrigem.getx();
			y += pontoOrigem.gety();
			pontos.add(new Ponto(x, y));
		}

		return pontos;
	}

	public static Integer obterRaio(Ponto inicio, Ponto fim) {
		double equacao = Math.pow((fim.getx() - inicio.getx()), 2) + Math.pow((fim.gety() - inicio.gety()), 2);
		return (int) Math.floor(Math.sqrt(equacao));
	}

	public static List<Ponto> obterPontosAlgoritmoMidPoint(Circulo circulo) {
		List<Ponto> pontos = new ArrayList<Ponto>();

		if (circulo.getRaio() != 0) {

			int x = 0;
			int y = circulo.getRaio();
			double d = 5 / 4 - circulo.getRaio();
			gerarPontosPorSimetria(pontos, circulo.getPontoOrigem(), new Ponto(x, y));

			while (y > x) {
				if (d < 0) {
					d = d + 2 * x + 3;
					x++;
				}

				else {
					d = d + 2 * (x - y) + 5;
					x++;
					y--;
				}
				gerarPontosPorSimetria(pontos, circulo.getPontoOrigem(), new Ponto(x, y));
			}
		}

		return pontos;
	}

	// desenha os pontos passados pelo Bresenham para cada 1/8 do circulo
	private static void gerarPontosPorSimetria(List<Ponto> pontos, Ponto pontoCentro, Ponto pontoB) {

		int x0 = (int) Math.floor(pontoCentro.getx());
		int x = (int) Math.floor(pontoB.getx());
		int y0 = (int) Math.floor(pontoCentro.gety());
		int y = (int) Math.floor(pontoB.gety());

		pontos.add(new Ponto(x0 + x, y0 + y));
		pontos.add(new Ponto(x0 + y, y0 + x));
		pontos.add(new Ponto(x0 + y, y0 - x));
		pontos.add(new Ponto(x0 + x, y0 - y));
		pontos.add(new Ponto(x0 - x, y0 - y));
		pontos.add(new Ponto(x0 - y, y0 - x));
		pontos.add(new Ponto(x0 - y, y0 + x));
		pontos.add(new Ponto(x0 - x, y0 + y));
	}
	
	public static double calcularDistanciaPontoCirculo(Ponto pt, Circulo circulo){

		double distanciaPontoCentroCirculo = CalculadorGenerico.obterDistanciaEntreDoisPontos(pt,circulo.getPontoOrigem());
		
		return Math.abs(distanciaPontoCentroCirculo - circulo.getRaio());
	}
}
