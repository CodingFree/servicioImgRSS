package servicioImgRSS;

import java.util.ArrayList;

public class InfoServicioWeb {
	private String categoria;
	private String nombreServicioWeb;
	private String urlServicioWeb;
	private String tipoServicioWeb;

	public InfoServicioWeb() {
		nombreServicioWeb = "";
		urlServicioWeb = "";
		tipoServicioWeb = "";
	}


	public InfoServicioWeb(String nombreServicioWeb, String urlServicioWeb, String tipoServicioWeb) {
		this.nombreServicioWeb=nombreServicioWeb;
		this.urlServicioWeb=urlServicioWeb;
		this.tipoServicioWeb=tipoServicioWeb;
	}


	public int getNumCats(){
		return 0;
	}
	
	public String getCat(int i) {
		return this.categoria;
	}


	public void addCat(String categoria) {
		this.categoria = categoria;
	}


	public String getNombreServicioWeb() {
		return this.nombreServicioWeb;
	}


	public void setNombreServicioWeb(String nombreServicioWeb) {
		this.nombreServicioWeb=nombreServicioWeb;
	}


	public String getURL() {
		return this.urlServicioWeb;
	}

	public void setURL(String url) {
		this.urlServicioWeb=url;
	}

	public String getType() {
		return this.tipoServicioWeb;
	}


	public void setType(String type) {
		this.tipoServicioWeb=type;
	}


}
