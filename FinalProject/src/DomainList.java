
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class DomainList {

	private ArrayList<String> domains = new ArrayList<String>();

	public DomainList() {
		for (Keyword k : new KeywordList().getKeyword()) {
			HashMap<String, String> q = new GoogleQuery(k.name, 100).query();
			for (Entry<String, String> e : q.entrySet()) {
				URL url;
				try {
					url = new URL(e.getValue());
				} catch (MalformedURLException e1) {
					continue;
				}
				domains.add(url.getHost());
			}
		}
	}

	public ArrayList<String> getDomains() {
		return this.domains;
	}

}
