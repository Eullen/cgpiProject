package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import controladores.TipoPrimitivo;

@XmlRootElement(name = "Figura")
public class Figura implements Serializable {

	@XmlAnyElement(lax = true)
	public List<Object> todosObjetosDesenhados;

	public void setObjetosDesenhados(Map<TipoPrimitivo, List<Object>> objetosDesenhados) {
		this.todosObjetosDesenhados = new ArrayList<>();
		objetosDesenhados.entrySet().stream().forEach(e -> {
			e.getValue().forEach((value) -> {
				this.todosObjetosDesenhados.add(value);
			});
		});
	}

	@XmlTransient
	public Map<TipoPrimitivo, List<Object>> getObjetosDesenhados(){
		Map<TipoPrimitivo, List<Object>> objetos = new HashMap<>();

		objetos.put(TipoPrimitivo.RETA, new ArrayList<Object>());
		objetos.put(TipoPrimitivo.RETANGULO, new ArrayList<Object>());
		objetos.put(TipoPrimitivo.CIRCULO, new ArrayList<Object>());
		objetos.put(TipoPrimitivo.POLIGONO, new ArrayList<Object>());
		objetos.put(TipoPrimitivo.LINHA_POLIGONAL, new ArrayList<Object>());

		AtomicReference<List<Object>> listaAtual = null;
		this.todosObjetosDesenhados.forEach((obj) -> {
			switch (obj.getClass().getName()){
				case "Reta":
					listaAtual.set(objetos.get(TipoPrimitivo.RETA));
					break;
				case "Poligono":
					listaAtual.set(objetos.get(TipoPrimitivo.POLIGONO));
					break;
				case "Retangulo":
					listaAtual.set(objetos.get(TipoPrimitivo.RETANGULO));
					break;
				case "Circulo":
					listaAtual.set(objetos.get(TipoPrimitivo.CIRCULO));
					break;
			}
			if(listaAtual != null) listaAtual.get().add(obj);
		});
		return objetos;
	}
}
