package servicioImgRSS;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servicioImgRSS.InfoServicioWeb;

public class GestorCategorias {
	private ArrayList<InfoServicioWeb> cats;

	/**
	 *
	 * @param sFile
	 *            path al fichero <i>.ini</i> con informaci?n sobre los
	 *            servicios web asociados a cada categor?a.
	 */
	public GestorCategorias(String sFile) {
		cats = new ArrayList<InfoServicioWeb>();

		/** Configuración de lectura **/
		Path configuracion = Paths.get(sFile);
		Charset charset = Charset.forName("ISO-8859-1");

		/** Patrones de busqueda, precompilados **/
		Pattern patronCat = Pattern.compile("^\\[.*\\]$");
		Pattern pattern = Pattern.compile("^(codCat|nombre|url|tipo):(.*)");

		try {
			List<String> lines = Files.readAllLines(configuracion, charset);
			if (lines.size() == 0) {
				throw new NullPointerException();
			}
			InfoServicioWeb servicio = new InfoServicioWeb();

			for (String line : lines) {
				// Cada vez que se encuentran los corchetes, se crea una
				// categoría.
				Matcher matcherCat = patronCat.matcher(line);
				if (matcherCat.find()) {
					if (validarServicio(servicio)) {
						// Se agrega la anterior siempre y cuando no sea la
						// inicial (que estaria vacia).
						cats.add(servicio);
					}

					// Preparamos un nuevo servicio.
					servicio = new InfoServicioWeb();
				} else {
					// Debería ser más sencillo dado que sabemos que el campo
					// existe.
					// Lo que quiero decir es que no debería hacer falta ninguna
					// comprobación en este punto.
					// Quizás una manera de resolverlo sería usando reflection.
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						// Quitamos espacios, por si acaso.
						String campo = matcher.group(1).trim();
						String valor = matcher.group(2).trim();
						if (new String("codCat").equals(campo)) {
							servicio.addCat(valor);
						} else if (new String("nombre").equals(campo)) {
							servicio.setNombreServicioWeb(valor);
						} else if (new String("url").equals(campo)) {
							servicio.setURL(valor);
						} else if (new String("tipo").equals(campo)) {
							servicio.setType(valor);
						}
					}
				}
			}

			if (!cats.contains(servicio)) {
				// Por si queda un último servicio sin agregar.
				if (validarServicio(servicio)) {
					cats.add(servicio);
				}
			}
		} catch (IOException | IllegalArgumentException e) {
			System.out.println(e);
		}
	}

	/**
	 *
	 * @param sCodCategoria
	 * @return informacion de los servicios web asociados directamente a
	 *         <i>sCodCategoria</i> y a sus subcategor?as. No devolver?
	 *         informaci?n repetida.
	 * 
	 */
	public List<InfoServicioWeb> getInfoCategoria(String sCodCategoria) {
		//Si quisiesemos recorrer un subconjunto de categorías podría ser así:
		//"^"+sCodCategoria+".*$"
		Pattern patronCat = Pattern.compile("^"+sCodCategoria+".*$", Pattern.CASE_INSENSITIVE );
		
		List<InfoServicioWeb> lista = new ArrayList<InfoServicioWeb>();
		Iterator<InfoServicioWeb> iterator = this.cats.iterator();
		
		while (iterator.hasNext()) {
			InfoServicioWeb categoria = iterator.next();
			String codigo = categoria.getCat(0).trim();
			Matcher matcher = patronCat.matcher(codigo);
			boolean encontrado = matcher.find();
			if (encontrado) {
				lista.add(categoria);
			}

		}
		return lista;
	}

	private boolean validarServicio(InfoServicioWeb servicio) {
		if (servicio.getNombreServicioWeb() != "") {
			return true;
		}
		return false;
	}
}
