package app;


import gui.PontoComMouseGui;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Julio Arakaki
 * @version v1.0 2018/05/05
 */

public class Aplicacao extends Application{
     public static void main (String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage palco) throws Exception {
        new PontoComMouseGui(palco);
    }
}

