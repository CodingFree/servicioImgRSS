package servicioImgRSS;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.*;

import javax.xml.parsers.*;

public class GestorCategorias {

	//Constante, para evitar erratas en algún string de los ficheros.
	private final static String nombreFichero = "serviciosRSS.xml";
	public ArrayList<InfoServicioWeb> lServicios = new ArrayList<InfoServicioWeb>();

	// ---------
	// Primera parte de la práctica 5
	// ---------
	public GestorCategorias(String sFile /* fichero de configuracion XML */) {
		Document docServicios = null;
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		factoria.setNamespaceAware(true);

		try {
			DocumentBuilder builder = factoria.newDocumentBuilder();
			// Iniciamos el parseo
			docServicios = builder.parse(sFile);
			
			// Seleccionamos raíz y nodos.
			Element raiz = docServicios.getDocumentElement();
			NodeList nodosHijo = (NodeList) raiz
					.getElementsByTagName("servicio");

			for (int i = 0; i < nodosHijo.getLength(); i++) {
				//Analizamos los nodos.
				Element current = (Element) nodosHijo.item(i);
				String direccion = current.getElementsByTagName("url").item(0)
						.getTextContent();
				String codigoCategoria = current.getElementsByTagName("codCat")
						.item(0).getTextContent();
				
				// Creamos un infoServicioWeb.
				InfoServicioWeb nuevoServ = new InfoServicioWeb("", direccion,
						current.getAttribute("tipo"));
				nuevoServ.addCat(codigoCategoria);

				// Se agrega a la lista de servicios web.
				this.lServicios.add(nuevoServ);
			}

		} catch (IOException e) {
			System.err.println("Error: no existe el fichero "+nombreFichero+". ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<InfoServicioWeb> getInfoCategoria(String sCodCategoria) {

		ArrayList<InfoServicioWeb> listaCategoria = new ArrayList<InfoServicioWeb>();

		for (InfoServicioWeb iw : lServicios) {

			// System.out.println( "URL: " + iw.getURL() );

			int nCats = iw.getNumCats();

			boolean found = false;
			int i = 0;
			while ((i < nCats) && !found) {
				String cat = iw.getCat(i);
				if (cat.equals(sCodCategoria)) {
					listaCategoria.add(iw);
					found = true;
				}

				i++;
			} // while
		} // for

		return listaCategoria;
	}

	// ------------------------------------------------------
	// Programa principal de prueba de la clase
	//

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: GestorCategorias <codigo categoria>");
			System.exit(0);
		}

		GestorCategorias gc = new GestorCategorias(nombreFichero);
		List<InfoServicioWeb> lista = gc.getInfoCategoria(args[0]);

		for (InfoServicioWeb iw : lista) {
			System.out.println("(" + iw.getType() + ") URL: " + iw.getURL());
		}
	}

} // class
