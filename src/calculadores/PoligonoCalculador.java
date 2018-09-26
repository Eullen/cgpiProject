package calculadores;

import java.util.ArrayList;
import java.util.List;

import primitivos.Poligono;
import primitivos.Ponto;
import primitivos.Reta;

public class PoligonoCalculador {
	
	public static List<Ponto> obterPontos(Poligono poligono) {
		
		List<Ponto> pontos = new ArrayList<>();
		
		for (Reta reta : poligono.getRetas()) {
			pontos.addAll(RetaCalculador.obterPontos(reta));
		}
		
		return pontos;
		
	}
}
