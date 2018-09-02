package gui;

import java.util.ArrayList;
import java.util.List;

import calculadores.CurvaDoDragaoCalculador;
import calculadores.RetaCalculador;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import primitivos.Ponto;
import primitivos.PontoGr;
import primitivos.Reta;

public class TelaCurvaDoDragao {

    Ponto pontoSelecionado = null;
    private Stage palco;
    private GraphicsContext gc;
    private Canvas canvas;

    public TelaCurvaDoDragao(Stage palco) {
        this.palco = palco;
        desenharTela();
    }

    public void desenharTela(){
        palco.setWidth(750);
        palco.setHeight(750);
        // Painel para os componentes
        BorderPane pane = new BorderPane();

        canvas = new Canvas(palco.getWidth(), palco.getHeight());
        gc = canvas.getGraphicsContext2D();

        //Criando Menu

        atribuirEventosAosComponentesGraficos();

        // atributos do painel
        pane.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setCenter(canvas); // posiciona o componente de desenho
        // cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();

    }

    private void atribuirEventosAosComponentesGraficos(){

        //canvas
        canvas.setOnMouseMoved(event -> {
            palco.setTitle("(Posi��o do Cursor):" + " (" + (int) event.getX() + ", " + (int) event.getY() + ")");
        });
        canvas.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY ) {
                preencherCanvas(new Ponto(event.getX(),event.getY()));
            }
        });
    }

    private void preencherCanvas(Ponto pt){

        if (pontoSelecionado == null){
            pontoSelecionado = pt;
        }else{
            Reta reta = new Reta(pontoSelecionado, pt);
            CurvaDoDragaoCalculador calc = new CurvaDoDragaoCalculador(reta, 4);
            List <Reta> retasCurvaDragao = calc.getRetasCurva();

            for (Reta retaCalc : retasCurvaDragao){
                desenharPontos(RetaCalculador.obterPontos(retaCalc));
            }
        }
    }


    private void desenharPontos( List<Ponto> pontos) {
        for (Ponto p : pontos) {
            desenharPonto((int) Math.floor(p.getx()), (int) Math.floor(p.gety()), 2, "", Color.BLACK);
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
     * @param cor      cor do ponto
     */
    public void desenharPonto(int x, int y, int diametro, String nome, Color cor) {
        PontoGr p;
        // Cria um ponto
        p = new PontoGr(x, y, cor, nome, diametro);
        // Desenha o ponto
        p.desenharPonto(gc);
    }
}
