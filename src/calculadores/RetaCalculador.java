package calculadores;

import java.util.ArrayList;
import java.util.List;
import primitivos.Ponto;
import primitivos.Reta;

public class RetaCalculador {
	
	public static List<Ponto> obterPontos(Reta reta){
		double m    = reta.getCoeficienteAngular();
		double b    = reta.getA().gety() - m * reta.getA().getx();
		int x1      = (int) Math.floor(reta.getA().getx());
		int x2      = (int) Math.floor(reta.getB().getx());
		int y_atual = 0;
		
		List<Ponto> pontos = new ArrayList<>();
		
		for(int x = x1; x <= x2; x++){
			y_atual = (int) Math.floor(m * x + b);
			pontos.add(new Ponto(x, y_atual));
		}
		
		return pontos;
	}
	
	
}
