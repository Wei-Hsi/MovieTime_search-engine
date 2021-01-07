
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.nodes.Element;

public class WebNode extends Thread {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;
	private boolean scored = false;
	private ArrayList<Keyword> keywords;

	public ArrayList<String> subLinks = new ArrayList<String>();

	public WebNode(WebPage webPage) {
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
//		this.nodeScore = -1;
	}

	public double getNodeScore() {
		return nodeScore;
	}

	public String getTitle() {
		return webPage.name;
	}

	public String getUrl() {
		return webPage.url;
	}

	@Override
	public String toString() {
		return "WebNode[" + this.webPage.name + "]";
	}

	public boolean isScored() {
		return this.scored;
	}

	public void toSubPage() throws Exception {
		/* Debug */
		System.out.println("[" + this.getClass() + "] " + this + ".toSubPage()\n"); // Debug
		/* Debug */
		this.webPage.toFetch();
//		System.out.print(this.webPage.getFetch());
		/* Debug */
		double i = 0; // Debug
		/* Debug */
		for (Element tag : this.webPage.getFetch().select("a")) {
			/* Debug */
			if (i++ > 5) { // Debug
				return; // Debug
			} // Debug
			/* Debug */
			String href = tag.attr("href");
			if (subLinks.contains(href)) {
				continue;
			}
			subLinks.add(href);
			href = urlHandler(webPage.url, href);
			if (subLinks.contains(href)) {
				continue;
			}
			subLinks.add(href);
			String text = tag.text();
			addChild(new WebNode(new WebPage(href, text)));
		}
	}

	public static String urlHandler(String mainURL, String subURL) {
		URL url;
		try {
			url = new URL(subURL);
			return url.toString();
		} catch (Exception exception) {
			try {
				url = new URL(mainURL);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
			String path = url.getPath();
			String host = url.toString().replace(path, "");
			String urlString;
			if (subURL.indexOf("/") == 0) {
				path = subURL;
			} else if (subURL.indexOf(".") == 0) {
				if (subURL.charAt(1) == '.' && subURL.charAt(2) == '/') {
					int times = 0;
					int fromIdx = 0;
					int foundIdx = -1;
					while ((foundIdx = subURL.indexOf("../", fromIdx)) != -1) {
						times++;
						fromIdx = foundIdx + "../".length();
					}
					boolean isFileDeepest = (path.lastIndexOf(".") > path.lastIndexOf("/"))
							&& (path.charAt(path.lastIndexOf("/") + 1) != '.');
					for (int i = 0; i < times + (isFileDeepest ? 1 : 0); i++) {
						path = path.replace(path.substring(path.lastIndexOf("/")), "");
					}
					path += subURL.substring(fromIdx - 1);
				} else if (subURL.indexOf("/") == 1) {
					path = path.replace(path.substring(path.lastIndexOf("/")), "") + subURL.substring(1);
				}
			} else {
				path = "/" + subURL;
			}
			urlString = host + path;
			return urlString;
		}
	}

	public void addChild(WebNode child) {
		this.children.add(child);
		child.parent = this;
	}

	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		System.out.println(this + ".setNodeScore");
		this.scored = true;
		this.nodeScore = 0;
		webPage.setScore(keywords); // Mutli Thread
		nodeScore = webPage.score;
		for (WebNode child : children) {
			nodeScore += child.nodeScore;
		}
	}

	public void setKeywords(ArrayList<Keyword> keywords) {
		this.keywords = keywords;
	}

	public void run() {
		try {
			setNodeScore(this.keywords);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
