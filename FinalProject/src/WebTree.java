
import java.io.IOException;
import java.util.ArrayList;

public class WebTree {
	public WebNode root;

	public WebTree(WebPage rootPage) {
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException {
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
		for (WebNode child : startNode.children) {
			setPostOrderScore(child, keywords);
			if (child.nodeScore == -1) {
				child.setNodeScore(keywords);
			}
		}
		if (startNode.nodeScore == -1) {
			startNode.setNodeScore(keywords);
		}
	}
}