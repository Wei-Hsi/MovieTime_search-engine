
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.select.Elements;

public class Fetch/* extends Thread */ {
	private String url;
	private String content;
	private URL urlObject;
	private HttpURLConnection conn;
	private int statusCode;
	private String statusMessage;

	private WordCounter wordCounter;
	private TagFinder tagFinder;

	public Fetch() {
		this.content = "";
	}

	public Fetch setURL(String url) {
		this.url = url;
		return this;
	}

	public String getURL() {
		return this.url;
	}

	public Fetch(String url) throws Exception {
		/* Debug */
//		System.out.println("> Fetch(" + url + ")");
		/* Debug */
		this.content = "";
		this.url = url;
		try {
			this.urlObject = new URL(this.url);
		} catch (java.net.MalformedURLException e) {
			System.err.println(e.getClass() + ": " + this.url);
			return;
		}
		this.conn = (HttpURLConnection) urlObject.openConnection();
		this.conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		this.statusCode = this.conn.getResponseCode();
		this.statusMessage = this.conn.getResponseMessage();
		if (this.statusCode / 100 > 2) {
			/* Debug */
			System.err.println(
					"> HTTPStatusException(" + this.url + ", " + this.statusCode + ", " + this.statusMessage + ")");
			/* Debug */
			throw new HTTPStatusException(this.statusCode, this.statusMessage);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String retVal = "";
		String line = null;
		while ((line = br.readLine()) != null) {
			retVal = retVal + line;
		}
		this.content = retVal;
		this.conn.disconnect();
//		System.out.println(this.content);
	}

//	public void connect() throws Exception {
//		this.conn = (HttpURLConnection) urlObject.openConnection();
//		this.conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
//		this.statusCode = this.conn.getResponseCode();
//		this.statusMessage = this.conn.getResponseMessage();
//		if (this.statusCode / 100 > 2) {
//			/* Debug */
//			System.err.println(
//					"> HTTPStatusException(" + this.url + ", " + this.statusCode + ", " + this.statusMessage + ")");
//			/* Debug */
//			throw new HTTPStatusException(this.statusCode, this.statusMessage);
//		}
//		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//		String retVal = "";
//		String line = null;
//		while ((line = br.readLine()) != null) {
//			retVal = retVal + line;
//		}
//		this.content = retVal;
//	}
//
//	public void run() {
//		try {
//			connect();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.err.println(e.getMessage());
//			return;
//		}
//	}

	public String getContent() {
		/* Debug */
//		System.out.println("> " + this + ".getContent()");
		/* Debug */
		return content;
	}

	public int countKeyword(Keyword k) {
		/* Debug */
//		System.out.println("> " + this + ".countKeyword(" + keyword + ")");
		/* Debug */
		if (this.wordCounter == null) {
			this.wordCounter = new WordCounter(this);
		}
		return this.wordCounter.countKeyword(k);
	}

	public Elements select(String tagQuery) {
		/* Debug */
//		System.out.println("> " + this + ".select(" + tagQuery + ")");
		/* Debug */
		if (this.tagFinder == null) {
			this.tagFinder = new TagFinder(this);
		}
		return this.tagFinder.select(tagQuery);
	}

	@Override
	public String toString() {
		return "Fetch[" + this.url + "]";
	}

	public class HTTPStatusException extends IOException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public HTTPStatusException(int code, String message) {
			super("HTTP Status: " + code + " " + message);
		}
	}

}
