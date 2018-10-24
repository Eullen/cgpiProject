package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import controladores.TipoPrimitivo;

@XmlRootElement
public class Figura {
	
	public List<Object> todosObjetosDesenhados;
	
	public void setObjetosDesenhados(Map<TipoPrimitivo, List<Object>> objetosDesenhados) {
		this.todosObjetosDesenhados = new ArrayList<>();
		objetosDesenhados.entrySet().stream().forEach(e -> {
			e.getValue().forEach((value) -> {
				this.todosObjetosDesenhados.add(value);
			});
		});
	}
}
