package controladores;

import java.util.List;

import calculadores.CalculadorGenerico;
import calculadores.CirculoCalculador;
import calculadores.CurvaDoDragaoCalculador;
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
import primitivos.Ponto;
import primitivos.PontoGr;
import primitivos.Reta;
import primitivos.Retangulo;

public class ControladorDeEventos {

	private int iteracoesCurvaDragao;
	private Canvas canvas;
	private TipoDesenho tipoDesenho;
	private Ponto pontoAtual;
	private Color cor;
	private int diametro;
	private WritableImage backup;
	private boolean fimElastico = true;

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

	public void onCanvasMousePressed(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY && tipoDesenho != null) {
			Ponto pontoClicado = new Ponto(event.getX(), event.getY());
			switch (tipoDesenho) {
			case CURVA_DO_DRAGAO:
				desenharCurvaDoDragao();
				break;
			case PONTO:
				desenharPonto((int) Math.floor(event.getX()), (int) Math.floor(event.getY()), "");
				break;
			case RETA:
			case CIRCULO:
				onMousePressedPrimitivosBasicos(pontoClicado);
				break;
			case RETA_ELASTICA:
			case CIRCULO_ELASTICO:
			case RETANGULO_ELASTICO:
			case POLIGONO_ELASTICO:
			case RETA_POLIGONAL:
				onMousePressedPrimitivosElasticos(pontoClicado);
				break;
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

	public void limparCanvas() {
		this.canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	private void preencherCanvasCurvaDoDragão() {
		limparCanvas();
		Reta reta = new Reta(new Ponto(150, 400), new Ponto(600, 400));
		CurvaDoDragaoCalculador calc = new CurvaDoDragaoCalculador(reta, this.iteracoesCurvaDragao);
		List<Reta> retasCurvaDragao;

		try {
			retasCurvaDragao = calc.getRetasCurva();
			for (Reta retaCalc : retasCurvaDragao) {
				desenharPontos(RetaCalculador.obterPontos(retaCalc));
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
			// Capturando estado do canvas para desenhar sobre ele
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.AZURE);
			backup = canvas.snapshot(params, backup);
			fimElastico = false;
		}
	}

	public void onMouseDraggedPrimitivosElasticos(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (fimElastico == false) {
				// Seta canvas para estado capturado quando o mouse foi pressionado
				canvas.getGraphicsContext2D().drawImage(backup, 0, 0);
				// Desenha sobre o "estado" capturado quando mouse foi pressionado		
				Ponto ptFinal = new Ponto(event.getX(), event.getY());
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
				break;
			case RETANGULO_ELASTICO:
				desenharRetangulo(pt);
				break;
			case RETA_POLIGONAL:
				break;
		}
	}

	public void onMouseReleasedPrimitivosElasticos(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (fimElastico == false) {
				Ponto ptFinal = new Ponto(event.getX(), event.getY());
				desenharPrimitivoElastico(ptFinal);
				fimElastico = true;
				pontoAtual = null;
			}
		}
	}

	private void desenharReta(Ponto pontoFinal) {
		Reta reta = new Reta(pontoAtual, pontoFinal);
		desenharPontos(RetaCalculador.obterPontosAlgoritmoMidPoint(reta));
	}

	private void desenharCirculo(Ponto pontoFinal) {
		Ponto pontoMedio = CalculadorGenerico.obterPontoMedio(pontoAtual, pontoFinal);
		int raio = CirculoCalculador.obterRaio(pontoMedio, pontoFinal);
		Circulo circulo = new Circulo(raio, pontoMedio);
		desenharPontos(CirculoCalculador.obterPontosAlgoritmoMidPoint(circulo));
	}
	
	private void desenharRetangulo(Ponto pontoFinal) {
		Retangulo retangulo = new Retangulo(pontoAtual, pontoFinal);
		desenharPontos(RetanguloCalculador.obterPontos(retangulo));
	}
	
	private void desenharPoligono() {
		// TODO: criar modelo e calculador		
	}
	
	private void desenharRetaPoligonal() {
		// TODO: criar modelo e calculador
	}

	private void desenharPontos(List<Ponto> pontos) {
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

	public void getEventoBasicoMenuDesenho(TipoDesenho desenho) {
		tipoDesenho = desenho;
		iteracoesCurvaDragao = 0;
	}

}
