package calculadores;

import java.util.ArrayList;
import java.util.List;

import primitivos.Circulo;
import primitivos.Ponto;

public class CirculoCalculador {

    public List<Ponto> obterPontos(Circulo circulo) {
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

        return pontos;
    }

    public Integer obterRaio(Ponto inicio, Ponto fim) {
        double equacao = Math.pow((fim.getx() - inicio.getx()), 2) + Math.pow((fim.gety() - inicio.gety()), 2);
        return (int) Math.floor(Math.sqrt(equacao));
    }
}
