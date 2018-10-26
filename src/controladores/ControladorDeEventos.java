package controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import calculadores.CirculoCalculador;
import calculadores.CurvaDoDragaoCalculador;
import calculadores.PoligonoCalculador;
import calculadores.RetaCalculador;
import calculadores.RetanguloCalculador;
import gui.Desenhador;
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
import primitivos.LinhaPoligonal;
import primitivos.Poligono;
import primitivos.Ponto;
import primitivos.Reta;
import primitivos.Retangulo;

public class ControladorDeEventos {

	private int iteracoesCurvaDragao;
	private Canvas canvas;
	private TipoDesenho tipoDesenho;
	private Ponto pontoAtual;
	private WritableImage backup;
	private boolean fimElastico;
	private Desenhador desenhador;

	public ControladorDeEventos(Canvas canvas) {
		super();
		this.canvas = canvas;
		this.iteracoesCurvaDragao = 0;
		fimElastico = true;
		this.desenhador = new Desenhador(this.canvas);
	}

	public Desenhador getDesenhador() {
		return desenhador;
	}

	public void setDesenhador(Desenhador desenhador) {
		this.desenhador = desenhador;
	}

	public void setTipoDesenho(TipoDesenho tipoDesenho) {
		this.tipoDesenho = tipoDesenho;
		resetCanvas();
	}
	
	public void onCanvasMousePressed(MouseEvent event) {
		if (tipoDesenho != null){
			if (event.getButton() == MouseButton.PRIMARY ) {
				// Captura clique com o bot�o prim�rio do mouse
				Ponto pontoClicado = new Ponto(event.getX(), event.getY());
				//Definir qual desenho ser� feito
				switch (tipoDesenho) {
					case CURVA_DO_DRAGAO:
						desenharCurvaDoDragao();
						break;
					case SELECIONA_DESENHO:
						onCanvasMousePressedSelecionarPrimitivo(pontoClicado);
						break;
					case PONTO:
						this.desenhador.desenharPonto((int) Math.floor(event.getX()), (int) Math.floor(event.getY()));
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
			}else if(event.getButton() == MouseButton.SECONDARY && this.desenhador.isPoligonoElasticoEmDesenho()){
				// Captura clique com o bot�o secund�rio do mouse quando usuario est� desenhando poligonos
				switch (tipoDesenho) {
					case POLIGONO_ELASTICO:
						Ponto ptInicio = this.desenhador.getPoligonoEmDesenho().getRetas().get(0).getA();
						this.desenhador.desenharPoligono(pontoAtual, ptInicio);
						this.desenhador.salvarPoligonoDesenhado(TipoPrimitivo.POLIGONO);
						break;
					case RETA_POLIGONAL:
						this.desenhador.salvarPoligonoDesenhado(TipoPrimitivo.LINHA_POLIGONAL);
						break;
				}
				resetCanvas();
				resetPoligonoEmDesenho();
			}
		}
	}
	


	private void onMousePressedPrimitivosBasicos(Ponto pt) {

		if (pontoAtual == null) {
			pontoAtual = pt;
		} else {
			switch (tipoDesenho) {
			case RETA:
				this.desenhador.desenharReta(pontoAtual,pt,fimElastico);
				break;
			case CIRCULO:
				this.desenhador.desenharCirculo(pontoAtual,pt,fimElastico);
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
			Poligono poligono = (tipoDesenho.equals(TipoDesenho.POLIGONO_ELASTICO)) 
					? new Poligono(desenhador.getCor()) 
					: new LinhaPoligonal(desenhador.getCor());
			this.desenhador.setPoligonoEmDesenho(poligono);
			pontoAtual = pt;
			fimElastico = false;
		}
		salvarCanvas();
	}
		
	
	public void onCanvasMousePressedSelecionarPrimitivo(Ponto pontoClicado){
		
		if(this.desenhador.getPoligonoEmDesenho() != null) {
			this.desenhador.salvarPoligonoDesenhado(TipoPrimitivo.LINHA_POLIGONAL);
		} 
		
		// Iterando sobre objetos j� desenhados
		this.desenhador.getObjetosDesenhados().forEach((tipoPrimitivo, desenhos)->{
			
			for(Object desenho : desenhos){
				double distancia = 0;
				// calcular distancia do ponto pro objeto
				switch (tipoPrimitivo) {
					case RETA:
						distancia = RetaCalculador.calcularDistanciaPontoReta(pontoClicado, (Reta)desenho);
						break;
					case RETANGULO:
						distancia = RetanguloCalculador.calcularDistanciaPontoRetasRetangulo(pontoClicado, (Retangulo)desenho);
						break;
					case POLIGONO:
					case LINHA_POLIGONAL:
						distancia = PoligonoCalculador.calcularDistanciaPontoRetasPoligono(pontoClicado, (Poligono) desenho);
						break;
					case CIRCULO:
						distancia = CirculoCalculador.calcularDistanciaPontoCirculo(pontoClicado, (Circulo) desenho);
						break;
				}
				//guarda objeto para remo��o posterior
				if (distancia < 7.00){
					//verifica se j� existe na lista de remo��o
					if (!this.desenhador.getIndicesObjetosParaApagar().get(tipoPrimitivo).contains(desenhos.indexOf(desenho))){
						this.desenhador.getIndicesObjetosParaApagar().get(tipoPrimitivo).add(desenhos.indexOf(desenho));
					}
				}
			}
		});
		this.desenhador.desenharObjetosArmazenados(Color.DARKRED);
		resetCanvas();
		resetPoligonoEmDesenho();
	}
		
	public void onMouseDraggedPrimitivosElasticos(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (fimElastico == false) {
				// Seta canvas para estado capturado quando o mouse foi pressionado
				canvas.getGraphicsContext2D().drawImage(backup, 0, 0);
				// Desenha sobre o "estado" capturado quando mouse foi pressionado		
				Ponto ptFinal = new Ponto(event.getX(), event.getY());
				if (isPoligonoElastico()&& !this.desenhador.getPoligonoEmDesenho().getRetas().isEmpty()){
					//retirando retas desenhadas no dragged
					this.desenhador.getPoligonoEmDesenho().removerReta(this.desenhador.getPoligonoEmDesenho().getRetas().size()-1);
				}
				this.desenhador.desenharPrimitivoElastico(pontoAtual,ptFinal, tipoDesenho, fimElastico);
				fimElastico = false;
			}
		}
	}

	public void onMouseReleasedPrimitivosElasticos(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (fimElastico == false) {
				Ponto ptFinal = new Ponto(event.getX(), event.getY());
				//Atualiza se n�o estiver desenhando poligono elastico 
				fimElastico =  (isPoligonoElastico())
								? false
								: true;
				this.desenhador.desenharPrimitivoElastico(pontoAtual,ptFinal,tipoDesenho, fimElastico);
				//Se estiver desenhando poligono elastico, precisa usar o ultimo ponto para desenhar a proxima reta
				pontoAtual = (isPoligonoElastico())
								? ptFinal 
								: null;

			}
		}
	}
	
