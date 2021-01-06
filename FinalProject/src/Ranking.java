import java.util.ArrayList;

public class Ranking {

	public Ranking(String[][] array) {
		quickSort(array, 0, array.length - 1);
	}

	public static void rank(String[][] array) {
//		ArrayList<String[]> l = new ArrayList<String[]>();
//		for (int i = 0; i < array.length; i++) {
//			if (array[i][0] != null && array[i][1] != null && array[i][2] != null) {
//				l.add(array[i]);
//			}
//			System.out.println(array[i][0] + "," + array[i][1] + "," + array[i][2]);

//		}
//		array = l.toArray(new String[l.size()][3]);
		quickSort(array, 0, array.length - 1);
	}

	public static void quickSort(String[][] arr, int leftest, int rightest) {
		if (leftest < rightest) {
			String[] pivot = arr[rightest];
			int i = leftest;
			int j = rightest;
			while (i < j) {
				while (-Double.parseDouble(arr[i][2]) < -Double.parseDouble(pivot[2])) {
					i++;
				}
				while (-Double.parseDouble(arr[j][2]) >= -Double.parseDouble(pivot[2])) {
					if (--j < 0) {
						j = leftest;
						break;
					}
				}
				if (i >= j) {
					break;
				}
				String[] temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
			String[] temp = arr[rightest];
			arr[rightest] = arr[i];
			arr[i] = temp;
			quickSort(arr, leftest, i - 1);
			quickSort(arr, i + 1, rightest);
		}
	}
}
