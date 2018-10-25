package primitivos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

import javafx.scene.paint.Color;

@XmlRootElement(name = "Poligono")
public class Poligono {
	private List<Reta> retas;
	private Color cor;

	public Poligono() { }

	public Poligono(Color cor) {
		this.retas = new ArrayList<>();
		this.cor = cor;
	}

	@XmlTransient
	public List<Reta> getRetas() {
		return retas;
	}

	public void addReta(Reta reta) {
		this.retas.add(reta);
	}
	public void removerReta(int indice){
		this.retas.remove(indice);
	}

	@XmlTransient
	public Color getCor() {
		return cor;
	}
	public void setCor(Color cor) {
		this.cor = cor;
	}

	@XmlElement(name = "Cor")
	public Cor getCustomColor(){
		return new Cor(this.cor.getRed(), this.cor.getGreen(), this.cor.getBlue());
	}
	public void setCustomColor(Cor cor){
		this.cor = new Color(cor.r, cor.g, cor.b,1);
	}


	@XmlAnyElement(lax = true)
	public List<Ponto> getPontos() {
		List<Ponto> pontos = new ArrayList<>();
		this.retas.forEach(reta -> {
			pontos.add(reta.getA());
			pontos.add(reta.getB());
		});
		return pontos;
	}

	public void setPontos(List<Ponto> pontos) {
		//tem que ajustar aqui
		this.retas = new ArrayList<>();
	}

}
