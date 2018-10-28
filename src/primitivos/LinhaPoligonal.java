package primitivos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorOrder;
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
	

	@Override
	public List<Object> getPontos() {
		if (retas != null ){
			List<Object> pontos = new ArrayList<>();
			pontos.add(retas.get(0).getA());	
			this.retas.forEach(reta -> {
				pontos.add(reta.getB());
			});
			return pontos;
		}
		return new ArrayList<>();
	}
}
