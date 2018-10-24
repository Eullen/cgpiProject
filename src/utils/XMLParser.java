package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XMLParser<T>{
	private File file;
	
	public XMLParser(String src) throws FileNotFoundException, IOException, Exception{
		this.file = new File(src);
	}
	
	public T toObject(String className) throws JAXBException {
	    JAXBContext jaxbContext       = JAXBContext.newInstance(className);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    @SuppressWarnings("unchecked")
		T dc = (T) jaxbUnmarshaller.unmarshal(this.file);
	    return dc;
	} 
	
	
	
	
	
	public void saveFile(T object) throws JAXBException {
		JAXBContext jaxbContext   = JAXBContext.newInstance(object.getClass());
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(object, System.out);
	    jaxbMarshaller.marshal(object, this.file);
	} 
}
