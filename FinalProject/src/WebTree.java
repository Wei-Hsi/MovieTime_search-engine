
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
		/* the loop */
		ArrayList<Thread> t = new ArrayList<Thread>();
//		Thread[] t = new Thread[startNode.children.size()];
		int i = 0;
		for (WebNode child : startNode.children) {
			setPostOrderScore(child, keywords);
			if (!child.isScored()) {
				child.setKeywords(keywords);
				t.add(child);
				t.get(i).start();
			}
			i++;
		}
		for (int j = 0; j < t.size(); j++) {
			try {
				t.get(j).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/* the loop */
		if (!startNode.isScored()) {
			startNode.setNodeScore(keywords);
		}
	}
}