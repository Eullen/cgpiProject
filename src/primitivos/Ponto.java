package primitivos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gui.TelaPrincipal;
import javafx.geometry.Point2D;

@XmlRootElement(name = "Ponto")
public class Ponto {

	private Point2D ponto;
	private Double xp;
	private Double yp;
	
	Ponto() {
		this.ponto = new Point2D(0, 0);
		xp = null;
		yp = null;
	}
	
	

	public Ponto(double x, double y) {
		this.ponto = new Point2D(x,y);
	}

	Ponto(Ponto p) {
		this.ponto = new Point2D(p.getx(),p.gety());
	}

	//@XmlElement(name="x")
	public double getx() {
		return this.ponto.getX();
	}
		
	//@XmlElement(name="y")
	public double gety() {
		return this.ponto.getY();
	}
	
	@XmlElement(name = "x")
	public double getXNormalizada(){
		return getx()/TelaPrincipal.LARGURA_CANVAS;
	}
	public void setXNormalizada(double xPorc){
		this.xp = xPorc*TelaPrincipal.LARGURA_CANVAS;
		if (this.yp != null){
			this.ponto = new Point2D(this.xp, this.yp);
		}
	}
	
	@XmlElement(name = "y")
	public double getYNormalizada(){
		return gety()/TelaPrincipal.ALTURA_CANVAS;
	}
	
	public void setYNormalizada(double yPorc){
		this.yp = yPorc*TelaPrincipal.ALTURA_CANVAS;
		if (this.xp != null){
			this.ponto = new Point2D(this.xp, this.yp);
		}	
	}
}