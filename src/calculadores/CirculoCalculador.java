package calculadores;

import java.util.ArrayList;
import java.util.List;

import primitivos.Circulo;
import primitivos.Ponto;

public class CirculoCalculador {

    public static List<Ponto> obterPontos(Circulo circulo) {
        List<Ponto> pontos = new ArrayList<>();
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

        //TODO: Arrumar
        /*
         * 
         * for (iAngulo = 0; iAngulo <= 45; iAngulo = iAngulo + 0.125) {
			
			dSeno = Math.sin( Math.toRadians(iAngulo));
			dCosseno = Math.cos( Math.toRadians(iAngulo));
			
			dX = new Double( dRaio *  dCosseno).intValue();
			dY = new Double( dRaio * dSeno).intValue();
			
			//Cálculo por octante dos pontos:
			//Ponto(x,y)
			alCoordenadas.add( new int [] {iCentroX + dX, iCentroY + dY} );
			
			//Ponto(-x, y)
			alCoordenadas.add( new int [] {iCentroX - dX, iCentroY + dY} );
			
			//Ponto(x, -y)
			alCoordenadas.add( new int [] {iCentroX + dX, iCentroY - dY} );
			
			//Ponto(-x, -y)
			alCoordenadas.add( new int [] {iCentroX - dX, iCentroY - dY} );
			
			//Ponto (y, x)
			alCoordenadas.add( new int [] {iCentroX + dY, iCentroY + dX} );
			
			//Ponto (-y, x)
			alCoordenadas.add( new int [] {iCentroX - dY, iCentroY + dX} );
			
			//Ponto (y, -x)
			alCoordenadas.add( new int [] {iCentroX + dY, iCentroY - dX} );
			
			//Ponto(-y, -x)
			alCoordenadas.add( new int [] {iCentroX - dY, iCentroY - dX} );
			
		}
		
		return alCoordenadas;
	}
         * */
        
        return pontos;
    }

    public static Integer obterRaio(Ponto inicio, Ponto fim) {
        double equacao = Math.pow((fim.getx() - inicio.getx()), 2) + Math.pow((fim.gety() - inicio.gety()), 2);
        return (int) Math.floor(Math.sqrt(equacao));
    }
}
