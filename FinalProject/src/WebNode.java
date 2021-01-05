

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.nodes.Element;

public class WebNode {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;

	public WebNode(WebPage webPage) {
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}

	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		webPage.setScore(keywords);
		nodeScore = webPage.score;
		for (WebNode child : children) {
			nodeScore += child.nodeScore;
		}
	}

	public void addChild(WebNode child) {
		this.children.add(child);
		child.parent = this;
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
	public void toSubPage() throws Exception {
		/* Debug */
		System.out.println(">>$ [" + this.getClass() + "] " + this + ".toSubPage()\n");
		/* Debug */
		webPage.toFetch();
		System.out.print(webPage.getFetch());
		for (Element tag : webPage.getFetch().select("a")) {
			String href = tag.attr("href"); // 有待整理
//			System.out.println(href);
//			System.out.println();
			href = urlHandler(webPage.url, href);
			String text = tag.text();
			addChild(new WebNode(new WebPage(href, text)));
		}
	}
	public static String urlHandler(String mainURL, String subURL) {
//		System.out.println(mainURL);
//		System.out.println(subURL);
		URL url;
		try {
			url = new URL(mainURL);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		String path = url.getPath();
		String host = url.toString().replace(path, "");
//		System.out.println(path);
//		System.out.println(host);
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
			host = "";
			path = "";
			host = subURL;
		}
		urlString = host + path;
		return urlString;
	}

}
