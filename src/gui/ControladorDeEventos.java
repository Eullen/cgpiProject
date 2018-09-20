package gui;


import java.util.ArrayList;
import java.util.List;


import calculadores.CalculadorGenerico;
import calculadores.CirculoCalculador;
import calculadores.CurvaDoDragaoCalculador;
import calculadores.RetaCalculador;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import primitivos.Circulo;
import primitivos.Ponto;
import primitivos.PontoGr;
import primitivos.Reta;

public class ControladorDeEventos {
	
	private int iteracoesCurvaDragao;
	private Canvas canvas;
    private TipoDesenho tipoDesenho;
	private Ponto pontoAtual;
    private Color cor;
    private int diametro;
    
    
	public ControladorDeEventos(Canvas canvas) {
		super();
		this.canvas = canvas;
		this.iteracoesCurvaDragao = 0;
		this.diametro = 3;		
	}
	
    public void setTipoDesenho(TipoDesenho tipoDesenho) {
		this.tipoDesenho = tipoDesenho;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public void setDiametro(int diametro) {
		this.diametro = diametro;
	}

	public void onCanvasMousePressed(MouseEvent event){
		if (event.getButton() == MouseButton.PRIMARY && tipoDesenho != null) {
        	if (tipoDesenho.equals(TipoDesenho.CURVA_DO_DRAGAO)) {
        		if (iteracoesCurvaDragao <= 17) {
        		preencherCanvasCurvaDoDragão();
        		this.iteracoesCurvaDragao += 1;
        		}else {
        			Alert alerta = new Alert(AlertType.WARNING, "A aplicação atingiu o máximo de iterações possíveis.", ButtonType.FINISH);
        			alerta.show();
        		}
        	}else if (tipoDesenho.equals(TipoDesenho.PONTO)){
        		desenharPonto((int) Math.floor(event.getX()),(int) Math.floor(event.getY()), "");
        	}else{
        		preencherCanvasPrimitivosBasicos(new Ponto(event.getX(),event.getY()));             		
        	}
    	}	
	}
	
	public void limparCanvas() {
		this.iteracoesCurvaDragao = 0;
		this.canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
	}
	
	private void preencherCanvasCurvaDoDragão() {
		limparCanvas();
		Reta reta = new Reta(new Ponto(150,400), new Ponto(600,400));
		CurvaDoDragaoCalculador calc = new CurvaDoDragaoCalculador(reta, this.iteracoesCurvaDragao);
	    List<Reta> retasCurvaDragao;
		
	    try {
			retasCurvaDragao = calc.getRetasCurva();
			for (Reta retaCalc : retasCurvaDragao){
		    	desenharPontos(RetaCalculador.obterPontos(retaCalc));	
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void preencherCanvasPrimitivosBasicos(Ponto pt){
		List<Ponto> pontos = new ArrayList<>();	 

		switch(tipoDesenho){
			case RETA:
				desenharReta(pt);
	            break;
			case CIRCULO:
				desenharCirculo(pt);
				break;
			default:
				throw new RuntimeException("Erro interno");
		}	
	}
	
	private void desenharReta(Ponto pontoFinal){
		if (pontoAtual == null){
			pontoAtual = pontoFinal;
		}else{
			Reta reta = new Reta(pontoAtual, pontoFinal);
            //pontos = RetaCalculador.obterPontos(reta);	
			desenharPontos(RetaCalculador.obterPontosAlgoritmoMidPoint(reta));
            pontoAtual = null;
		}
	}
	
	private void desenharCirculo(Ponto pontoFinal){
		if (pontoAtual == null){
			pontoAtual = pontoFinal;
		}else{
			Ponto pontoMedio = CalculadorGenerico.obterPontoMedio(pontoAtual, pontoFinal);
			int raio = CirculoCalculador.obterRaio(pontoMedio, pontoFinal);
			Circulo circulo = new Circulo(raio, pontoMedio);
			desenharPontos(CirculoCalculador.obterPontosAlgoritmoMidPoint(circulo));
			 pontoAtual = null;
		}
	}
	
    private void desenharPontos( List<Ponto> pontos) {
        for (Ponto p : pontos) {
            desenharPonto((int) Math.floor(p.getx()), (int) Math.floor(p.gety()), "");
        }
    }

    private void desenharPonto(int x, int y, String nome) {
        PontoGr p;
        // Cria um ponto
        p = new PontoGr(x, y, cor, nome, diametro);
        // Desenha o ponto
        p.desenharPonto(canvas.getGraphicsContext2D());
    }
	
	public void getEventoBasicoMenuDesenho(TipoDesenho desenho){
		tipoDesenho = desenho;
		iteracoesCurvaDragao = 0;
	}
	
	
	
}
