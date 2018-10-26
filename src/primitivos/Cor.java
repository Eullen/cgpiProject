package primitivos;

import javafx.scene.paint.Color;

import javax.xml.bind.annotation.XmlElement;

public class Cor {
    @XmlElement(name = "R")
    int r;
    @XmlElement(name = "G")
    int g;
    @XmlElement(name = "B")
    int b;

    public Cor(){ }

    public Cor(double red, double green, double blue){    	
    	this.r = (int)(red * 255 );
        this.g = (int)(green * 255 );
        this.b = (int)(blue * 255 );
    }

    public Color toColor(){
        return Color.rgb(r, g, b,1);
    }
}
