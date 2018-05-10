package org.bildit.ba.phonebook.dto;

/**
 * @author Maja Vasilic
 * */
public class Contacts {
	private int contactID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String address;
	private String gender;
	private int userID;

	public Contacts() {

	}

	public Contacts(int contactsID, String firstName, String lastName, String gender,
			String email, String phoneNumber, String address, int userID) {
		super();
		this.contactID = contactsID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getUserID() {
		return userID;
	}

	public int getContactID() {
		return contactID;
	}

}
