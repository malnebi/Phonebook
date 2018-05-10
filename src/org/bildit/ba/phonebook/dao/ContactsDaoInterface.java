package org.bildit.ba.phonebook.dao;

import java.util.ArrayList;

import org.bildit.ba.phonebook.dto.Contacts;
import org.bildit.ba.phonebook.dto.Users;

/**
 * @author Maja Vasilic
 *
 */
public interface ContactsDaoInterface {

	// Doda novi unos u imenik
	/** Enter new contact in phonebook */
	public Contacts addContactToPhonebook(int contactID, String firstName,
			String lastName, String email, String phoneNumber, String address,
			String gender, int userID);

	// Edituje unose u imeniku
	/** Edit contacts in phonebook */
	public boolean updateContact(Contacts contact);

	// Briše unose iz imenika
	/** Delete contacts from phonebook */
	public boolean deleteEntryFromContacts(Contacts contact);

	// Pretraži svoj imenik po imenu / prezimenu
	/** Search phonebook. */
	public Contacts searchContacts(int userID, String column,
			String fieldToSearch);

	// Izlista sve unose iz svog imenika
	/** Returns list of names from phonebook for a specified user. */
	public ArrayList<Contacts> usersContacts(Users user);

	/** Returns true if made space for users Contacts in db */
	public boolean makeCapacityForNewUserInContacts(int userID);

	public void printContact(Contacts contact);
}
