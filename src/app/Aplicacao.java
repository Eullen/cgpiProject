package app;

import gui.TelaPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class Aplicacao extends Application {


	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage palco) throws Exception {
		new TelaPrincipal(palco);
	}
}
