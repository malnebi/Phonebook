package org.bildit.ba.phonebook.main;

import java.util.ArrayList;
import java.util.Scanner;

import org.bildit.ba.phonebook.dao.ContactsDaoImplementation;
import org.bildit.ba.phonebook.dao.UsersDaoImplementation;
import org.bildit.ba.phonebook.dao.Utilities;
import org.bildit.ba.phonebook.dto.Contacts;
import org.bildit.ba.phonebook.dto.Users;

public class PhonebookApp {

	static Scanner input = new Scanner(System.in);
	static Users user = new Users();
	static UsersDaoImplementation userImpl = new UsersDaoImplementation();
	static ContactsDaoImplementation contactImpl = new ContactsDaoImplementation();
	static boolean repeat = true;

	public static void main(String[] args) {

		System.out.println("**************************");
		System.out.println("Wellcome to Phonebook app");
		System.out.println("____________________________");
		System.out.println(" To register press [1], to login press [2]");
		int press = Utilities.checkInputInt(1, 2, input);

		while (true) { // infinite loop becouse this is console program
			input.nextLine();
			if (press == 1) {
				registerUser(userImpl, 0); // 0 is false for admin
				System.out.println("You are registered now.");
				System.out
						.println("Enter your username and password to login: ");
				String userName = input.nextLine();
				String password = input.nextLine();

				user = userImpl.findUserInDatabase(userName, "userName");

				if (user != null) {
					userImpl.logIn(userName, password);

					// check if user is admin
					if (userImpl.isAdmin(user)) {
						System.out.println("***  " + user.getFirstName()
								+ " you are loged in. ");
						System.out
								.println("This is the MAIN MENUE FOR ADMIN ***");

						mainMenueAdmin();

					} else {
						System.out.println("*** " + user.getFirstName()
								+ " you are loged in.");
						System.out.println("This is the MAIN MENUE***");

						mainMenueUser(user);
					}
				} else {
					System.out
							.println("Wrong user name or password. Try again");
				}

			} else {
				System.out
						.println("Enter your username and password to login: ");
				System.out
						.println("To log as admin (User name: admin, password: admin)");
				String userName = input.nextLine();
				String password = input.nextLine();

				user = userImpl.findUserInDatabase(userName, "userName");

				if (user != null) {
					userImpl.logIn(userName, password);

					// check if user is admin
					if (userImpl.isAdmin(user)) {
						System.out.println("***  " + user.getFirstName()
								+ " you are loged in.");
						System.out
								.println("This is the MAIN MENUE FOR ADMIN ***");

						mainMenueAdmin();

					} else {
						System.out.println("*** " + user.getFirstName()
								+ " you are loged in.");
						System.out.println("This is the MAIN MENUE***");

						mainMenueUser(user);
					}
				} else {
					System.out
							.println("Wrong user name or password. Try again");
				}
			}
		}
	}

