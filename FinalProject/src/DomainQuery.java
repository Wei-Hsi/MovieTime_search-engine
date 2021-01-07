import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class DomainQuery extends Thread {
	private ArrayList<String> domains;
	private Keyword k;

	public ArrayList<String> getDomains() {
		return domains;
	}

	public DomainQuery(ArrayList<String> domains, Keyword k) {
		this.domains = domains;
		this.k = k;
	}

	@Override
	public void run() {
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
