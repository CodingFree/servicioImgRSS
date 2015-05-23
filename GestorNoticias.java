package piat.servicioImgRSS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class GestorNoticias {

	private String result = "";

	public GestorCategorias gc;

	public GestorNoticias(String iniFile) {
		gc = new GestorCategorias(iniFile);
	}

	public String getNoticiasXML(String sCodCat) throws Exception {
		XMLNoticiasHandler nhXML = null;
		JSONNoticiasHandler nhJSON = null;

		// Crear Parser SAX
		SAXParserFactory factoria = SAXParserFactory.newInstance();
		factoria.setNamespaceAware(true);
		SAXParser parser = factoria.newSAXParser();

		this.result = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<noticias xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation='noticias.xsd'>";

		// Recorre la lista de servicios WEB que ofrecen noticias de la
		// categor√≠a pasada como par√°metro
		for (InfoServicioWeb iw : gc.getInfoCategoria(sCodCat)) {

			if (iw.getType().equalsIgnoreCase("XML")) // Solo los servicios XML
			{
				nhXML = new XMLNoticiasHandler();
				parser.parse(iw.getURL(), nhXML);
				result += nhXML.getResult();
			} else if (iw.getType().equalsIgnoreCase("JSON")) { // Suponemos que
																// es JSON
				nhJSON = new JSONNoticiasHandler(iw.getURL());
				result += nhJSON.getResult();
			}
		}
		result += "</noticias>";

		return result;
	}

	public String getNoticiasHTML() {
		File fXSL;
		File XMLT;
		StreamSource SS;
		String htmlNoticias;
		String respuesta = new String();
		Transformer transformer;
		TransformerFactory tFactory;

		// Procesar el contenido del fichero XML_Transform.xml generado por
		// getNoticiasXML
		// y generar un fichero HTML que se pueda visualizar en el navegador.
		// Para ello
		// se usar· un fichero de transformaciÛn XSL y la clase Trasformer

		try {
			tFactory = TransformerFactory.newInstance();
			fXSL = new File("XMHTML.xsl");
			SS = new StreamSource(fXSL);
			transformer = tFactory.newTransformer(SS);

			StringWriter writer = new StringWriter();
			XMLT = new File("XML_Transform.xml");
			transformer.transform(new StreamSource(XMLT), new StreamResult(
					writer));

			respuesta = writer.toString();
		} catch (Exception e) {
			System.err.println("Exception: " + e);
			respuesta = "HA HABIDO UNA EXCEPCION";

		}

		return respuesta;

	}

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Falta el codigo de categoria...");
			System.exit(0);
		}

		// -------
		// serviciosRSS.xml contiene la lista de servidores
		// -------
		GestorNoticias gn = new GestorNoticias("serviciosRSS.xml");

		FileOutputStream ficheroSalida;
		PrintStream flujoSalida;

		try {
			String nombreFichero = String
					.valueOf(System.currentTimeMillis() / 1000L);

			ficheroSalida = new FileOutputStream(nombreFichero+".xml");
			flujoSalida = new PrintStream(ficheroSalida);

			String XML_Transformado = gn.getNoticiasXML(args[0]);

			flujoSalida.println(XML_Transformado);
			flujoSalida.close();

			// Segunda parte de la pr·ctica 5
			String HTML_Transformado = gn.getNoticiasHTML();
			ficheroSalida = new FileOutputStream(nombreFichero+".html");
			flujoSalida = new PrintStream(ficheroSalida);
			flujoSalida.println(HTML_Transformado);
			flujoSalida.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error writing to file");
		}
	}

}
