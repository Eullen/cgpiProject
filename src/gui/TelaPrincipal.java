package gui;
import java.io.File;

import controladores.ControladorDeEventos;
import controladores.TipoDesenho;
import controladores.TipoPrimitivo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import primitivos.Circulo;
import primitivos.LinhaPoligonal;
import primitivos.Poligono;
import primitivos.Ponto;
import primitivos.PontoGr;
import primitivos.Reta;
import primitivos.Retangulo;
import transformadoresGeometricos.TipoTransformacao;
import utils.AlertaCallback;
import utils.AlertaPersonalizado;
import utils.Figura;
import utils.XMLParser;

@SuppressWarnings("restriction")
public class TelaPrincipal {

	Ponto pontoSelecionado = null;
	private Stage palco;
	private MenuBar menu;
	private Menu desenhoPontoPonto;
	private Menu desenhoElastico;
	private Menu clipping;
	private Menu transformacoes;
	private Menu opcoes;
	private Menu arquivo;
	private MenuItem menuLimpar;
	private MenuItem menuPontos;
	private MenuItem menuRetas;
	private MenuItem menuCirculos;
	private MenuItem menuCurvaDragao;
	private MenuItem menuRetaElastica;
	private MenuItem menuCirculoElastico;
	private MenuItem menuRetanguloElastico;
	private MenuItem menuPoligonoElastico;
	private MenuItem menuRetaPoligonalElastica;
	private MenuItem menuApagarPrimitivos;
	private MenuItem menuSelecionarPrimitivos;
	private MenuItem menuDesfazerSelecaoPrimitivos;
	private MenuItem menuClipping;
	private MenuItem menuSelecionarAreaClipping;
	private MenuItem menuDesfazerSelecaoClipping;
	private MenuItem menuSelecionarObjTransformacao;
	private MenuItem menuSelecionarPontoOrigemTransformacao;
	private MenuItem menuTranslacao;
	private MenuItem menuEscala;
	private MenuItem menuRotacao;
	private MenuItem menuAbrirArquivo;
	private MenuItem menuSalvarArquivo;
	private Spinner<Double> escalaSx;
	private Spinner<Double> escalaSy;
	private Spinner<Integer> angulosRotacao;
	
	private Canvas canvas;
	private ControladorDeEventos controladorDeEventos;
	private FileChooser fileChooser;
	
	public static int LARGURA_CANVAS = 1000;
	public static int ALTURA_CANVAS = 700;
					

	public TelaPrincipal(Stage palco) {
		this.palco = palco;
		desenharTela();
	}