	public static void mainMenueAdmin() {

		System.out.println("Enter number from 1 to 6 to select option: ");
		System.out.println("[1] Enter new user.");
		System.out.println("[2] Edit user.");
		System.out.println("[3] Find user. ");
		System.out.println("[4] See all users. ");
		System.out.println("[5] Logout all users.");
		System.out.println("[6] Remove user.");
		System.out.println("[7] Do something with your phonebook.");
		System.out.println("[8] Log out.");

		int userInput = Utilities.checkInputInt(1, 8, input);

		switch (userInput) {

		case 1: // Enter new user
			System.out.println("Is new user admin [1]yes, [0] no: ");
			int isAdmin = Utilities.checkInputInt(0, 1, input);
			registerUser(userImpl, isAdmin);
			System.out.println("Your user is registered in database.");
			optionMenueLogoutExitAppAdmin();
			break;

		case 2: // Edit user
			repeat = true;
			while (repeat == true) {
				System.out.println("Enter users username ");
				input.nextLine();
				String userName = input.nextLine();

				Users user = userImpl.findUserInDatabase(userName, "userName");

				// check if data is valid, is user in database
				if (user != null) {
					editUser(user);
					repeat = false;
					optionMenueLogoutExitAppAdmin();
				} else {
					System.out
							.println("Input is wrong or users is not in databese. Try again.");
					repeat = true;
				}
			}
			break;

		case 3: // Find user
			repeat = true;
			while (repeat == true) {

				Users u = findUser();
				if (u != null) {
					// print user
					userImpl.printUser(u);
					repeat = false;
				} else {
					System.out.println("User is not found!");
					System.out
							.println("Would you like to try with another one? [1] YES, [2] NO");
					int option = Utilities.checkInputInt(1, 2, input);
					if (option == 1) {
						repeat = true;
					} else {
						repeat = false;
						optionMenueLogoutExitAppAdmin();
					}
				}
			}
			System.out.println();
			optionMenueLogoutExitAppAdmin();
			break;

		case 4: // See all users

			ArrayList<Users> listOfusers = userImpl.allUsersFromDBList();

			for (Users u : listOfusers) {
				userImpl.printUser(u);
			}
			System.out.println();

			optionMenueLogoutExitAppAdmin();
			break;

		case 5: // Logout all users

			if (userImpl.logOutAllUsers()) {
				System.out.println("All users are loged off now.");
			}
			optionMenueLogoutExitAppAdmin();
			break;

		case 6: // Remove user

			repeat = true;
			while (repeat == true) {
				System.out.println("Enter username of user you want to delete");
				String username = input.next();
				// make user object
				Users userToDelete = userImpl.findUserInDatabase(username,
						"userName");
				// if user is deleted prit message
				if (userImpl.deleteUser(userToDelete)) {
					System.out.println("User " + userToDelete.getUserName()
							+ " is now deleted from the database");
					System.out.println();
					optionMenueLogoutExitAppAdmin();
				} else {
					System.out.println("User is not found");
					System.out
							.println("Would you like to try with another one? [1] YES, [2] NO");
					int option = Utilities.checkInputInt(1, 2, input);
					if (option == 1) {
						repeat = true;
					} else {
						repeat = false;
						optionMenueLogoutExitAppAdmin();
					}
				}
			}
			break;

		case 7:// Do something with your phonebook
			mainMenueUser(user);
			break;

		case 8: // Log out
			userImpl.logOut(user);
			System.out.println("User " + user.getFirstName()
					+ " is now loged out.");
		}
	}

	public static void mainMenueUser(Users user) {
		System.out.println("Enter number from 1 to 6 to select option:");
		System.out.println("1. Add new contact.  ");
		System.out.println("2. Edit contact. ");
		System.out.println("3. Delete contact. ");
		System.out.println("4. See list of all contacts.");
		System.out.println("5. Search contacts. ");
		System.out.println("6. Log out from app.");

		int userInput = Utilities.checkInputInt(1, 6, input);

		switch (userInput) {

		case 1:// Add new contact
			int userID = user.getUserID();
			addNewContact(contactImpl, userID);
			optionMenueLogoutExitAppUser(user);
			break;

		case 2: // Edit contact

			repeat = true;
			while (repeat == true) {
				System.out.println("Let's first find contact to edit!");

				Contacts editCon = findContact();
				contactImpl.printContact(editCon);

				// check if data is valid, is user in database
				if (user != null) {
					editContact(editCon);
					repeat = false;
					optionMenueLogoutExitAppUser(user);
				} else {
					System.out
							.println("Input is wrong or users is not in databese. Try again.");
					repeat = true;
				}
			}

			// contact.editEntryInContacts(userID, column, fieldToEdit);
			optionMenueLogoutExitAppUser(user);
			break;

		case 3:// delete contact
			repeat = true;
			while (repeat == true) {
				System.out.println("Let's first find contact to delete!");

				Contacts deleteCon = findContact();
				contactImpl.printContact(deleteCon);

				if (deleteCon != null) {

					System.out
							.println("Is this contact you want to delete: [1] YES or [2] NO?");
					int delete = Utilities.checkInputInt(1, 2, input);

					if (delete == 1) {

						if (contactImpl.deleteEntryFromContacts(deleteCon)) {

							System.out.println("Contact "
									+ deleteCon.getFirstName() + " "
									+ deleteCon.getLastName()
									+ " is now deleted.");
							System.out
									.println("Delete another one: [1] YES or [2] NO?");
							delete = Utilities.checkInputInt(1, 2, input);

							if (delete == 1) {
								repeat = true;
							} else {
								repeat = false;
							}
						}
					} else {
						System.out
								.println("Delete another one: [1] YES or [2] NO?");
						delete = Utilities.checkInputInt(1, 2, input);

						if (delete == 1) {
							repeat = true;
						} else {
							repeat = false;
						}
					}
				} else {
					System.out
							.println("Delete another one: [1] YES or [2] NO?");
					int delete = Utilities.checkInputInt(1, 2, input);

					if (delete == 1) {
						repeat = true;
					} else {
						repeat = false;
					}
				}
			}
			optionMenueLogoutExitAppUser(user);
			break;

		case 4: // See list of all contacts

			ArrayList<Contacts> listOfContacts = contactImpl
					.usersContacts(user);

			for (Contacts c : listOfContacts) {
				contactImpl.printContact(c);
			}

			optionMenueLogoutExitAppUser(user);
			break;

		case 5: // Search Contacts

			repeat = true;
			while (repeat == true) {

				Contacts c = findContact();
				if (c != null) {
					// print contact
					contactImpl.printContact(c);
					repeat = false;
				} else {
					System.out.println("Contact is not found!");
					System.out
							.println("Would you like to try with another one? [1] YES, [2] NO");
					int option = Utilities.checkInputInt(1, 2, input);
					if (option == 1) {
						repeat = true;
					} else {
						repeat = false;
						optionMenueLogoutExitAppUser(user);
					}
				}
			}
			System.out.println();
			optionMenueLogoutExitAppUser(user);
			break;
		case 6:
			userImpl.logOut(user);
			System.out.println("User " + user.getFirstName()
					+ " is now loged out.");
		}
	}

