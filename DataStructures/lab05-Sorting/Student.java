//I am the sole author of this file and repository
/**
 * A student holds a collection of identifying information. Such as the entire name, address,
 * campus phone, student inbox, and cell phone.
 */
public class Student { //$ great job!

	private String entireName;
	private String noPrefix;
	private String address;
	private long campusPhone;
	private int studentInbox;
	private long cellPhone;
	private int areaCode;
	 

	/**
	 * Constructs a student with all the data fields
	 * @param name expects the name
	 * @param livingLocation expects the campus address
	 * @param williamsPhone expects their school phone number
	 * @param sInbox expects their student inbox
	 * @param persyPhone expects their cell phone number
	 */
	public Student(String name, String livingLocation, long williamsPhone, int sInbox, String persyPhone) {
		entireName = name;
		address = livingLocation;
		campusPhone = williamsPhone;
		studentInbox = sInbox;
		cellPhone = Long.parseLong(persyPhone);
		doAreaCode(persyPhone);
		doFirstName(name);
	}

	/**
	* Public method that returns the entire name.
	*
	*
	* @return The entirename instance variable.
	*/
	public String getName() {
		return entireName;
	}

	/**
	 * Public method that returns the address
	 *
	 *
	 * @return adress
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Public method that returns the campus phone number
	 *
	 *
	 * @return campusPhone
	 */
	public long getCampusPhone() {
		return campusPhone;
	}

	/**
	 * Public method that returns the inbox
	 *
	 *
	 * @return studentInbox
	 */
	public int inbox() {
		return studentInbox;
	}

	/**
	 * Public method that returns the cell phone number
	 *
	 *
	 * @return cellPhone
	 */
	public long getCellPhone() {
		return cellPhone;
	}

	/**
	 * Public method that returns the area code of their cell phone
	 *
	 * @return integer area code
	 */
	public int getAreaCode() {
		return areaCode;
	}

	/**
	 * Private method that sets the areacode
	 * If their number is less than required length, their area code is -1
	 * to signify they do not have a valid number. Otherwise if it is 10,
	 * take the first 3 digits since there is no international digit. But if it is
	 * 11 or 12, take modified versions.
	 *
	 * @post set areaCode
	 */
	private void doAreaCode(String s) {
		if (s.length() == 10) {
			areaCode = Integer.parseInt(s.substring(0, 3));
		} else if (s.length() == 11) {
			areaCode = Integer.parseInt(s.substring(1, 4));
		} else if (s.length() == 12) {
			areaCode = Integer.parseInt(s.substring(2, 5));
		} else {
			areaCode = -1;
		}
	}

    //$ nice job with these methods!
	/**
	 * Private method that sets the firstName
	 * If their name has a prefix, we look for the second space,
	 * Otherwise we look for the first space
	 *
	 *
	 *
	 * @post set firstName
	 */
	private void doFirstName(String s) {
		String lower = s.toLowerCase();
		if (lower.substring(0, 2).equals("ii") || lower.substring(0, 3).equals("iii") || lower.substring(0, 2).equals("jr")) {
			int first = lower.indexOf(" ");
    		noPrefix = lower.substring(first + 1, lower.length());
		} else {
			noPrefix = lower;
		}
	}
	/**
	 * Public method that returns the name with no prefix
	 *
	 * @return string noPrefix
	 */
	public String getPureName() {
		return noPrefix;
	}
	/**
	* Constructs a string representation of the student.
	* @post returns a string representing list
	*
	* @return A string representing the elements of the list.
	*/

	public String toString() {
		return "\n" + entireName + "\n" + address + "\n" + campusPhone + " " + studentInbox + " " + cellPhone + "\n";
	}

}
