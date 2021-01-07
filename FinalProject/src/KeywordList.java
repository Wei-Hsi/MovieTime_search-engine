
import java.util.ArrayList;

public class KeywordList {

	private ArrayList<Keyword> lst = new ArrayList<Keyword>();

	public KeywordList() {
		lst.add(new Keyword("影城", 50));
		lst.add(new Keyword("上映日期", 3));
		lst.add(new Keyword("訂票", 30));
		lst.add(new Keyword("主演", 1));
		lst.add(new Keyword("數位", 30));
		lst.add(new Keyword("2D", 10));
		lst.add(new Keyword("3D", 10));
		lst.add(new Keyword("現正熱映", 3));
		lst.add(new Keyword("維基百科", -100));
	}

	public ArrayList<Keyword> getList() {
		return this.lst;
	}
}
