package servicioImgRSS;

import java.io.IOException;
import java.util.List;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class XMLNoticiasHandler extends DefaultHandler {

	boolean isStart = false;
	boolean currentElement = false;
	String currentValue = null;
	private String result = "";

	public XMLNoticiasHandler() {
		this.result = "";
	}

	// Dem�s metodos del Parser SAX
	private String parsearNombre(String fullName) {
		String parsedName = "";
		switch (fullName) {
		case "item":
			parsedName = "articulo";
			break;
		case "title":
			parsedName = "titulo";
			break;
		case "description":
			parsedName = "descripcion";
			break;
		case "category":
			parsedName = "categoria";
			break;
		case "pubDate":
			parsedName = "fecha";
			break;
		default:
			parsedName = "";
		}
		return parsedName;

	}

	public void startElement(String nameSpace, String localName,
			String fullName, Attributes atrs) {

		currentElement = true;

		if (localName.equals("item")) {
			/** Start */
			this.isStart = true;
			System.out.println();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (this.isStart) {
			currentElement = false;

			/** set value */
			if(localName.equalsIgnoreCase("item")){
				this.isStart = false;
			}else if (localName.equalsIgnoreCase("title")) {
				System.out.println("title: "+currentValue);
			} else if (localName.equalsIgnoreCase("link")) {
				System.out.println("link: "+currentValue);
			} else if (localName.equalsIgnoreCase("pubDate")) {
				System.out.println("pubDate: "+currentValue);
			} else if (localName.equalsIgnoreCase("description")) {
				System.out.println("Description: "+currentValue);
			}
		}
	}

	@Override
	public void error(SAXParseException exc) throws SAXException {
		mostrarError(exc, "Se encontr� un error");
	}

	@Override
	public void fatalError(SAXParseException exc) throws SAXException {
		mostrarError(exc, "Se encontr� un error fatal");
	}

	private void mostrarError(SAXParseException exc, String warning)
			throws SAXException {
		System.out.println(warning + ".  L�nea:    " + exc.getLineNumber());
		System.out.println("URI:     " + exc.getSystemId());
		System.out.println("Mensaje: " + exc.getMessage());
		throw new SAXException(warning);//
	}

	public String getResult() {
		return result.toString();
	}
}
