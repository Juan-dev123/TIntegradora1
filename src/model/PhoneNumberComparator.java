package model;

import java.util.Comparator;

public class PhoneNumberComparator implements Comparator<Client>{

	@Override
	/**
	 * It compares to client by the phone number
	 * @param c1 The client one
	 * @param c2 The client two
	 * @return A number major than 0 if the client one is major than the client two. A number minor than 0 if the client one is minor. 0 if they are equals
	 */
	public int compare(Client c1, Client c2) {
		return c2.getPhoneNumber().compareTo(c1.getPhoneNumber());
	}

}
