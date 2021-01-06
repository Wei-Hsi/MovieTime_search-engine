public class WordCounter {
	private String content;

	public WordCounter(Fetch fetch) {
		this.content = fetch.getContent();
	}

	public WordCounter(String content) {
		this.content = content;
	}

	public int countKeyword(Keyword k) {
		/* Debug */
//		System.out.println("> WordCounter.countKeyword(" + keyword + ")");
		/* Debug */
		content = content.toUpperCase();
		String keyword = k.name.toUpperCase();

		int retVal = 0;
		int fromIdx = 0;
		int found = -1;

		while ((found = content.indexOf(keyword, fromIdx)) != -1) {
			retVal++;
			fromIdx = found + keyword.length();
		}

		System.out.print(" " + retVal);
//		ArrayList<Keyword> keywords = new KeywordList().getKeyword();
//		
//		if (keywords.get(keywords.size() - 1).name.equals(k.name)) {
//			System.out.println();
//		}
		return retVal;
	}
}
