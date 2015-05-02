package servicioImgRSS;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

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

		// JSONNoticiasHandler nhJSON = null;

		// Enacabezado del XML de salida

		// Crear Parser SAX
		SAXParserFactory factoria = SAXParserFactory.newInstance();
		factoria.setNamespaceAware(true);
		SAXParser parser = factoria.newSAXParser();

		// Recorre la lista de servicios WEB que ofrecen noticias de la
		// categor�a pasada como par�metro
		for (InfoServicioWeb iw : gc.getInfoCategoria(sCodCat)) {
			//this.result = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<noticias>";

			if (iw.getType().equals("XML")) // Solo los servicios XML
			{
				nhXML = new XMLNoticiasHandler();
				parser.parse(iw.getURL(), nhXML);
				result += nhXML.getResult();
			}
			// } else // Suponemos que es JSON
			// {
			// // Pr�ctica 4
			// }
			//result += "</noticias>";
			System.out.println(result);
		}

		// Cierre del XML de salida

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
			String nombreFichero = String.valueOf(System.currentTimeMillis() / 1000L)+".xml";
			ficheroSalida = new FileOutputStream(nombreFichero);
			flujoSalida = new PrintStream(ficheroSalida);

			String XML_Transformado = gn.getNoticiasXML(args[0]);

			flujoSalida.println(XML_Transformado);
			flujoSalida.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error writing to file");
		}
	}

}