	private void desenharCurvaDoDragao() {
		if (iteracoesCurvaDragao <= 17) {
			preencherCanvasCurvaDoDragao();
			this.iteracoesCurvaDragao += 1;
		} else {
			Alert alerta = new Alert(AlertType.WARNING, "A aplica��o atingiu o m�ximo de itera��es poss�veis.",
					ButtonType.FINISH);
			alerta.show();
		}
	}
	
	private void preencherCanvasCurvaDoDragao() {
		
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Reta reta = new Reta(new Ponto(150, 400), new Ponto(600, 400), this.desenhador.getCor());
		CurvaDoDragaoCalculador calc = new CurvaDoDragaoCalculador(reta, this.iteracoesCurvaDragao);
		List<Reta> retasCurvaDragao;

		try {
			retasCurvaDragao = calc.getRetasCurva();
			for (Reta retaCalc : retasCurvaDragao) {
				this.desenhador.desenharPontos(RetaCalculador.obterPontos(retaCalc), this.desenhador.getCor());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getEventoBasicoMenuDesenho(TipoDesenho desenho) {
		tipoDesenho = desenho;
		resetCanvas();
	}
	
	public void limparCanvas() {
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.desenhador.inicilizarEstruturasManipulacaoDeDesenhos();
		resetCanvas();
		resetPoligonoEmDesenho();
	}

	private void salvarCanvas(){
		// Capturando estado do canvas para desenhar sobre ele
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.WHITE);
		backup = canvas.snapshot(params, backup);
	}
	
	public void resetCanvas(){
		fimElastico = true;
		pontoAtual = null;
		iteracoesCurvaDragao = 0;
	}
	
	public void resetPoligonoEmDesenho() {
		this.desenhador.setPoligonoEmDesenho(null);
	}
		
	private Boolean isPoligonoElastico(){
		return tipoDesenho.equals(TipoDesenho.POLIGONO_ELASTICO) || (tipoDesenho.equals(TipoDesenho.RETA_POLIGONAL));
	}
	

	public void desfazerSelecao() {
		this.desenhador.limparObjetosSelecionados();
	}
	
	public void apagarPrimitivos(){		
		if (!this.desenhador.getIndicesObjetosParaApagar().isEmpty()){
			Map<TipoPrimitivo, List<Object>> objetosDesenhadosAtualizados = copiaDesenhos(this.desenhador.getObjetosDesenhados()); 
			this.desenhador.getIndicesObjetosParaApagar().forEach((tipoPrimitivo, listaIndices) -> {
				for (int indice : listaIndices){
					// remove primitivo da estrutura que guarda os objetos desenhados
					Object objetoParaRemover = this.desenhador.getObjetosDesenhados().get(tipoPrimitivo).get(indice);
					objetosDesenhadosAtualizados.get(tipoPrimitivo).remove(objetoParaRemover);
				}				
			});
			this.desenhador.setObjetosDesenhados(objetosDesenhadosAtualizados);
		}
		desfazerSelecao();
	}
	
	// cria copia da lista de objetos desenhados para manipular os indices sem perda de dados 
	private Map<TipoPrimitivo, List<Object>> copiaDesenhos(Map<TipoPrimitivo, List<Object>> objetosDesenhados){
		Map<TipoPrimitivo, List<Object>> objetosDesenhadosAtualizados = new HashMap<>();
		objetosDesenhados.forEach((tipo, lista)->{
			List<Object> listaAuxiliar = new ArrayList<>();
			for ( Object desenho : lista){
				listaAuxiliar.add(desenho);
			}
			objetosDesenhadosAtualizados.put(tipo,listaAuxiliar);
		});
		return objetosDesenhadosAtualizados;
	}

}
