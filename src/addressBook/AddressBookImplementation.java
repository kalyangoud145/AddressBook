
package addressBook;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.io.*;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/*Class contains methods implemented by addressBookInterface methods*/
public class AddressBookImplementation implements AddressBookInterface{

	InputScanner use = new InputScanner();
	public static List<Person> list = new ArrayList<Person>();
	ObjectMapper mapper = new ObjectMapper();

	/*add person to the  person list*/
	public  List<Person> addPerson() {
		list.add(addUser());
		for (Person P : list) {
			System.out.println(P.toString());
		}
		return list;
	}
	
	/*Adding information of person and add it in an object
	 * return object of person will give with added information in it
	 */
	private Person addUser() {
		Person person = new Person();
		System.out.println("\n\t\t\tEnter First Name");
		person.setFirstName(use.inputString());
		System.out.println(person.getFirstName());
		System.out.println("\n\t\t\tEnter Last Name");
		person.setLastName(use.inputString());
		System.out.println("\n\t\t\tEnter city");
		person.setCity(use.inputString());
		System.out.println("\n\t\t\tEnter State");
		person.setState(use.inputString());
		System.out.println("\n\t\t\tEnter ZipCode");
		person.setZip(use.inputInteger());
		System.out.println("\n\t\t\tEnter Phone Number");
		person.setPhoneNumber(use.inputString());
		return person;
	}
	
	/*Editing information of existing Person from list */
	static int count = 0;
	public void editPerson() {
		System.out.println("\n\t\t\tEnter first name");
		String firstName = use.inputString();
		for (Person P : list) {
			if (firstName.equals(P.getFirstName())) {
				count++;
				System.out.println("\n\t\t\tData found\n");
				System.out.println("\t\t\t1.Firstname and lastname 2.To edit city,State and zip\n" + "\t\t\t3. To edit Phone Number\n");
				int editChoice = use.inputInteger();
				switch (editChoice) {
				case 1:
					editAddressBookPerson(P, 1);
					break;
				case 2:
					editAddressBookPerson(P, 2);
					break;
				case 3:
					editAddressBookPerson(P, 3);
					break;
				default:
					System.out.println("\t\t\tSomething went wrong\n" + "\t\t\tTry again later");
				}
			} 
		} 
		if(count==0)
			System.out.println("\n\t\t\tData not found");	
	}

	/*Removes data of a person from list*/
	public void deletePerson() throws Exception {
		System.out.println("\n\t\t\tEnter phoneNumber whose data is to be removed");
		String phoneNumber = use.inputString();
		int count = 0;
		List<Person> Remove = new ArrayList<>();
		for (Person P : list) {
			if (phoneNumber.equals(P.getPhoneNumber())) {
				System.out.println("\n\t\t\tData found\n\n\t\t\tData Removed");
				Remove.add(P);
				count++;
			}
		}
		list.removeAll(Remove);
		if (count == 0)
			System.out.println("\n\t\t\tSorry, no such data found");
	}

	/*To search if the person is present or not using phoneNumber of the person as search parameter*/
	public void searchPerson() {
		System.out.println("\n\t\t\tEnter the phoneNumber of the person to be searched");
		String phoneNumber = use.inputString();
		int count = 0;
		List<Person> toSearch = new ArrayList<>();
		for (Person P : list) {
			if (phoneNumber.equals(P.getPhoneNumber())) {
				System.out.println("\n\t\t\tPerson found");
				toSearch.add(P);
				count++;
			}
		}
		if (count == 0)
			System.out.println("\n\t\t\tSorry, no such person is  found in the address book");
	}	

	/*Sorting of list respect to zip*/
	public void sortByZip() {
		Collections.sort(list, new SortByZip());
		for (Person person : list) {
			System.out.println(person.toString());
		}	
	}

	/*Sorting of list respect to First Name*/
	public void sortByName() {
		Collections.sort(list, new SortByName());
		for (Person person : list) {
			System.out.println(person.toString());
		}	
	}

	/*Displaying the list*/
	public void display() {
		for (Person P : list) {
			System.out.println(P.toString());
		}
	}

	/*Editing of state,city,zip  and phone number
	 *  P is the person object
	 *  i is the case for editing address or phone number*/
	private void editAddressBookPerson(Person P, int i) {
		switch (i) {
		case 1:
			System.out.println("\n\t\t\tEnter the FirstName");
			P.setFirstName(use.inputString());
			System.out.println("\n\t\t\tEnter the LastName");
			P.setLastName(use.inputString());
			System.out.println("\n\t\t\tNew Firstname and Lastname are updated");
			break;
		case 2:
			System.out.println("\n\t\t\tEnter the state");
			P.setState(use.inputString());
			System.out.println("\n\t\t\tEnter the city");
			P.setCity(use.inputString());
			System.out.println("\n\t\t\tEnter the ZipCode");
			P.setZip(use.inputInteger());
			System.out.println("\n\t\t\tNew city,State and zip are updated");
			break;
		case 3:
			System.out.println("\n\t\t\tEnter the new Phone Number");
			String phoneNumber = use.inputString();
			P.setPhoneNumber(phoneNumber);
			System.out.println("\n\t\t\tNew Phone Number updated ");
			break;
		}
	}

	/*Saving list data in file */
	public void saveAddressBook(String file) {
		try {
			mapper.writeValue(new File("AddressBook/" + file + ".json"), list);
			System.out.println("\n\t\t\tSaved");
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*Saves the person list in a different file*/
	public void saveAsAddressBook() {
		System.out.println("\n\t\t\tEnter the name of the new file");
		String fileNameNew = use.inputString();
		saveAddressBook(fileNameNew);
		System.out.println("\n\t\t\tData saved in new file");
	}

	/*clears the list from previous address book if new address book is opened*/
	public void closeAddressBook(String existingAddressBook) {
		list.clear();
	}

	/*Reading file from file existingAddressBook is the name of File from which 
	 * data is to read and  throws Exception for file not found*/
	public void read(String existingAddressBook) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("AddressBook/" + existingAddressBook + ".json"));
			String arrayToJson;
			if ((arrayToJson = reader.readLine()) != null) {
				TypeReference<ArrayList<Person>> type = new TypeReference<ArrayList<Person>>() {
				};
				list = objectMapper.readValue(arrayToJson, type);
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
