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
		int y1      = (int) Math.floor(reta.getA().gety());
		int y2      = (int) Math.floor(reta.getB().gety());
		int y_atual = 0;
		int deltaX  = (x1 >= x2 ) ? Math.abs(x1-x2) : Math.abs(x2-x1);
		int deltaY	=  (y1 >= y2) ? Math.abs(y1-y2) : Math.abs(y2-y1);
		List<Ponto> pontos = new ArrayList<>();

		for(int x = x1; x <= x2; x++){
			y_atual = (int) Math.floor(m * x + b);
			pontos.add(new Ponto(x, y_atual));
		}

		if (deltaX == 0 ){
			//TODO: Loop que só anda no y
		}
		else if (deltaY == 0){
			//TODO: Loop que só anda no x
		}
		else if (deltaX > deltaY){
			//TODO: Loop que usa o x para achar o y
		}
		else{
			//TODO: Loop que usa o y para achar o x
		}

		// TODO: Logica Daniel
		/*
		* Verifica se x1=x2
		* 	Loop diferente	=, anda só no y
		* Depois se y1=y2
		* 	Loop diferente, anda só no x
		*
		* Define inicial, define final
		* Anda no delta maior
		*
		* Vários loops ou um loop parametrizável
		*
		* */
		
		return pontos;
	}
	
	
}
