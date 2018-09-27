package primitivos;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Poligono {
	
	private List<Reta> retas;
	private Color cor;	

	public Poligono() {
		this.retas = new ArrayList<>();
		this.cor = cor;
	}

	public List<Reta> getRetas() {
		return retas;
	}

	public void addReta(Reta reta) {
		this.retas.add(reta);
	}
	
	public void removerReta(int indice){
		this.retas.remove(indice);
	}
	
}
