package model;

import java.util.Comparator;

public class PhoneNumberComparator implements Comparator<Client>{

	@Override
	public int compare(Client c1, Client c2) {
		return c2.getPhoneNumber().compareTo(c2.getPhoneNumber());
	}

}
