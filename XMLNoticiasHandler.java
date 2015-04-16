package servicioImgRSS;

import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class XMLNoticiasHandler extends DefaultHandler {

	private String result;
	
	public XMLNoticiasHandler() {
		// Constructor de la clase
	}

	// Demás metodos del Parser SAX
	
	public String getResult() {
	    return result.toString();
	}
}