	public void desenharTela(){
			
		palco.setWidth(LARGURA_CANVAS);
		palco.setHeight(ALTURA_CANVAS);
		palco.setResizable(false);

		//criando Canvas
        canvas = new Canvas(palco.getWidth(), palco.getHeight());
		controladorDeEventos = new ControladorDeEventos(canvas);  
		
		// Painel para os componentes
        BorderPane pane = new BorderPane();
        
        //Criando Menu
        menu 							        = new MenuBar();
        desenhoPontoPonto 				        = new Menu("Desenho Ponto a Ponto");
        desenhoElastico					        = new Menu("Desenho com Elasticos");
        clipping 						        = new Menu("Clipping");
        transformacoes 					        = new Menu("TransformaÃ§Ãµes GeomÃ©tricas");
        opcoes 							        = new Menu("Opcoes");
        arquivo 						        = new Menu("Arquivo");
        menuPontos 						        = new MenuItem("Pontos");
        menuRetas 						        = new MenuItem("Retas");
        menuCirculos 					        = new MenuItem("Circulos");
        menuLimpar 						        = new MenuItem("Limpar");
    	menuCurvaDragao 				        = new MenuItem("Curva do DragÃ£o");
    	menuRetaElastica 				        = new MenuItem("Retas");
    	menuCirculoElastico 			        = new MenuItem("Circulos");
    	menuRetanguloElastico 			        = new MenuItem("Retangulos");
    	menuPoligonoElastico 			        = new MenuItem("Poligonos");
    	menuRetaPoligonalElastica 		        = new MenuItem("Reta Poligonal");
    	menuApagarPrimitivos 			        = new MenuItem("Apagar Desenhos Selecionados");
    	menuSelecionarPrimitivos 		        = new MenuItem("Selecionar Formas Desenhadas");
    	menuDesfazerSelecaoPrimitivos 	        = new MenuItem("Desfazer Selecao de Formas");
    	menuSelecionarAreaClipping 		        = new MenuItem("Selecionar Ã�rea de Recorte");
    	menuClipping 					        = new MenuItem("Recortar");
    	menuDesfazerSelecaoClipping 	        = new MenuItem("Desfazer SeleÃ§Ã£o de Ã�rea");
    	menuAbrirArquivo 				        = new MenuItem("Abrir XML");
    	menuSalvarArquivo 				        = new MenuItem("Salvar em XML");
    	menuSelecionarObjTransformacao	        = new MenuItem("Selecionar Figura");
    	menuSelecionarPontoOrigemTransformacao  = new MenuItem("Escolher Ponto de Referência para Transformação");
    	menuRotacao 					        = new MenuItem("Rotacionar Figura");
    	menuTranslacao					        = new MenuItem("Transladar Figura");
    	menuEscala						        = new MenuItem("Escalar Figura");
    	
    	desenhoPontoPonto.getItems().addAll(menuPontos,menuRetas,menuCirculos, menuCurvaDragao);
    	desenhoElastico.getItems().addAll(menuRetaElastica, menuCirculoElastico, menuRetanguloElastico, menuPoligonoElastico, menuRetaPoligonalElastica);
    	opcoes.getItems().addAll(menuSelecionarPrimitivos,menuDesfazerSelecaoPrimitivos,menuApagarPrimitivos,menuLimpar);
    	arquivo.getItems().addAll(menuAbrirArquivo, menuSalvarArquivo);
    	clipping.getItems().addAll(menuSelecionarAreaClipping,menuDesfazerSelecaoClipping,menuClipping);
    	transformacoes.getItems().addAll(menuSelecionarPontoOrigemTransformacao,menuSelecionarObjTransformacao, menuRotacao, menuTranslacao, menuEscala);
    	menu.getMenus().addAll(desenhoPontoPonto,desenhoElastico,transformacoes,arquivo,clipping,opcoes);

    	
    	//Criando footer
    	GridPane grid = montarMenuOpcoesGraficas();
    	VBox menus = new VBox();
    	menus.getChildren().addAll(menu,grid);
    	menus.setMinHeight(60);
    	menus.setMaxHeight(60);
    	        
    	// atributos do painel
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setCenter(canvas); // posiciona o componente de desenho
        pane.setTop(menus);
    	atribuirEventosAosComponentesGraficos();
        // cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();
		
	}
	
