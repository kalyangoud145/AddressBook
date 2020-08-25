package addressBook;
import java.util.Comparator;
/*for sorting of data by using zip in address book*/

public class SortByZip implements Comparator<Person> {
	/* 
	 * Compares the data and returns 0 if the  data is same 
	 * and 1 if first data is greater
	 * and -1 if second data is greater
	 */
	@Override
	public int compare(Person p1, Person p2) {
		if(p1.getZip()==p2.getZip())
			return 0;
		else {
			if(p1.getZip()>p2.getZip()) {
				return 1;
			}else {
				return -1;
			}
		}
	}
}
