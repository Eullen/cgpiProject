package gui;
import java.io.File;

import controladores.ControladorDeEventos;
import controladores.TipoDesenho;
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

	private MenuItem menuAbrirArquivo;
	private MenuItem menuSalvarArquivo;
	private Canvas canvas;
	private ControladorDeEventos controladorDeEventos;
	private FileChooser fileChooser;
	
	public static int LARGURA_CANVAS = 750;
	public static int ALTURA_CANVAS = 750;
					

	public TelaPrincipal(Stage palco) {
		this.palco = palco;
		desenharTela();
	}

	public void desenharTela(){
			
		palco.setWidth(LARGURA_CANVAS);
		palco.setHeight(ALTURA_CANVAS);

		//criando Canvas
        canvas = new Canvas(palco.getWidth(), palco.getHeight());
		controladorDeEventos = new ControladorDeEventos(canvas);  
		
		// Painel para os componentes
        BorderPane pane = new BorderPane();
        
        //Criando Menu
        menu = new MenuBar();
        desenhoPontoPonto = new Menu("Desenho Ponto a Ponto");
        desenhoElastico = new Menu("Desenho com Elasticos");
        opcoes = new Menu("Opcoes");
        arquivo = new Menu("Arquivo");
        menuPontos = new MenuItem("Pontos");
        menuRetas = new MenuItem("Retas");
        menuCirculos = new MenuItem("Circulos");
        menuLimpar = new MenuItem("Limpar");
    	menuCurvaDragao = new MenuItem("Curva do Dragao");
    	menuRetaElastica = new MenuItem("Retas");
    	menuCirculoElastico = new MenuItem("Circulos");
    	menuRetanguloElastico = new MenuItem("Retangulos");
    	menuPoligonoElastico = new MenuItem("Poligonos");
    	menuRetaPoligonalElastica = new MenuItem("Reta Poligonal");
    	menuApagarPrimitivos = new MenuItem("Apagar Desenhos Selecionados");
    	menuSelecionarPrimitivos = new MenuItem("Selecionar Formas Desenhadas");
    	menuDesfazerSelecaoPrimitivos = new MenuItem("Desfazer Selecao de Formas");

    	menuAbrirArquivo = new MenuItem("Abrir XML");
    	menuSalvarArquivo = new MenuItem("Salvar em XML");
    	desenhoPontoPonto.getItems().addAll(menuPontos,menuRetas,menuCirculos, menuCurvaDragao);
    	desenhoElastico.getItems().addAll(menuRetaElastica, menuCirculoElastico, menuRetanguloElastico, menuPoligonoElastico, menuRetaPoligonalElastica);
    	opcoes.getItems().addAll(menuSelecionarPrimitivos,menuDesfazerSelecaoPrimitivos,menuApagarPrimitivos,menuLimpar);
    	arquivo.getItems().addAll(menuAbrirArquivo, menuSalvarArquivo);
    	menu.getMenus().addAll(desenhoPontoPonto,desenhoElastico,arquivo,opcoes);

    	
    	//Criando footer
    	GridPane grid = montarMenuOpcoesGraficas();
    	VBox menus = new VBox();
    	menus.getChildren().addAll(menu,grid);
    	        
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
	
	// Vincula??o dos componentes do MENU aos eventos declarados no ControladorDeEventos de componentes gr?ficos.
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
			controladorDeEventos.desfazerSelecao();
		});
		this.menuApagarPrimitivos.setOnAction(ev ->{
			
			AlertaPersonalizado.criarAlertaComCallback("A execução dessa operacao resulta na perda de todos os desenhos selecionados.\n "
					+ "Deseja continuar?", new AlertaCallback() {				
						@Override
						public void alertaCallbak() {
							controladorDeEventos.apagarPrimitivos();
						}
					});
		});

		
		this.menuAbrirArquivo.setOnAction(ev -> {
			AlertaPersonalizado.criarAlertaComCallback("A execução dessa operacao resulta na perda de todos os desenhos não salvos.\n "
					+ "Deseja continuar?", new AlertaCallback() {				
						@Override
						public void alertaCallbak() {
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
		
		// canvas
		canvas.setOnMouseMoved(event -> {
			palco.setTitle("(Posi??o do Cursor):" + " (" + (int) event.getX() + ", " + (int) event.getY() + ")");
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
		diametroLinhas.valueProperty().addListener(e -> {
			controladorDeEventos.getDesenhador().setDiametro(diametros.getValue());
		});

		grid.add(new Label("Cor: "), 0, 0);
		grid.add(colorPicker, 1, 0);
		grid.add(new Label("Diametro dos Pontos: "), 2, 0);
		grid.add(diametroLinhas, 3, 0);

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
