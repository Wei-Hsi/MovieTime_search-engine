
import java.util.ArrayList;

public class DomainList {

	private ArrayList<String> domains = new ArrayList<String>();

	public DomainList() {

		ArrayList<Thread> t = new ArrayList<Thread>();
//		Thread[] t = new Thread[startNode.children.size()];
		int i = 0;

		for (Keyword k : new KeywordList().getList()) {
			if (i > 0) {
				break;
			}
			DomainQuery thread = new DomainQuery(domains, k);
			t.add(thread);
			t.get(i).start();
			i++;
		}
		for (int j = 0; j < t.size(); j++) {
			try {
				t.get(j).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int aa = 0; aa < t.size(); aa++) {
			domains.addAll(((DomainQuery) t.get(aa)).getDomains());
		}
	}

	public ArrayList<String> getList() {
		return this.domains;
	}

}
