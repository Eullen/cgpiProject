package calculadores;

import java.util.ArrayList;
import java.util.List;

import com.sun.management.DiagnosticCommandMBean;

import primitivos.Ponto;
import primitivos.Reta;
import primitivos.Retangulo;

public class RetanguloCalculador {

	public static List<Ponto> obterPontos(Retangulo retangulo) {
		return obterPontosDasRetas(obterRetas(retangulo));
	}
	
	
	private static List<Reta> obterRetas (Retangulo retangulo){
		List<Reta> retas = new ArrayList<>();
		//Criando retas que formam o retangulo
		Ponto ptXminYmax = new Ponto(retangulo.getDiagonalMin().getx(), retangulo.getDiagonalMax().gety());
		Ponto ptXmaxYmin = new Ponto(retangulo.getDiagonalMax().getx(), retangulo.getDiagonalMin().gety());
		retas.add(new Reta(retangulo.getDiagonalMin(), ptXminYmax));
		retas.add(new Reta(retangulo.getDiagonalMin(), ptXmaxYmin));
		retas.add(new Reta(retangulo.getDiagonalMax(), ptXminYmax));
		retas.add(new Reta(retangulo.getDiagonalMax(), ptXmaxYmin));	
		return retas;
	}
	
	private static List<Ponto> obterPontosDasRetas(List<Reta> retas) {
		
		List<Ponto> pontos = new ArrayList<>();
		
		for (Reta reta : retas) {
			pontos.addAll(RetaCalculador.obterPontos(reta));
		}
		
		return pontos;
		
	}
	
	private static double calcularDistanciaPontoRetasRetangulo(Ponto pt, Retangulo retangulo){
		
		List<Reta> retas = obterRetas(retangulo);
		double distancia = 100000;
		// verifica qual reta mais proxima do ponto 
		for (Reta reta: retas){
			double distanciaPontoRetaAtual = (RetaCalculador.calcularDistanciaPontoReta(pt, reta));
			distancia =  (distanciaPontoRetaAtual < distancia)? distanciaPontoRetaAtual :distancia;
		}
		return distancia;
	}
}
