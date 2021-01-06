import java.util.HashMap;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	public String searchKeyword;
	public String url;
	public String content;
	public HashMap<String, String> searchResult;
	public int num;
	private WebPage queryPage;

	public GoogleQuery(String searchKeyword, int num) {
		/* Debug */
		System.out.println("> GoogleQuery(" + searchKeyword + ", " + num + ")");
		/* Debug */
		this.searchKeyword = searchKeyword;
		this.num = num;
		this.url = "http://www.google.com/search?q=" + searchKeyword.replace(" ", "+") + "&oe=utf8&num=" + this.num;
		queryPage = new WebPage(this.url, this.searchKeyword);
	}

	public GoogleQuery(String searchKeyword) {
		this(searchKeyword, 100);
	}

	public HashMap<String, String> query() {
		/* Debug */
		System.out.println("> " + this + ".query()");
		/* Debug */
		try {
			queryPage.toFetch();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		HashMap<String, String> retVal = new HashMap<String, String>();
		Elements lis = queryPage.getFetch().select("div").select(".kCrYT");
		for (Element li : lis) {
			try {
				String citeUrl = li.select("a").get(0).attr("href").replace("/url?q=", "");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				System.out.println(title + ", " + citeUrl);
				retVal.put(title, citeUrl);
			} catch (IndexOutOfBoundsException e) {
//              e.printStackTrace();
			}
		}
		this.searchResult = retVal;
		return retVal;
	}

	public String toString() {
		return "GoogleQuery [" + this.searchKeyword + "]";
	}
}