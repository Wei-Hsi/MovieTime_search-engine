

import java.io.IOException;
import java.util.ArrayList;

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

}
