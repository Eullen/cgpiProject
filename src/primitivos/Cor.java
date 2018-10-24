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
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color toColor(){
        return new Color((double)r, (double)g, (double)b, 1);
    }
}
