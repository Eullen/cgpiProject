package primitivos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.scene.paint.Color;

@XmlRootElement(name = "LinhaPoligonal")	
public class LinhaPoligonal extends Poligono{

	public LinhaPoligonal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LinhaPoligonal(Color cor) {
		super(cor);
		// TODO Auto-generated constructor stub
	}
	
	/*
	@XmlAnyElement(lax = true)
	@Override
	public List<Ponto> getPontos() {
		List<Ponto> pontos = new ArrayList<>();
		//pega ponto inicial
		pontos.add(this.retas.get(0).getB());	
		this.retas.forEach(reta -> {
			pontos.add(reta.getB());
		});
		return pontos;
	}
	
	@Override
	public void setPontos(List<Ponto> pontos) {
		//tem que ajustar aqui
		this.retas = new ArrayList<>();
		pontos.forEach(pt->{
			if (pontos.indexOf(pt) != pontos.size()-1) 
				retas.add(new Reta(pt, pontos.get(pontos.indexOf(pt)+1)));
		});
	}
	*/
	
}
