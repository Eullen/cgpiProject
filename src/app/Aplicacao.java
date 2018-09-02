package app;

import gui.TelaCurvaDoDragao;
import javafx.application.Application;
import javafx.stage.Stage;

public class Aplicacao extends Application {

    private Stage palco;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage palco) throws Exception {
        this.palco = palco;
        new TelaCurvaDoDragao(palco);
    }
}