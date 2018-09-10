package gui;

import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.impl.ior.iiop.AlternateIIOPAddressComponentImpl;
import com.sun.xml.internal.ws.util.StringUtils;

import calculadores.CalculadorGenerico;
import calculadores.CirculoCalculador;
import calculadores.CurvaDoDragaoCalculador;
import calculadores.RetaCalculador;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import primitivos.Circulo;
import primitivos.Ponto;
import primitivos.PontoGr;
import primitivos.Reta;

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
    private String tipoDesenho;
    private GraphicsContext gc;
    private Canvas canvas;
    private Color cor;
    private int diametro;
    private int iteracoesCurvaDragao;
	
	public TelaPrincipal(Stage palco) {
		this.palco = palco;
		desenharTela();
		this.iteracoesCurvaDragao = 0;
	}

	public void desenharTela(){
		palco.setWidth(750);
		palco.setHeight(750);
        // Painel para os componentes
        BorderPane pane = new BorderPane();
        
        canvas = new Canvas(palco.getWidth(), palco.getHeight());
        gc = canvas.getGraphicsContext2D();
        
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
    	
    	atribuirEventosAosComponentesGraficos();
        
    	// atributos do painel
        pane.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setCenter(canvas); // posiciona o componente de desenho
        pane.setTop(menus);
        // cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();
		
	}
	
	private void atribuirEventosAosComponentesGraficos(){
		//menu
		this.menuRetas.setOnAction(e->{
			tipoDesenho = menuRetas.getText();
			iteracoesCurvaDragao = 0;
		});
		
		this.menuPontos.setOnAction(e->{
			tipoDesenho = menuPontos.getText();
			iteracoesCurvaDragao = 0;
		});
		this.menuCirculos.setOnAction(e->{
			tipoDesenho = menuCirculos.getText();
			iteracoesCurvaDragao = 0;
		});
		this.menuLimpar.setOnAction(e->{
			iteracoesCurvaDragao = 0;
			limparCanvas();
		});
		this.menuCurvaDragao.setOnAction(e->{
			tipoDesenho = menuCurvaDragao.getText();
		});
		//canvas
        canvas.setOnMouseMoved(event -> {
            palco.setTitle("(Posição do Cursor):" + " (" + (int) event.getX() + ", " + (int) event.getY() + ")");
        });
        canvas.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY && tipoDesenho != null) {
            	if (tipoDesenho.equals(menuCurvaDragao.getText())) {
            		if (iteracoesCurvaDragao <= 17) {
            		preencherCanvasCurvaDoDragão();
            		this.iteracoesCurvaDragao += 1;
            		}else {
            			Alert alerta = new Alert(AlertType.WARNING, "A aplicação atingiu o máximo de iterações possíveis.", ButtonType.FINISH);
            			alerta.show();
            		}
            	}else {
            		preencherCanvasBasico(new Ponto(event.getX(),event.getY()));             		
            	}
        	}	
        });	
	}
	
	private void limparCanvas() {
		this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	private GridPane montarMenuOpcoesGraficas() {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setHgap(5);
		
		// Color Picker 
		ColorPicker colorPicker = new ColorPicker(Color.BLACK);
		colorPicker.setOnAction(e->{
			cor = colorPicker.getValue();
		});
		
		Spinner<Integer> diametroLinhas = new Spinner<Integer>();
		SpinnerValueFactory<Integer> diametros = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20,3);
		diametroLinhas.setValueFactory(diametros);
		diametroLinhas.valueProperty().addListener(e->{
			this.diametro = diametros.getValue(); 
		});
		
		grid.add(new Label("Cor: "), 0, 0);
		grid.add(colorPicker, 1, 0);
		grid.add(new Label("Diâmetro dos Pontos: "),2, 0);
		grid.add(diametroLinhas, 3, 0);
				
		return grid;
	}
	
	private void preencherCanvasBasico(Ponto pt){
		List<Ponto> pontos = new ArrayList<>();	 
		
		switch(tipoDesenho){
			case "Pontos":
				pontos.add(pt);
				break;
			case "Retas":
				if (pontoSelecionado == null){
					pontoSelecionado = pt;
				}else{
					Reta reta = new Reta(pontoSelecionado, pt);
		            //pontos = RetaCalculador.obterPontos(reta);	
		            pontos = RetaCalculador.obterPontosAlgoritmoMidPoint(reta);
					pontoSelecionado = null;
				}
	            break;
			case "Círculos":
				if (pontoSelecionado == null){
					pontoSelecionado = pt;
				}else{
					Ponto pontoMedio = CalculadorGenerico.obterPontoMedio(pontoSelecionado, pt);
					int raio = CirculoCalculador.obterRaio(pontoMedio, pt);
					Circulo circulo = new Circulo(raio, pontoMedio);
					pontos = CirculoCalculador.obterPontosAlgoritmoMidPoint(circulo);
					pontoSelecionado = null;
				}
				break;
			default:
				throw new RuntimeException("Erro interno");
		}	
		desenharPontos(pontos);
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
	
    private void desenharPontos( List<Ponto> pontos) {
        for (Ponto p : pontos) {
            desenharPonto((int) Math.floor(p.getx()), (int) Math.floor(p.gety()), "");
        }
    }

    /**
     * Desenha um ponto grafico
     *
     * @param g        contexto grafico
     * @param x        posicao x
     * @param y        posicao y
     * @param diametro diametro do ponto
     * @param nome     nome do ponto
     */
    public void desenharPonto(int x, int y, String nome) {
        PontoGr p;
        // Cria um ponto
        p = new PontoGr(x, y, cor, nome, diametro);
        // Desenha o ponto
        p.desenharPonto(gc);
    }
}
