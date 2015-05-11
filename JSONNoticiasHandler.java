/**
 * 
 */
package servicioImgRSS;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Tyrion
 *
 */
public class JSONNoticiasHandler {

	private String result = "";

	/**
	 * 
	 */
	public JSONNoticiasHandler(String sURL) {
		try {
			Reader reader = new InputStreamReader(new URL(sURL).openStream(),
					"UTF-8");
			if (reader.ready()) {
				Gson gson = new GsonBuilder().create();
				Response obj = gson.fromJson(reader, Response.class);
				for (int i = 0; i < obj.responseData.feed.entries.size(); i++) {
					Entry entrada = obj.responseData.feed.entries.get(i);
					result += "<articulo id=\"" + entrada.link
							+ "\"><titulo><![CDATA[" + entrada.title
							+ "]]></titulo>";
					result += "<descripcion><![CDATA[" + entrada.contentSnippet
							+ "]]></descripcion>";
					result += "<fecha><![CDATA[" + entrada.publishedDate
							+ "]]></fecha>";
					if (entrada.category != null) {
						result += "<categoria><![CDATA[" + entrada.category
								+ "]]></cateoria>";
					} else {
						result += "<categoria/></articulo>";
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private class Response { // This class should match your json object
								// structure
		private String responseDetails = null;
		private int responseStatus;
		private responseData responseData;
	}

	private class responseData { // This is the inner array class
		private Feed feed;
	}

	private class Feed {
		private String title;
		private List<Entry> entries;
	}

	private class Entry {
		private String title;
		private String publishedDate;
		private String contentSnippet;
		private String link; // Guid?
		private String category;

	}

	public String getResult() {
		return result.toString();
	}
}
