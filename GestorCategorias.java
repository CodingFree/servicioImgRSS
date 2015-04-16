package servicioImgRSS;
import java.util.ArrayList;
import java.util.List;

public class GestorCategorias {

   public ArrayList <InfoServicioWeb>  lServicios = new ArrayList <InfoServicioWeb>();

   public GestorCategorias( String sFile /* no usado en esta version */ ){
   
   // IMPORTANTE: 
   // En este versión se añaden los servicios WEB directamente.
   // En la práctica 5 se se obtendrá esta información de un fichero XML de configuración y se
   // usará la API DOM para la lectura de la información de los servicios WEB.
   
        InfoServicioWeb iw;

        iw = new InfoServicioWeb("Portada","http://news.google.es/news?output=rss", "XML");
        iw.addCat("A");
		lServicios.add(iw);

        iw = new InfoServicioWeb("Internacional","http://news.google.es/news?output=rss&topic=w", "XML");
        iw.addCat("B");
		lServicios.add(iw);

        iw = new InfoServicioWeb("Nacional","http://news.google.es/news?output=rss&topic=n", "XML");
        iw.addCat("C");
		lServicios.add(iw);

        iw = new InfoServicioWeb("Economia","http://news.google.es/news?output=rss&topic=b", "XML");
        iw.addCat("D");
		lServicios.add(iw);

        iw = new InfoServicioWeb("Deportes","http://news.google.es/news?output=rss&topic=s", "XML");
        iw.addCat("E");
		lServicios.add(iw);

        iw = new InfoServicioWeb("Ciencia","http://news.google.es/news?output=rss&topic=t", "XML");
        iw.addCat("F");
		lServicios.add(iw);

		//---
		// Ejemplo de como definir un servicio basado en un fichero
        // iw = new InfoServicioWeb("Ciencia","file:./files/ciencia_google_news_20_03_2014.xml", "XML");
        // iw.addCat("F");
		// lServicios.add(iw);

		//----------------------------------------------------------------------------------------------
		// Servicios JSON
		//----------------------------------------------------------------------------------------------

        iw = new InfoServicioWeb("Portada","http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=http://estaticos.elmundo.es/elmundo/rss/portada.xml", "JSON");
        iw.addCat("A");
		lServicios.add(iw);			

        iw = new InfoServicioWeb("Internacional","http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=http://estaticos.elmundo.es/elmundo/rss/internacional.xml", "JSON");
        iw.addCat("B");
		lServicios.add(iw);			

        iw = new InfoServicioWeb("Nacional","http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=http://estaticos.elmundo.es/elmundo/rss/espana.xml", "JSON");
        iw.addCat("C");
		lServicios.add(iw);			

        iw = new InfoServicioWeb("Economia","http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=http://estaticos.elmundo.es/elmundo/rss/economia.xml", "JSON");
        iw.addCat("D");
		lServicios.add(iw);			

        iw = new InfoServicioWeb("Deportes","http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=http://estaticos.elmundo.es/elmundodeporte/rss/portada.xml", "JSON");
        iw.addCat("E");
		lServicios.add(iw);			

        iw = new InfoServicioWeb("Ciencia","http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&q=http://estaticos.elmundo.es/elmundo/rss/ciencia.xml", "JSON");
        iw.addCat("F");
		lServicios.add(iw);			
   }

   public List <InfoServicioWeb> getInfoCategoria(String sCodCategoria) {

    ArrayList <InfoServicioWeb>  listaCategoria  = new ArrayList <InfoServicioWeb>();
	       
		for(InfoServicioWeb iw : lServicios ) {			
		
			// System.out.println( "URL: " + iw.getURL() );

			int nCats = iw.getNumCats();
	
			boolean found = false;
			int i = 0;
			while( ( i<nCats ) && !found)
			{		
				String cat = iw.getCat( i );			
				if ( cat.equals( sCodCategoria ) )
				{
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

public static void main( String[] args )
{
	if ( args.length == 0 )
	{
		System.out.println("Usage: GestorCategorias <codigo categoria>");
		System.exit(0);
	}
	
	GestorCategorias gc = new GestorCategorias( null /* debería ser el nombre de un fichero */ );
	
	List <InfoServicioWeb> lista = gc.getInfoCategoria( args[0] );

	for(InfoServicioWeb iw : lista) {				
			System.out.println( "URL: " + iw.getURL() );
	}
} 

}
