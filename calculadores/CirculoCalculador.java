package calculadores;

import java.util.ArrayList;
import java.util.List;

import primitivos.Circulo;
import primitivos.Ponto;

public class CirculoCalculador{

	public List<Ponto> obterPontos(Circulo circulo) {
		List<Ponto> pontos 		= new ArrayList<>();
		final int raio     		= circulo.getRaio();
		final Ponto pontoOrigem = circulo.getPontoOrigem();
		
		for(double i = 1; i <= 360; i++){
			int x = (int)(raio * Math.cos(i)); 
			int y = (int)(raio * Math.sin(i));
			x += pontoOrigem.getx();
			y += pontoOrigem.gety();
			pontos.add(new Ponto(x, y));
		}
		
		return pontos;
	}
	
	public Integer obterRaio(Ponto inicio, Ponto fim){
		double equacao = Math.pow((fim.getx() - inicio.getx()),2) + Math.pow((fim.gety() - inicio.getx()),2);
		return (int) Math.sqrt(equacao); 
	}
}
