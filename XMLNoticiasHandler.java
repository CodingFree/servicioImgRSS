package servicioImgRSS;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLNoticiasHandler extends DefaultHandler {

	boolean doRead = false;
	boolean currentElement = false;
	String currentValue = null;
	private StringBuilder sb;

	// Not used yet
	private String result = "";
	private Attributes atributes;

	public XMLNoticiasHandler() {
		this.result = "";
	}

	public void startElement(String nameSpace, String localName,
			String fullName, Attributes atrs) {

		if (localName.equalsIgnoreCase("item")) {
			this.doRead = true;
		}

		currentElement = true;
		this.sb = new StringBuilder();
		this.atributes = atrs;

		if (this.doRead) {
			if (localName.equalsIgnoreCase("title")) {
				this.result += ("<articulo>");
				this.result += ("<titulo>");
			} else if (localName.equalsIgnoreCase("description")) {
				this.result += ("<descripcion>");
			} else if (localName.equalsIgnoreCase("pubDate")) {
				this.result += ("<fecha>");

			} else if (localName.equalsIgnoreCase("category")) {
				this.result += ("<categoria>");
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (this.doRead) {
			sb.append(new String(ch, start, length));
		}
	}
	
	private String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (this.doRead) {
			currentElement = false;
			
			if (localName.equalsIgnoreCase("guid")) {
				result = this.replaceLast(result, "<articulo>", "<articulo id=\""+sb.toString()+"\">");
			}else if (localName.equalsIgnoreCase("title")) {
				StringBuilder salida = new StringBuilder();
				salida.append( "<![CDATA[");
				salida.append(sb.toString());
				salida.append("]]></titulo>\n");;
				result += salida.toString();
			} else if (localName.equalsIgnoreCase("category")) {
				StringBuilder salida = new StringBuilder();
				salida.append( "<![CDATA[");
				salida.append(sb.toString());
				salida.append("]]></categoria>\n");;
				result += salida.toString();
			} else if (localName.equalsIgnoreCase("pubDate")) {
				StringBuilder salida = new StringBuilder();
				salida.append( "<![CDATA[");
				salida.append(sb.toString());
				salida.append("]]></fecha>\n");;
				result += salida.toString();
			} else if (localName.equalsIgnoreCase("description")) {
				StringBuilder salida = new StringBuilder();
				salida.append( "<![CDATA[");
				salida.append(sb.toString());
				salida.append("]]></descripcion></articulo>\n");
				result += salida.toString();
				this.doRead = false;
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
