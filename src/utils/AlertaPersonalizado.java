package utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertaPersonalizado {
	public static void criarAlertaComCallback(String msg, AlertaCallback alertaImpl){
		Alert alert = new Alert(AlertType.WARNING, msg, ButtonType.YES, ButtonType.NO);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.YES)
		     alertaImpl.alertaCallbak();
	}
}
