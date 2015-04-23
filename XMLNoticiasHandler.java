package servicioImgRSS;

import java.io.IOException;
import java.util.List;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class XMLNoticiasHandler extends DefaultHandler {	

	private String result;
	
	public XMLNoticiasHandler() {
		this.result = "";
	}

	// Demás metodos del Parser SAX
	
    public void startElement(String espacioNombres, String nomLocal, String nomCompleto, Attributes atrs) {
    	switch(nomCompleto){
    		case "rss":
    			this.result += "<rss>";
    			break;
    	}
    	//System.out.println("Nombre local:" + nomLocal + " Nombre completo:" + nomCompleto );
		
		for ( int i = 0; i < atrs.getLength(); i++) {
			//System.out.println("    ATRIB. Nombre local: " + atrs.getLocalName(i) );
			//System.out.println("    ATRIB. Tipo:         " + atrs.getType(i) );
			//System.out.println("    ATRIB. Valor:        " + atrs.getValue(i) );
		}
    }
    public void endElement(String espacio, String nomLocal, String nomCompleto) {
    	switch(nomCompleto){
		case "rss":
			this.result += "</rss>";
			break;
		}
    }
    public void characters_b(char[] ch, int inicio, int longitud) {
		String cad = new String( ch, inicio, longitud);
		System.out.println("    Caracteres: " + cad );
		System.out.println("         Inicio:" + inicio + " Longitud:" + longitud );
    }

    public void error(SAXParseException exc) throws SAXException {
		mostrarError( exc, "Se encontró un error");
    }
    public void fatalError(SAXParseException exc) throws SAXException {
		mostrarError( exc, "Se encontró un error fatal");
    }
    private void mostrarError( SAXParseException exc, String aviso )  throws SAXException {
		System.out.println( aviso + ".  Línea:    " + exc.getLineNumber() );
		System.out.println( "URI:     " + exc.getSystemId() );
		System.out.println( "Mensaje: " + exc.getMessage() );
		throw new SAXException(aviso);//
    }
    
	public String getResult() {
	    return result.toString();
	}
}
