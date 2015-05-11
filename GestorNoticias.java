package servicioImgRSS;

import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
		// categoría pasada como parámetro
		for (InfoServicioWeb iw : gc.getInfoCategoria(sCodCat)) {

			if (iw.getType().equalsIgnoreCase("XML")) // Solo los servicios XML
			{
				nhXML = new XMLNoticiasHandler();
				parser.parse(iw.getURL(), nhXML);
				result += nhXML.getResult();
			} else if(iw.getType().equalsIgnoreCase("JSON")){ // Suponemos que es JSON
				nhJSON = new JSONNoticiasHandler(iw.getURL());
				result += nhJSON.getResult();
			}
		}
		result += "</noticias>";

		return result;
	}

	public String getNoticiasHTML() {
		return "";
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Falta el codigo de categoria...");
			System.exit(0);
		}

		GestorNoticias gn = new GestorNoticias("categorias.ini");

		FileOutputStream ficheroSalida;
		PrintStream flujoSalida;

		try {
			String XML_Transformado = gn.getNoticiasXML(args[0]);

			if (XML_Transformado != "") {
				String nombreFichero = String.valueOf(System
						.currentTimeMillis() / 1000L) + ".xml";
				ficheroSalida = new FileOutputStream(nombreFichero);
				flujoSalida  = new PrintStream(ficheroSalida);

				flujoSalida .println(XML_Transformado);
				
				flujoSalida.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error writing to file");
		}
	}

}
