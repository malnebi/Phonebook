package org.bildit.ba.phonebook.dto;

import java.util.ArrayList;

public class Users {
	private int userID;
	private String firstName;
	private String lastName;
	private String gender;
	private String emal;
	private String userName;
	private String password;
	private int isAdmin;// in db boolean value is expresed with tiny int
	private ArrayList<Contacts> usersContacts = new ArrayList<>();

	public Users() {

	}

	public Users(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Users(String userName, String password, String emal) {
		this.userName = userName;
		this.password = password;
		this.emal = emal;
	}

	public Users(int userID, String firstName, String lastName, String gender,
			String emal, String userName, String password, int isAdmin) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.emal = emal;
		this.userName = userName;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public int getUserID() {
		return userID;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return emal;
	}

	public void setEmail(String emal) {
		this.emal = emal;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public ArrayList<Contacts> getUsersContacts() {
		return usersContacts;
	}

	public void setUsersContacts(ArrayList<Contacts> usersContacts) {
		this.usersContacts = usersContacts;
	}

}
