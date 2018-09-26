package primitivos;

import java.util.ArrayList;
import java.util.List;

public class Poligono {
	
	List<Reta> retas;
	
	

	public Poligono() {
		this.retas = new ArrayList<>();
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