	// Vincula??o dos componentes do MENU aos eventos declarados no ControladorDeEventos de componentes grasficos.
	private void atribuirEventosAosComponentesGraficos() {
		// menu
		this.menuRetas.setOnAction(e -> {
			controladorDeEventos.getEventoBasicoMenuDesenho(TipoDesenho.RETA);
		});

		this.menuPontos.setOnAction(e -> {
			controladorDeEventos.getEventoBasicoMenuDesenho(TipoDesenho.PONTO);
		});
		this.menuCirculos.setOnAction(e -> {
			controladorDeEventos.getEventoBasicoMenuDesenho(TipoDesenho.CIRCULO);
		});
		this.menuLimpar.setOnAction(e -> {
			AlertaPersonalizado.criarAlertaComCallback("A execucao dessa operacao resulta na perda de todos os dados desenhados.\n "
					+ "Deseja continuar?", new AlertaCallback() {				
						@Override
						public void alertaCallbak() {
							controladorDeEventos.limparCanvas();
						}
					});
		});
		this.menuCurvaDragao.setOnAction(e -> {
			controladorDeEventos.setTipoDesenho(TipoDesenho.CURVA_DO_DRAGAO);
		});
		this.menuRetaElastica.setOnAction(e -> {
			controladorDeEventos.setTipoDesenho(TipoDesenho.RETA_ELASTICA);
		});
		this.menuCirculoElastico.setOnAction(e -> {
			controladorDeEventos.setTipoDesenho(TipoDesenho.CIRCULO_ELASTICO);
		});
		this.menuRetanguloElastico.setOnAction(e -> {
			controladorDeEventos.setTipoDesenho(TipoDesenho.RETANGULO_ELASTICO);
		});
		this.menuPoligonoElastico.setOnAction(e -> {
			controladorDeEventos.setTipoDesenho(TipoDesenho.POLIGONO_ELASTICO);
		});
		this.menuRetaPoligonalElastica.setOnAction(e -> {
			controladorDeEventos.setTipoDesenho(TipoDesenho.RETA_POLIGONAL);
		});
		this.menuSelecionarPrimitivos.setOnAction(e -> {
			controladorDeEventos.setTipoDesenho(TipoDesenho.SELECIONA_DESENHO);
		});
		this.menuDesfazerSelecaoPrimitivos.setOnAction(e -> {
			controladorDeEventos.setTransformacaoEmAndamento(false);
			controladorDeEventos.desfazerSelecao();
		});
		this.menuApagarPrimitivos.setOnAction(ev ->{
			
			AlertaPersonalizado.criarAlertaComCallback("A execução dessa operacão resulta na perda de todos os desenhos selecionados.\n "
					+ "Deseja continuar?", new AlertaCallback() {				
						@Override
						public void alertaCallbak() {
							controladorDeEventos.setTransformacaoEmAndamento(false);
							controladorDeEventos.apagarPrimitivos();
						}
					});
		});

		this.menuSelecionarAreaClipping.setOnAction(ev -> {
			this.controladorDeEventos.setTipoDesenho(TipoDesenho.SELECIONAR_AREA_CLIPPING);
		});
		
		this.menuDesfazerSelecaoClipping.setOnAction(ev -> {
			this.controladorDeEventos.desfazerSelecaoClipping();
		});
		
		this.menuClipping.setOnAction(ev -> {
			this.controladorDeEventos.recortar();
		});
		
		this.menuAbrirArquivo.setOnAction(ev -> {
			AlertaPersonalizado.criarAlertaComCallback("A execução dessa operacão resulta na perda de todos os desenhos não salvos.\n "
					+ "Deseja continuar?", new AlertaCallback() {				
						@Override
						public void alertaCallbak() {
							controladorDeEventos.setTransformacaoEmAndamento(false);
							abriXML();
						}
					});
		});
		
		this.menuSalvarArquivo.setOnAction(ev -> {
			fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
			File file = fileChooser.showSaveDialog(this.palco);
			if (file != null) {
				try {
					XMLParser<Figura> parser = new XMLParser<Figura>(file);
					Figura figura = new Figura();
					figura.setObjetosDesenhados(this.controladorDeEventos.getDesenhador().getObjetosDesenhados());
					parser.saveFile(figura, new Class[] {
							Figura.class,
							Retangulo.class,
							Ponto.class,
							Reta.class,
							Circulo.class,
							Poligono.class,
							LinhaPoligonal.class,
							PontoGr.class
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		this.menuSelecionarPontoOrigemTransformacao.setOnAction(ev -> {
			controladorDeEventos.setTipoTransformacao(TipoTransformacao.SELECAO_PONTO);
		});
		
		this.menuSelecionarObjTransformacao.setOnAction(ev -> {
			controladorDeEventos.setTipoTransformacao(TipoTransformacao.SELECAO_FIGURA);
		});
		
		this.menuTranslacao.setOnAction(ev -> {
			if (this.controladorDeEventos.getTransformadorGeometrico().getFigura() != null){
				this.controladorDeEventos.getDesenhador().desenharPontos(controladorDeEventos.getTransformadorGeometrico().transformarFigura(TipoTransformacao.TRANSLACAO),Color.GREENYELLOW);
			}
		});
		
		this.menuEscala.setOnAction(ev -> {
			if (this.controladorDeEventos.getTransformadorGeometrico().getFigura() != null){
				this.controladorDeEventos.getDesenhador().desenharPontos(controladorDeEventos.getTransformadorGeometrico().transformarFigura(TipoTransformacao.ESCALA),Color.GREENYELLOW);
			}
		});
		
		this.menuRotacao.setOnAction(ev -> {
			if (this.controladorDeEventos.getTransformadorGeometrico().getFigura() != null){
				this.controladorDeEventos.getDesenhador().desenharPontos(controladorDeEventos.getTransformadorGeometrico().transformarFigura(TipoTransformacao.ROTACAO),Color.GREENYELLOW);
			}
		});
		
		// canvas
		canvas.setOnMouseMoved(event -> {
			palco.setTitle("(Posição do Cursor):" + " (" + (int) event.getX() + ", " + (int) event.getY() + ")");
		});
		canvas.setOnMousePressed(event -> {
			controladorDeEventos.onCanvasMousePressed(event);
		});

		canvas.setOnMouseDragged(ev -> {
			controladorDeEventos.onMouseDraggedPrimitivosElasticos(ev);
		});

		canvas.setOnMouseReleased(ev -> {
			controladorDeEventos.onMouseReleasedPrimitivosElasticos(ev);
		});

	}
	
	// Menu de cores e diametro das linhas

	@SuppressWarnings("restriction")
	private GridPane montarMenuOpcoesGraficas() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setHgap(5);

		// Color Picker
		ColorPicker colorPicker = new ColorPicker(Color.BLACK);
		colorPicker.setOnAction(e -> {
			controladorDeEventos.getDesenhador().setCor(colorPicker.getValue());
		});

		Spinner<Integer> diametroLinhas = new Spinner<Integer>();
		SpinnerValueFactory<Integer> diametros = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 3);
		diametroLinhas.setValueFactory(diametros);
		diametroLinhas.setMaxWidth(80);
		diametroLinhas.valueProperty().addListener(e -> {
			controladorDeEventos.getDesenhador().setDiametro(diametros.getValue());
		});
		
		angulosRotacao = new Spinner<Integer>();
		SpinnerValueFactory<Integer> angulos = new SpinnerValueFactory.IntegerSpinnerValueFactory(-90, 90 , 45, 15);
		angulosRotacao.setValueFactory(angulos);
		angulosRotacao.setMaxWidth(80);
		angulosRotacao.valueProperty().addListener(e -> {
			this.controladorDeEventos.getTransformadorGeometrico().setAnguloRotacao(angulosRotacao.getValue());
		});
		
		escalaSx = new Spinner<Double>();
		SpinnerValueFactory<Double> valoresSx = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 5, 1, 0.1);
		escalaSx.setValueFactory(valoresSx);
		escalaSx.setMaxWidth(80);
		escalaSx.valueProperty().addListener(e -> {
			this.controladorDeEventos.getTransformadorGeometrico().setEscalaX(escalaSx.getValue());
		});
		
		escalaSy = new Spinner<Double>();
		SpinnerValueFactory<Double> valoresSy = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 5, 1, 0.1);
		escalaSy.setValueFactory(valoresSy);
		escalaSy.setMaxWidth(80);
		escalaSy.valueProperty().addListener(e -> {
			this.controladorDeEventos.getTransformadorGeometrico().setEscalaY(escalaSy.getValue());
		});
		
		
		grid.add(new Label("Cor: "), 0, 0);
		grid.add(colorPicker, 1, 0);
		grid.add(new Label("Diametro: "), 2, 0);
		grid.add(diametroLinhas, 3, 0);
		grid.add(new Label("Ã‚ngulo: "), 4, 0);
		grid.add(angulosRotacao, 5, 0);
		grid.add(new Label("Escala Sx: "), 6, 0);
		grid.add(escalaSx, 7, 0);
		grid.add(new Label("Escala Sy: "), 8, 0);
		grid.add(escalaSy, 9, 0);

		return grid;
	}
	
	private void abriXML() {
		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File file = fileChooser.showOpenDialog(this.palco);
		if (file != null) {
			try {
				XMLParser<Figura> parser = new XMLParser<Figura>(file);
				Figura figura = parser.toObject(new Class[] {
						Figura.class,
						Retangulo.class,
						Ponto.class,
						Reta.class,
						Ponto.class,
						Circulo.class,
						Poligono.class,
						LinhaPoligonal.class,
						PontoGr.class
				});
				//desenhando objetos obtidos
				this.controladorDeEventos.getDesenhador().setObjetosDesenhados(
						figura.getObjetosDesenhados()
				);					
				this.controladorDeEventos.getDesenhador().desenharObjetosArmazenados(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
