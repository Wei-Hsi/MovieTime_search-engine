

import java.util.ArrayList;

public class Ranking {

	public ArrayList<WebNode> nodeList = new ArrayList<WebNode>();

	public Ranking(ArrayList<WebNode> nodeList) {
		this.nodeList = nodeList;
		QuickSort(nodeList,0,nodeList.size()-1);
	}
	public ArrayList<WebNode> QuickSort(ArrayList<WebNode> nodeList,int leftest,int rightest) {
		
		if (nodeList.size() < 2) {
			return nodeList;
		} else if (leftest < rightest) {
			WebNode pivotKey = nodeList.get(leftest);
			int i = leftest;
			int j = rightest;

			do {
				do {
					i += 1;
				} while (!(nodeList.get(i).getNodeScore() > pivotKey.getNodeScore()) && i >= rightest);

				do {
					j -= 1;
				} while (!(nodeList.get(j).getNodeScore() < pivotKey.getNodeScore()) && j >= leftest);

				if (i < j) {
					WebNode temp = nodeList.get(i);
					nodeList.set(i, nodeList.get(j));
					nodeList.set(j, temp);
				}
			} while (!(i >= j));
			WebNode temp = nodeList.get(leftest);
			nodeList.set(leftest, nodeList.get(j));
			nodeList.set(j, temp);

			QuickSort(nodeList, leftest, j - 1);
			QuickSort(nodeList, j + 1, rightest);
		}
		return nodeList;
	}


}
