package calculadores;

import primitivos.Ponto;

public class CalculadorGenerico {
    
	public static Ponto obterPontoMedio(Ponto pontoInicial, Ponto pontoFinal) {
        int xMedio = (int) ((pontoFinal.getx() + pontoInicial.getx()) / 2);
        int yMedio = (int) ((pontoFinal.gety() + pontoInicial.gety()) / 2);
        Ponto pontoMedio = new Ponto(xMedio, yMedio);
        return pontoMedio;
    }
}
