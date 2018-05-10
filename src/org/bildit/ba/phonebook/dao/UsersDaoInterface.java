package org.bildit.ba.phonebook.dao;

import java.util.ArrayList;

import org.bildit.ba.phonebook.dto.Users;

/**
 * @author Maja Vasilic
 *
 */

public interface UsersDaoInterface {

	/** Register New User to the Database */
	public Users enterNewUserToDB(String firstName, String lastName,
			String gender, String mail, String userName, String password,
			int isAdmin);

	/** Check if user exists in the database */
	public boolean isAlreadyRegistered(Users user);

	/** Returns true if login succeeded or false if it fails */
	public boolean logIn(String userName, String password );

	/** Removes user from list of online users. */
	public boolean logOut(Users user);

	/** Log out all users from the app. Clear list of online users. */
	public boolean logOutAllUsers();

	/** Check in db if user is admin */
	public boolean isAdmin(Users user);

	/**
	 * Check list of online users every time user makes a choice and if user is
	 * offline don't let him do stuff
	 */
	public boolean isActive(Users user);

	/** Returns list of all users from db */
	public ArrayList<Users> allUsersFromDBList();

	/** Update user in the db table "Users" (change information in Users) */
	public boolean updateUserInDatabase(Users user);

	/** Find user by userName, first or last name, id */
	public Users findUserInDatabase(String userData, String column);

	public void printUser(Users user);

	public boolean deleteUser(Users user);

}
