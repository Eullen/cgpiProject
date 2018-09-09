package calculadores;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import primitivos.Ponto;
import primitivos.PontoGr;
import primitivos.Reta;

public class RetaCalculador {

    public static List<Ponto> obterPontos(Reta reta) {
        double m = reta.getCoeficienteAngular();
        double b = reta.getA().gety() - m * reta.getA().getx();
        int x1 = (int) Math.floor(reta.getA().getx());
        int x2 = (int) Math.floor(reta.getB().getx());
        int y1 = (int) Math.floor(reta.getA().gety());
        int y2 = (int) Math.floor(reta.getB().gety());
        int deltaX = (x1 >= x2) ? Math.abs(x1 - x2) : Math.abs(x2 - x1);
        int deltaY = (y1 >= y2) ? Math.abs(y1 - y2) : Math.abs(y2 - y1);
        List<Ponto> pontos = new ArrayList<>();

        if (x2 < x1) {
            int aux = x1;
            x1 = x2;
            x2 = aux;
        }

        if (y2 < y1) {
            int aux = y1;
            y1 = y2;
            y2 = aux;
        }

        if (deltaX == 0) {
            //TODO: Loop que só anda no y: Linha vertical
            for (int y = y1; y <= y2; y++) {
                pontos.add(new Ponto(x1, y));
            }
        } else if (deltaY == 0) {
            //TODO: Loop que só anda no x: Linha horizontal
            for (int x = x1; x <= x2; x++) {
                pontos.add(new Ponto(x, y1));
            }
        } else if (deltaX > deltaY) {
            //TODO: Loop que usa o x para achar o y
            for (int x = x1; x <= x2; x++) {
                int y_atual = (int) Math.floor(m * x + b);
                pontos.add(new Ponto(x, y_atual));
            }
        } else {
            //TODO: Loop que usa o y para achar o x
            for (int y = y1; y <= y2; y++) {
                int x_atual = (int) Math.floor((y - b) / m);
                pontos.add(new Ponto(x_atual, y));
            }
        }

        return pontos;
    }
    
	public static List<Ponto> obterPontosAlgoritmoMidPoint(Reta reta){
		
		int x1 = (int) Math.floor(reta.getA().getx());
        int x2 = (int) Math.floor(reta.getB().getx());
        int y1 = (int) Math.floor(reta.getA().gety());
        int y2 = (int) Math.floor(reta.getB().gety());
        List<Ponto> pontos = new ArrayList<>();
        
        // Algoritmo Professor adaptado
		int dx, dy, i, e;
		int incx, incy, inc1, inc2;
		int x,y;

		dx = x2-x1;
		dy = y2-y1;

		if (dx < 0) dx = -dx;
		if (dy < 0) dy = -dy;
		incx = 1;
		if (x2 < x1) incx = -1;
		incy = 1;
		if (y2 < y1) incy = -1;
		x = x1;
		y = y1;
		pontos.add(new Ponto(0,0));
		if (dx > dy) {
			pontos.add(new Ponto(x,y));
			e = 2 * dy-dx;
			inc1 = 2*(dy-dx);
			inc2 = 2*dy;
			for (i=0; i<dx; i++) {
				if (e >= 0) {
					y += incy;
					e += inc1;
				}
				else
					e += inc2;
				x += incx;
				pontos.add(new Ponto(x,y));
			}

		} else {
			pontos.add(new Ponto(x,y));
			e = 2*dx-dy;
			inc1 = 2*(dx-dy);
			inc2 = 2*dx;
			for (i=0; i<dy; i++) {
				if (e >= 0) {
					x += incx;
					e += inc1;
				}
				else
					e += inc2;
				y += incy;
				pontos.add(new Ponto(x,y));
			}
		}
		
		return pontos;
	}
}
