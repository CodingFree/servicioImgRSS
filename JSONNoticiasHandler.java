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
			Reader reader = new InputStreamReader(new URL(sURL).openStream(),  "UTF-8");
			if (reader.ready()) {
				Gson gson = new GsonBuilder().create();
				Response obj = gson.fromJson(reader, Response.class);
			    for (int i = 0; i < obj.responseData.feed.entries.size(); i++) {
				System.out.println(obj.responseData.feed.entries.get(i).title);
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
		//private List<Item> item; // This is for the inner array

		@Override
		public String toString() {
			return responseData + " - " + responseStatus + " (" + responseDetails + ")";
		}
	}

	private class responseData { // This is the inner array class
		private Feed feed;
	}
	
	private class Feed{
		private String title;
		private List<Entry> entries;		
	}
	
	private class Entry{
		private String title;
		private String publishedDate;
		private String contentSnippet;
		private String link; // Guid?
		//private String category;
		
		
	}

}
