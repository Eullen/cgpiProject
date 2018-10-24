package calculadores;

import java.util.ArrayList;
import java.util.List;

import primitivos.Poligono;
import primitivos.Ponto;
import primitivos.Reta;
import primitivos.Retangulo;

public class PoligonoCalculador {
	
	public static List<Ponto> obterPontos(Poligono poligono) {
		
		List<Ponto> pontos = new ArrayList<Ponto>();
		
		for (Reta reta : poligono.getRetas()) {
			pontos.addAll(RetaCalculador.obterPontos(reta));
		}
		
		return pontos;
		
	}
	
	public static double calcularDistanciaPontoRetasPoligono(Ponto pt, Poligono poligono){
		double distancia = 100000;
		// verifica qual reta mais proxima do ponto 
		for (Reta reta: poligono.getRetas()){
			double distanciaPontoRetaAtual = (RetaCalculador.calcularDistanciaPontoReta(pt, reta));
			distancia =  (distanciaPontoRetaAtual < distancia)? distanciaPontoRetaAtual :distancia;
		}
		return distancia;
	}
}