	public static void registerUser(UsersDaoImplementation userImpl, int isAdmin) {

		input.nextLine(); // clear scanner
		System.out.println("Enter name: ");
		String firstName = input.nextLine();
		System.out.println("Enter last name: ");
		String lastName = input.nextLine();
		System.out.println("Enter user name: ");
		String userName = input.nextLine();
		System.out.println("Enter password: ");
		String password = input.nextLine();
		System.out.println("Enter gender: ");
		String gender = input.nextLine();
		System.out.println("Enter e-mail: ");
		String mail = input.nextLine();

		userImpl.enterNewUserToDB(firstName, lastName, gender, mail, userName,
				password, isAdmin);

		Users user = userImpl.findUserInDatabase(userName, "userName");

		contactImpl.makeCapacityForNewUserInContacts(user.getUserID());
	}

	public static void editUser(Users user) {

		boolean editNext = true;
		while (editNext == true) {

			System.out.print("Set a new name for user (current: "
					+ user.getFirstName() + " ): ");
			String name = input.next();
			System.out.print("Set a new last name for user (current: "
					+ user.getLastName() + " ): ");
			String lastName = input.next();
			System.out.print("Set a new user name for user (current: "
					+ user.getUserName() + " ): ");
			String userName = input.next();
			System.out.print("Set a new gender for user (current: "
					+ user.getGender() + " ): ");
			String gender = input.next();
			System.out.print("Set a new email for user (current: "
					+ user.getEmail() + " ): ");
			String email = input.next();
			System.out
					.print("Set a new value for user if its admin [1] or regular user [0](current: "
							+ user.getIsAdmin() + " ): ");
			int isAdmin = input.nextInt();

			Users userUpdate = new Users(user.getUserID(), name, lastName,
					gender, email, userName, user.getPassword(), isAdmin);

			userImpl.updateUserInDatabase(userUpdate);
			System.out.println("Data for this user is updated.");

			System.out
					.println("Would you like to update more users [1] yes, [2] no: ");
			int choise = Utilities.checkInputInt(1, 2, input);
			if (choise == 1) {
				editNext = true;
			} else {
				editNext = false;
			}
		}
	}

	public static void editContact(Contacts contact) {

		boolean editNext = true;
		while (editNext == true) {

			System.out.print("Set a new name for contact (current: "
					+ contact.getFirstName() + " ): ");
			String name = input.next();

			System.out.print("Set a new last name for contact (current: "
					+ contact.getLastName() + " ): ");
			String lastName = input.next();

			System.out.print("Set a new phone number for contact(current: "
					+ contact.getPhoneNumber() + " ): ");
			String phoneNumber = input.next();

			System.out.print("Set a new gender for user (current: "
					+ contact.getGender() + " ): ");
			String gender = input.next();

			System.out.print("Set a new email for user (current: "
					+ contact.getEmail() + " ): ");
			String email = input.next();

			System.out.print("Set a new address for user (current: "
					+ contact.getAddress() + " ): ");
			String address = input.next();

			Contacts updateCon = new Contacts(contact.getContactID(), name,
					lastName, gender, email, phoneNumber, address,
					contact.getUserID());

			contactImpl.updateContact(updateCon);
			System.out.println("Data for this contact are updated.");

			System.out
					.println("Would you like to update more contacts [1] yes, [2] no: ");
			int choise = Utilities.checkInputInt(1, 2, input);
			if (choise == 1) {
				editNext = true;
			} else {
				editNext = false;
			}
		}
	}

