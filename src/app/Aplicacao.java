package app;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.PontoComMouseGui2;

public class Aplicacao extends Application {

    private Stage palco;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage palco) throws Exception {
        this.palco = palco;
        new PontoComMouseGui2(palco);
    }
}

