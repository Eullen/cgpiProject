package controladores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import calculadores.CalculadorGenerico;
import calculadores.CirculoCalculador;
import calculadores.CurvaDoDragaoCalculador;
import calculadores.PoligonoCalculador;
import calculadores.RetaCalculador;
import calculadores.RetanguloCalculador;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import primitivos.Circulo;
import primitivos.Poligono;
import primitivos.Ponto;
import primitivos.PontoGr;
import primitivos.Reta;
import primitivos.Retangulo;
import utils.AlertaCallback;
import utils.AlertaPersonalizado;

public class ControladorDeEventos {

	private int iteracoesCurvaDragao;
	private Canvas canvas;
	private TipoDesenho tipoDesenho;
	private Ponto pontoAtual;
	private Poligono poligonoEmDesenho;
	private Color cor;
	private int diametro;
	private WritableImage backup;
	private boolean fimElastico = true;
	private Map<TipoPrimitivo, List<Object>> objetosDesenhados;  

	public ControladorDeEventos(Canvas canvas) {
		super();
		this.canvas = canvas;
		this.iteracoesCurvaDragao = 0;
		this.diametro = 3;
		this.cor = Color.BLACK;
		criarMapVazio();
	}

	public void setTipoDesenho(TipoDesenho tipoDesenho) {
		this.tipoDesenho = tipoDesenho;
		resetCanvas();
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public void setDiametro(int diametro) {
		this.diametro = diametro;
	}
	
	private void criarMapVazio(){
		//TODO: mudar implementação para evitar ambiguidades e colocar a cor do objeto
		objetosDesenhados = new HashMap<>();
		List<TipoPrimitivo> listEnum = Arrays.asList(TipoPrimitivo.values());
		
		for ( TipoPrimitivo tipoPrimitivo: listEnum) {
			objetosDesenhados.put(tipoPrimitivo, new ArrayList<>());
		}
	}
	
	public void onCanvasMousePressed(MouseEvent event) {
		if (tipoDesenho != null){
			if (event.getButton() == MouseButton.PRIMARY ) {
				// Captura clique com o botão primário do mouse
				Ponto pontoClicado = new Ponto(event.getX(), event.getY());
				//Definir qual desenho será feito
				switch (tipoDesenho) {
					case CURVA_DO_DRAGAO:
						desenharCurvaDoDragao();
						break;
					case PONTO:
						desenharPonto((int) Math.floor(event.getX()), (int) Math.floor(event.getY()), "", cor);
						break;
					case RETA:
					case CIRCULO:
						onMousePressedPrimitivosBasicos(pontoClicado);
						break;
					case RETA_ELASTICA:
					case CIRCULO_ELASTICO:
					case RETANGULO_ELASTICO:
						onMousePressedPrimitivosElasticos(pontoClicado);
					case POLIGONO_ELASTICO:
					case RETA_POLIGONAL:
						onMousePressedPoligonosElasticos(pontoClicado);
						break;
				}
			}else if(event.getButton() == MouseButton.SECONDARY && (poligonoEmDesenho != null) && (poligonoEmDesenho.getRetas().size() > 2)){
				// Captura clique com o botão secundário do mouse quando usuario está desenhando poligonos
				switch (tipoDesenho) {
					case POLIGONO_ELASTICO:
						salvarPrimitivoDesenhado(TipoPrimitivo.POLIGONO, poligonoEmDesenho);
						fecharPoligono();
						break;
					case RETA_POLIGONAL:
						salvarPrimitivoDesenhado(TipoPrimitivo.LINHA_POLIGONAL, poligonoEmDesenho);
						break;
				}
				resetCanvas();
			}
		}
	}

	private void preencherCanvasCurvaDoDragão() {
		
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Reta reta = new Reta(new Ponto(150, 400), new Ponto(600, 400), cor);
		CurvaDoDragaoCalculador calc = new CurvaDoDragaoCalculador(reta, this.iteracoesCurvaDragao);
		List<Reta> retasCurvaDragao;

		try {
			retasCurvaDragao = calc.getRetasCurva();
			for (Reta retaCalc : retasCurvaDragao) {
				desenharPontos(RetaCalculador.obterPontos(retaCalc), cor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onMousePressedPrimitivosBasicos(Ponto pt) {

		if (pontoAtual == null) {
			pontoAtual = pt;
		} else {
			switch (tipoDesenho) {
			case RETA:
				desenharReta(pt);
				break;
			case CIRCULO:
				desenharCirculo(pt);
				break;
			default:
				throw new RuntimeException("Erro interno");
			}
			pontoAtual = null;
		}
	}

	private void onMousePressedPrimitivosElasticos(Ponto pt) {
		if (pontoAtual == null) {
			pontoAtual = pt;
			salvarCanvas();
			fimElastico = false;
		}
	}
	
	private void onMousePressedPoligonosElasticos(Ponto pt){
		if (pontoAtual == null) {
			poligonoEmDesenho = new Poligono();
			pontoAtual = pt;
			fimElastico = false;
		}
		salvarCanvas();
	}
	
	public void onMouseDraggedPrimitivosElasticos(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (fimElastico == false) {
				// Seta canvas para estado capturado quando o mouse foi pressionado
				canvas.getGraphicsContext2D().drawImage(backup, 0, 0);
				// Desenha sobre o "estado" capturado quando mouse foi pressionado		
				Ponto ptFinal = new Ponto(event.getX(), event.getY());
				if (isPoligonoElastico()&& !poligonoEmDesenho.getRetas().isEmpty()){
					//retirando retas desenhadas no dragged
					poligonoEmDesenho.removerReta(poligonoEmDesenho.getRetas().size()-1);
				}
				desenharPrimitivoElastico(ptFinal);
				fimElastico = false;
			}
		}
	}
	
	private void desenharPrimitivoElastico(Ponto pt) {
		switch(tipoDesenho) {
			case RETA_ELASTICA:
				desenharReta(pt);
				break;
			case CIRCULO_ELASTICO:
				desenharCirculo(pt);
				break;
			case POLIGONO_ELASTICO:
			case RETA_POLIGONAL:
				desenharPoligono(pt);
				break;
			case RETANGULO_ELASTICO:
				desenharRetangulo(pt);
				break;
		}
	}
	
	public void onMouseReleasedPrimitivosElasticos(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (fimElastico == false) {
				Ponto ptFinal = new Ponto(event.getX(), event.getY());
				//Atualiza se não estiver desenhando poligono elastico 
				fimElastico =  (isPoligonoElastico())
								? false
								: true;
				desenharPrimitivoElastico(ptFinal);
				//Se estiver desenhando poligono elastico, precisa usar o ultimo ponto para desenhar a proxima reta
				pontoAtual = (isPoligonoElastico())
								? ptFinal 
								: null;

			}
		}
	}
	
	private void desenharCurvaDoDragao() {
		if (iteracoesCurvaDragao <= 17) {
			preencherCanvasCurvaDoDragão();
			this.iteracoesCurvaDragao += 1;
		} else {
			Alert alerta = new Alert(AlertType.WARNING, "A aplicação atingiu o máximo de iterações possíveis.",
					ButtonType.FINISH);
			alerta.show();
		}
	}
	
	private void desenharReta(Ponto pontoFinal) {
		Reta reta = new Reta(pontoAtual, pontoFinal, cor);
		desenharPontos(RetaCalculador.obterPontosAlgoritmoMidPoint(reta), cor);
		salvarPrimitivoDesenhado(TipoPrimitivo.RETA,reta);
	}

	private void desenharCirculo(Ponto pontoFinal) {
		Ponto pontoMedio = CalculadorGenerico.obterPontoMedio(pontoAtual, pontoFinal);
		int raio = CirculoCalculador.obterRaio(pontoMedio, pontoFinal);
		Circulo circulo = new Circulo(raio, pontoMedio, cor);
		desenharPontos(CirculoCalculador.obterPontosAlgoritmoMidPoint(circulo), cor);
		salvarPrimitivoDesenhado(TipoPrimitivo.CIRCULO, circulo);
	}
	
	private void desenharRetangulo(Ponto pontoFinal) {
		Retangulo retangulo = new Retangulo(pontoAtual, pontoFinal, cor);
		desenharPontos(RetanguloCalculador.obterPontos(retangulo), cor);
		salvarPrimitivoDesenhado(TipoPrimitivo.RETANGULO, retangulo);
	}
	
	private void desenharPoligono(Ponto pontoFinal) {
		Reta reta = new Reta(pontoAtual, pontoFinal, cor);
		poligonoEmDesenho.addReta(reta);
		desenharPontos(PoligonoCalculador.obterPontos(poligonoEmDesenho), cor);
	}

	private void desenharPontos(List<Ponto> pontos, Color cor) {
		for (Ponto p : pontos) {
			desenharPonto((int) Math.floor(p.getx()), (int) Math.floor(p.gety()), "", cor);
		}
	}

	private void desenharPonto(int x, int y, String nome, Color cor) {
		PontoGr p;
		// Cria um ponto
		p = new PontoGr(x, y, cor, nome, diametro);
		// Desenha o ponto
		p.desenharPonto(canvas.getGraphicsContext2D());
	}
	
	private void salvarPrimitivoDesenhado(TipoPrimitivo tipoPrimitivo,Object primitivo){
		if (fimElastico || isPoligonoElastico()) objetosDesenhados.get(tipoPrimitivo).add(primitivo);
	}
	
	private void fecharPoligono(){
			Reta retaFinal = new Reta(poligonoEmDesenho.getRetas().get(0).getA(), pontoAtual, cor);
			poligonoEmDesenho.addReta(retaFinal);
			desenharPontos(PoligonoCalculador.obterPontos(poligonoEmDesenho), cor);
	}

	public void getEventoBasicoMenuDesenho(TipoDesenho desenho) {
		tipoDesenho = desenho;
		iteracoesCurvaDragao = 0;
		resetCanvas();
	}
	
	public void mostrarAvisoLimparCanvas(){
		AlertaPersonalizado.criarAlertaComCallback("A execução dessa operação resulta na perda de todos os dados desenhados. \n "
				+ "Deseja continuar?", new AlertaCallback() {				
					@Override
					public void alertaCallbak() {
						limparCanvas();
					}
				});
	}
	
	public void limparCanvas() {
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		criarMapVazio();
		resetCanvas();
	}

	private void salvarCanvas(){
		// Capturando estado do canvas para desenhar sobre ele
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.AZURE);
		backup = canvas.snapshot(params, backup);
	}
	
	public void resetCanvas(){
		fimElastico = true;
		poligonoEmDesenho = null;
		pontoAtual = null;
		iteracoesCurvaDragao = 0;
	}
		
	private Boolean isPoligonoElastico(){
		return tipoDesenho.equals(TipoDesenho.POLIGONO_ELASTICO) || (tipoDesenho.equals(TipoDesenho.RETA_POLIGONAL));
	}
}