	public static Contacts findContact() {
		System.out
				.println("Chose field for a search: "
						+ "[1]name, [2]last name, [3]phone number, [4]gender, [5]e-mail, [6] address"
						+ "(Enter numbers from 1 to 6) ");

		input.nextLine();
		int usersInput = Utilities.checkInputInt(1, 6, input);

		String column = "";

		switch (usersInput) {
		case 1:
			column = "firstName";
			break;
		case 2:
			column = "lastName";
			break;
		case 3:
			column = "phoneNumber";
			break;
		case 4:
			column = "gender";
			break;
		case 5:
			column = "email";
			break;
		case 6:
			column = "address";
			break;
		}
		input.nextLine(); // to clean Scanner
		System.out.println("Enter field value");
		String fieldValue = input.nextLine();

		return contactImpl.searchContacts(user.getUserID(),
				column, fieldValue);
	}

	/***/
	public static Users findUser() {
		System.out
				.println("Chose field for a search: "
						+ "[1]name, [2]last name, [3]user name, [4]gender, [5]e-mail, [6]is admin "
						+ "(Enter numbers from 1 to 6)");

		input.nextLine();
		int usersInput = Utilities.checkInputInt(1, 6, input);

		String column = "";

		switch (usersInput) {
		case 1:
			column = "firstName";
			break;
		case 2:
			column = "lastName";
			break;
		case 3:
			column = "userName";
			break;
		case 4:
			column = "gender";
			break;
		case 5:
			column = "email";
			break;
		case 6:
			column = "isAdmin";
			System.out.println("Enter [1] for admin, [0] for regular user.");
			break;
		}
		input.nextLine(); // to clean Scanner
		System.out.println("Enter field value");
		String fieldValue = input.nextLine();

		return userImpl.findUserInDatabase(fieldValue, column);
	}


	public static void addNewContact(ContactsDaoImplementation contacts,
			int userID) {
		input.nextLine();
		System.out.println("Enter name");
		String firstName = input.nextLine();
		System.out.println("Enter last name");
		String lastName = input.nextLine();
		System.out.println("Enter e-mail");
		String email = input.nextLine();
		System.out.println("Enter phone number");
		String phoneNumber = input.nextLine();
		System.out.println("Enter address");
		String address = input.nextLine();
		System.out.println("Enter gender");
		String gender = input.nextLine();

		ArrayList<Contacts> usersContacts = contacts.usersContacts(user);

		// all contacts are in one table in db, every has its own id
		int contactID = (userID - 1) * 100 + usersContacts.size() + 1;

		contacts.addContactToPhonebook(contactID, firstName, lastName, email,
				phoneNumber, address, gender, userID);

		System.out.println("The contact " + firstName + " " + lastName
				+ "is added to your phonebook.");

	}

	public static void optionMenueLogoutExitAppAdmin() {
		System.out
				.println("Press [1] to back to the main menue, [2] to log out, [3] to exit app. ");
		int option = Utilities.checkInputInt(1, 3, input);
		if (option == 1) {
			mainMenueAdmin();
		} else if (option == 2) {
			userImpl.logOut(user);
			System.out.println("User " + user.getFirstName()
					+ " is now loged out.");
		} else {
			ConnectionManager.getInstance().close();
			System.exit(1);
		}
	}

	public static void optionMenueLogoutExitAppUser(Users user) {
		System.out
				.println("Press [1] to back to the main menue, [2] to log out, [3] to exit app. ");
		int option = Utilities.checkInputInt(1, 3, input);
		if (option == 1) {

			mainMenueUser(user);

		} else if (option == 2) {
			userImpl.logOut(user);
			System.out.println("User " + user.getFirstName()
					+ " is now loged out.");
		} else {
			ConnectionManager.getInstance().close();
			System.exit(1);
		}
	}
}
