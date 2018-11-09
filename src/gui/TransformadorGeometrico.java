package gui;

import java.util.ArrayList;
import java.util.List;

import calculadores.CirculoCalculador;
import calculadores.PoligonoCalculador;
import calculadores.RetaCalculador;
import calculadores.RetanguloCalculador;
import controladores.TipoDesenho;
import controladores.TipoPrimitivo;
import primitivos.Circulo;
import primitivos.Poligono;
import primitivos.Ponto;
import primitivos.Reta;
import primitivos.Retangulo;
import transformadoresGeometricos.Escala;
import transformadoresGeometricos.Rotacao;
import transformadoresGeometricos.TipoTransformacao;
import transformadoresGeometricos.Translacao;

public class TransformadorGeometrico {
	
	private Ponto pontoReferecia;
	private int anguloRotacao;
	private double escalaX;
	private double escalaY;
	private Object figura;
	
	
	
	public TransformadorGeometrico() {
		this.pontoReferecia = new Ponto(0,0);
		this.figura  = null;
		this.escalaX = 1;
		this.escalaY = 1;
		this.anguloRotacao = 45;
	}
	
	public Object getFigura() {
		return figura;
	}

	public void setFigura(Object figura) {
		this.figura = figura;
	}

	public Ponto getPontoReferecia() {
		return pontoReferecia;
	}
	
	public void setPontoReferecia(Ponto pontoReferecia) {
		this.pontoReferecia = pontoReferecia;
	}
	
	public int getAnguloRotacao() {
		return anguloRotacao;
	}
	
	public void setAnguloRotacao(int anguloRotacao) {
		this.anguloRotacao = anguloRotacao;
	}
	
	public double getEscalaX() {
		return escalaX;
	}
	
	public void setEscalaX(double escalaX) {
		this.escalaX = escalaX;
	}
	
	public double getEscalaY() {
		return escalaY;
	}
	
	public void setEscalaY(double escalaY) {
		this.escalaY = escalaY;
	}
	
	public List<Ponto> transformarFigura(TipoTransformacao tipoTransformacao){
		List<Ponto> pontosDaFigura = new ArrayList<>();
		switch(figura.getClass().getSimpleName()) {
			case "Circulo":
				Circulo circulo = (Circulo) figura;
				pontosDaFigura = CirculoCalculador.obterPontos(circulo);
				break;
			case "LinhaPoligonal":
			case "Poligono":
				Poligono poligono = (Poligono) figura;
				pontosDaFigura  = PoligonoCalculador.obterPontos(poligono);
				break;
			case "Reta":
				Reta reta = (Reta) figura;
				pontosDaFigura = RetaCalculador.obterPontos(reta);
				break;
			case "Retangulo":
				Retangulo retangulo = (Retangulo) figura;
				pontosDaFigura = RetanguloCalculador.obterPontos(retangulo);
				break;
		}
		
		return aplicarTransformacao(tipoTransformacao, pontosDaFigura);

	}
	
	public List<Ponto> aplicarTransformacao(TipoTransformacao tipoTransformacao, List<Ponto> pontosDaFigura){
		switch (tipoTransformacao) {
			case ESCALA:
				Escala escala = new Escala(this.pontoReferecia, escalaX, escalaY, pontosDaFigura);
				return escala.aplicarEscala();
			case ROTACAO:
				Rotacao rotacao = new Rotacao(this.pontoReferecia,pontosDaFigura, anguloRotacao);
				return rotacao.aplicarRotacao();
			case TRANSLACAO:
				Translacao translacao = new Translacao(pontosDaFigura, pontoReferecia);
				return translacao.aplicarTranslacao();
		}
		//Não deve cair nesse return
		return pontosDaFigura;
	}
	
}
