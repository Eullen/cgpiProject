package primitivos;

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
	
}
