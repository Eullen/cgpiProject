package gui;

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
import transformadoresGeometricos.TipoTransformacao;
import transformadoresGeometricos.Translacao;

public class TransformadorGeometrico {
	
	private Ponto pontoReferecia;
	private int anguloRotacao;
	private int escalaX;
	private int escalaY;
	private Object figura;
	
	
	
	public TransformadorGeometrico() {
		this.pontoReferecia = null;
		this.figura = null;
		this.escalaX = 1;
		this.escalaY = 1;
		this.anguloRotacao = 45;
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
	
	public int getEscalaX() {
		return escalaX;
	}
	
	public void setEscalaX(int escalaX) {
		this.escalaX = escalaX;
	}
	
	public int getEscalaY() {
		return escalaY;
	}
	
	public void setEscalaY(int escalaY) {
		this.escalaY = escalaY;
	}
	
	public Object getFigura() {
		return figura;
	}
	
	public void setFigura(Object figura) {
		this.figura = figura;
	}
	
	
	public List<Ponto> transladarFigura(TipoPrimitivo tipoPrimitivo, TipoTransformacao tipoTransformacao){
		Translacao translacao;
		switch(tipoPrimitivo) {
			case CIRCULO:
				Circulo circulo = (Circulo) figura;
				translacao = new Translacao(CirculoCalculador.obterPontos((circulo)));
				return translacao.aplicarTranslacao();
			case LINHA_POLIGONAL:
			case POLIGONO:
				Poligono poligono = (Poligono) figura;
				translacao = new Translacao(PoligonoCalculador.obterPontos((poligono)));
				return translacao.aplicarTranslacao();
			case RETA:
				Reta reta = (Reta) figura;
				translacao = new Translacao(RetaCalculador.obterPontos((reta)));
				return translacao.aplicarTranslacao();
			case RETANGULO:
				Retangulo retangulo = (Retangulo) figura;
				translacao = new Translacao(RetanguloCalculador.obterPontos((retangulo)));
				return translacao.aplicarTranslacao();
		}
		// Não deve cair aqui
		return null;
	}
	
}
