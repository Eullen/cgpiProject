package primitivos;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

import javafx.scene.paint.Color;

@XmlRootElement(name = "Poligono")
public class Poligono {
	protected List<Reta> retas;
	private Color cor;
	private Reta reta; 
	
	public Poligono() { 
		this.retas = new ArrayList<>();
		this.reta  = new Reta();
	}

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
		this.cor = cor.toColor();
	}

	@XmlAnyElement(lax = true)
	public List<Object> getPontos() {
		if (retas != null ){
			List<Object> pontos = new ArrayList<>();
				
			this.retas.forEach(reta -> {
				pontos.add(reta.getA());
			});
			return pontos;
		}
		return new ArrayList<>();
	}
	
	@XmlElement(name="Ponto")
	public Ponto getPonto(){
		return null;
	}
	
	public void setPonto(Ponto ponto) {
		if (this.reta != null){
			if (reta.getA() == null){
				reta.setA(ponto);
			}else{
				if (reta.getB() == null){
					// Tem somente o a da reta, mas vai construir nova
					reta.setB(ponto);
				}else{
					// Tem reta, mas vai construir nova
					reta.setA(reta.getB());
					reta.setB(ponto);
				}	
				this.retas.add(new Reta(reta.getA(), reta.getB()));
			}
		}
	}

	public void adicionarUltimaReta() {
		this.retas.add(new Reta(this.retas.get(this.retas.size()-1).getB(), this.retas.get(0).getA()));
	}
}
