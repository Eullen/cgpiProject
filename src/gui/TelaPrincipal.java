package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.stage.Stage;
import primitivos.Ponto;

public class TelaPrincipal{

    Ponto pontoSelecionado = null;
    private Stage palco;
    private MenuBar menu;
    private Menu desenho;
    private Menu opcoes;
    private MenuItem menuLimpar;
    private MenuItem menuPontos;
    private MenuItem menuRetas;
    private MenuItem menuCirculos;
    private MenuItem menuCurvaDragao;
    private Canvas canvas;
    private ControladorDeEventos controladorDeEventos;
	
	public TelaPrincipal(Stage palco) {
		this.palco = palco;
		desenharTela();
	}

	public void desenharTela(){
			
		palco.setWidth(750);
		palco.setHeight(750);

		//criando Canvas
        canvas = new Canvas(palco.getWidth(), palco.getHeight());
		controladorDeEventos = new ControladorDeEventos(canvas);      
		// Painel para os componentes
        BorderPane pane = new BorderPane();
        
        //Criando Menu
        menu = new MenuBar();
        desenho = new Menu("Desenho");
        opcoes = new Menu("Opções");
        menuPontos = new MenuItem("Pontos");
        menuRetas = new MenuItem("Retas");
        menuCirculos = new MenuItem("Círculos");
        menuLimpar = new MenuItem("Limpar");
    	menuCurvaDragao = new MenuItem("Curva do Dragão");
        desenho.getItems().addAll(menuPontos,menuRetas,menuCirculos, menuCurvaDragao);
    	opcoes.getItems().add(menuLimpar);
    	menu.getMenus().addAll(desenho,opcoes);
    	
    	//Criando footer
    	GridPane grid = montarMenuOpcoesGraficas();
    	VBox menus = new VBox();
    	menus.getChildren().addAll(menu,grid);
    	        
    	// atributos do painel
        pane.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setCenter(canvas); // posiciona o componente de desenho
        pane.setTop(menus);
        
        
    	atribuirEventosAosComponentesGraficos();
        // cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();
		
	}
	
	private void atribuirEventosAosComponentesGraficos(){
		//menu
		this.menuRetas.setOnAction(e->{
			controladorDeEventos.getEventoBasicoMenuDesenho(TipoDesenho.RETA);
		});
		
		this.menuPontos.setOnAction(e->{
			controladorDeEventos.getEventoBasicoMenuDesenho(TipoDesenho.PONTO);
		});
		this.menuCirculos.setOnAction(e->{
			controladorDeEventos.getEventoBasicoMenuDesenho(TipoDesenho.CIRCULO);
		});
		this.menuLimpar.setOnAction(e->{
			controladorDeEventos.limparCanvas();
		});
		this.menuCurvaDragao.setOnAction(e->{
			controladorDeEventos.setTipoDesenho(TipoDesenho.CURVA_DO_DRAGAO);
		});
		//canvas
        canvas.setOnMouseMoved(event -> {
            palco.setTitle("(Posição do Cursor):" + " (" + (int) event.getX() + ", " + (int) event.getY() + ")");
        });
        canvas.setOnMousePressed(event -> {
        	//System.out.println("Clicou nessa porra");
        	controladorDeEventos.onCanvasMousePressed(event);
        });	
	}
	
	private GridPane montarMenuOpcoesGraficas() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setHgap(5);
		
		// Color Picker 
		ColorPicker colorPicker = new ColorPicker(Color.BLACK);
		colorPicker.setOnAction(e->{
			controladorDeEventos.setCor(colorPicker.getValue());
		});
		
		Spinner<Integer> diametroLinhas = new Spinner<Integer>();
		SpinnerValueFactory<Integer> diametros = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20,3);
		diametroLinhas.setValueFactory(diametros);
		diametroLinhas.valueProperty().addListener(e->{
			controladorDeEventos.setDiametro(diametros.getValue());
		});
		
		grid.add(new Label("Cor: "), 0, 0);
		grid.add(colorPicker, 1, 0);
		grid.add(new Label("Diâmetro dos Pontos: "),2, 0);
		grid.add(diametroLinhas, 3, 0);
				
		return grid;
	}
	
//	private void preencherCanvasBasico(Ponto pt){
//		List<Ponto> pontos = new ArrayList<>();	 
//		
//		switch(tipoDesenho){
//			case "Pontos":
//				pontos.add(pt);
//				break;
//			case "Retas":
//				if (pontoSelecionado == null){
//					pontoSelecionado = pt;
//				}else{
//					Reta reta = new Reta(pontoSelecionado, pt);
//		            //pontos = RetaCalculador.obterPontos(reta);	
//		            pontos = RetaCalculador.obterPontosAlgoritmoMidPoint(reta);
//					pontoSelecionado = null;
//				}
//	            break;
//			case "Círculos":
//				if (pontoSelecionado == null){
//					pontoSelecionado = pt;
//				}else{
//					Ponto pontoMedio = CalculadorGenerico.obterPontoMedio(pontoSelecionado, pt);
//					int raio = CirculoCalculador.obterRaio(pontoMedio, pt);
//					Circulo circulo = new Circulo(raio, pontoMedio);
//					pontos = CirculoCalculador.obterPontosAlgoritmoMidPoint(circulo);
//					pontoSelecionado = null;
//				}
//				break;
//			default:
//				throw new RuntimeException("Erro interno");
//		}	
//		desenharPontos(pontos);
//	}
	
//	private void preencherCanvasCurvaDoDragão() {
//		limparCanvas();
//		Reta reta = new Reta(new Ponto(150,400), new Ponto(600,400));
//		CurvaDoDragaoCalculador calc = new CurvaDoDragaoCalculador(reta, this.iteracoesCurvaDragao);
//	    List<Reta> retasCurvaDragao;
//		
//	    try {
//			retasCurvaDragao = calc.getRetasCurva();
//			for (Reta retaCalc : retasCurvaDragao){
//		    	desenharPontos(RetaCalculador.obterPontos(retaCalc));	
//		    }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//    private void desenharPontos( List<Ponto> pontos) {
//        for (Ponto p : pontos) {
//            desenharPonto((int) Math.floor(p.getx()), (int) Math.floor(p.gety()), "");
//        }
//    }

//    /**
//     * Desenha um ponto grafico
//     *
//     * @param g        contexto grafico
//     * @param x        posicao x
//     * @param y        posicao y
//     * @param diametro diametro do ponto
//     * @param nome     nome do ponto
//     */
//    public void desenharPonto(int x, int y, String nome) {
//        PontoGr p;
//        // Cria um ponto
//        p = new PontoGr(x, y, cor, nome, diametro);
//        // Desenha o ponto
//        p.desenharPonto(gc);
//    }
}
