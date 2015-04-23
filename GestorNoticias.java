package servicioImgRSS;

import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class GestorNoticias {

	public GestorCategorias gc;

	public GestorNoticias(String iniFile) {
		gc = new GestorCategorias(iniFile);
	}

	public String getNoticiasXML(String sCodCat) throws Exception {
		XMLNoticiasHandler nhXML = null;

		// JSONNoticiasHandler nhJSON = null;

		// Enacabezado del XML de salida
		String result = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<noticias>";

		// Crear Parser SAX
		SAXParserFactory factoria = SAXParserFactory.newInstance();
		factoria.setNamespaceAware(true);
		SAXParser parser= factoria.newSAXParser();

		// Recorre la lista de servicios WEB que ofrecen noticias de la
		// categoría pasada como parámetro
		for (InfoServicioWeb iw : gc.getInfoCategoria(sCodCat)) {
			System.out.println("La url es: "+iw.getURL());

			if (iw.getType().equals("XML")) // Solo los servicios XML
			{
				nhXML = new XMLNoticiasHandler();
				parser.parse(iw.getURL(), nhXML);
				result += nhXML.getResult();
			}
//			} else // Suponemos que es JSON
//			{
//				// Práctica 4
//			}
		}

		result += "</noticias>"; // Cierre del XML de salida
		System.out.println(result);
		return result;
	}

	public String getNoticiasHTML() {
		return "";
	}

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Falta el codigo de categoria...");
			System.exit(0);
		}else{
			System.out.println("La categoría es: "+ args[0]);
		}

		GestorNoticias gn = new GestorNoticias(null);

		FileOutputStream ficheroSalida;
		PrintStream flujoSalida;

		try {
			ficheroSalida = new FileOutputStream("XML_transform.xml");
			flujoSalida = new PrintStream(ficheroSalida);

			String XML_Transformado = gn.getNoticiasXML(args[0]);

			flujoSalida.println(XML_Transformado);
			flujoSalida.close();
			System.out.println("Salida exitosa");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error writing to file");
		}
	}

}