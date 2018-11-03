package gui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaClipping {
	private Stage palco;
	private WritableImage imagemRecortada;
	public TelaClipping(Stage palco, WritableImage imagemRecortada) {
		this.palco = palco;
		this.imagemRecortada = imagemRecortada;
	}
	
	public void desenharTela() {	
		palco.setWidth(TelaPrincipal.LARGURA_CANVAS);
		palco.setHeight(TelaPrincipal.ALTURA_CANVAS);

		//criando Canvas
        Canvas canvas = new Canvas(imagemRecortada.getWidth(), imagemRecortada.getHeight());
		canvas.getGraphicsContext2D().clearRect(0, 0, imagemRecortada.getWidth(), imagemRecortada.getHeight());
		canvas.getGraphicsContext2D().drawImage(imagemRecortada,0,0);
		// Painel para os componentes
        BorderPane pane = new BorderPane();
        pane.setCenter(canvas);
		// cria e insere cena
        Scene scene = new Scene(pane);
        palco.setScene(scene);
        palco.show();
	}
}
