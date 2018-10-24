package utils;

import primitivos.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XMLParser<T>{
	private File file;

	public XMLParser(File file) throws FileNotFoundException, IOException, Exception{
		this.file = file;
	}
	
	public XMLParser(String src) throws FileNotFoundException, IOException, Exception{
		this.file = new File(src);
	}
	
	public T toObject(Class[] classList) throws JAXBException {
	    JAXBContext jaxbContext       = JAXBContext.newInstance(classList);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    @SuppressWarnings("unchecked")
		T dc = (T) jaxbUnmarshaller.unmarshal(this.file);
	    return dc;
	}
	
	
	public void saveFile(T object, Class[] classList) throws JAXBException {
		//JAXBContext jaxbContext   = JAXBContext.newInstance(object.getClass());
		JAXBContext jaxbContext   = JAXBContext.newInstance(classList);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(object, System.out);
	    jaxbMarshaller.marshal(object, this.file);
	} 
}
