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
	
	private String titulo = "";
	private String descripcion = "";
	private String fecha = "";
	private String categoria = "";

	// Not used yet
	private String result = "";

	public XMLNoticiasHandler() {
		this.result = "";
	}

	public void startElement(String nameSpace, String localName,
			String fullName, Attributes atrs) {

		if (localName.equalsIgnoreCase("item")) {
			this.result += this.titulo + this.descripcion + this.fecha + this.categoria;
			
			this.doRead = true;
			this.titulo ="";
			this.descripcion = "";
			this.categoria = "";
			this.fecha = "";
		}
		
		
		currentElement = true;
		this.sb = new StringBuilder();
		

		if (this.doRead) {
			if (localName.equalsIgnoreCase("title")) {
				this.titulo += "<articulo>";
				this.titulo += ("<titulo>");
			} else if (localName.equalsIgnoreCase("description")) {
				this.descripcion += ("<descripcion>");
			} else if (localName.equalsIgnoreCase("pubDate")) {
				this.fecha += ("<fecha>");
			} else if (localName.equalsIgnoreCase("category")) {
				this.categoria += ("<categoria>");
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
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (this.doRead) {
			currentElement = false;
			
			if (localName.equalsIgnoreCase("guid")) {
				this.titulo = this.titulo.replaceAll("<articulo>", "<articulo id=\""+sb.toString()+"\">");
			}else if (localName.equalsIgnoreCase("title")) {
				this.titulo += "<![CDATA[";
				this.titulo += sb.toString();
				this.titulo += "]]></titulo>";
			} else if (localName.equalsIgnoreCase("category")) {
				this.categoria += "<![CDATA[";
				this.categoria += sb.toString();
				this.categoria += "]]></categoria>";
				this.categoria += "</articulo>";
			} else if (localName.equalsIgnoreCase("pubDate")) {
				this.fecha += "<![CDATA[";
				this.fecha += sb.toString();
				this.fecha += "]]></fecha>";
			} else if (localName.equalsIgnoreCase("description")) {
				this.descripcion += "<![CDATA[";
				this.descripcion += sb.toString();
				this.descripcion += "]]></descripcion>";

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
