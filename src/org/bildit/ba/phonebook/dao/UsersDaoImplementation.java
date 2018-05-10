package org.bildit.ba.phonebook.dao;

import java.sql.Connection; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import org.bildit.ba.phonebook.dto.Users;
import org.bildit.ba.phonebook.main.ConnectionManager;

public class UsersDaoImplementation implements UsersDaoInterface {

	private static Connection conn = ConnectionManager.getInstance()
			.getConnection();

	private static ArrayList<Users> onlineUsersList = new ArrayList<>();
	private static ArrayList<Users> allRegisteredUsers = new ArrayList<>();

	@Override
	public Users enterNewUserToDB(String firstName, String lastName,
			String gender, String email, String userName, String password,
			int isAdmin) {

		String query = "INSERT INTO users(userID, firstName, lastName, gender, email, userName, password, isAdmin) "
				+ "VALUES (default, ?, ?, ?, ?, ?, ?, ?)"; // default value -
															// it's
															// autoincrement in
															// db

		try (PreparedStatement ps = conn.prepareStatement(query);) {

			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, gender);
			ps.setString(4, email);
			ps.setString(5, userName);
			ps.setString(6, password);
			ps.setInt(7, isAdmin); // tiny int in db

			ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e);
		}

		Users user = new Users(userName, password, email);
		allRegisteredUsers.add(user);
		return user;
	}

	@Override
	public boolean isAlreadyRegistered(Users user) {

		boolean isRegistered = false;

		ArrayList<Users> list = allUsersFromDBList();

		for (int i = 0; i < list.size(); i++) {

			if (user.getUserName().compareTo(list.get(i).getUserName()) == 0) {
				isRegistered = true;
			}
		}
		return isRegistered;
	}

	@Override
	public boolean logIn(String userName, String password) {

		Users user = new Users(userName, password);
		boolean isLogedIn = false;

		// check if user is registered
		if (isAlreadyRegistered(user)) {

			// add this user to the list of online users
			onlineUsersList.add(user);

			isLogedIn = true;
		}
		return isLogedIn;
	}

	@Override
	public boolean logOut(Users user) {

		boolean logOut = false;

		// go througth list of online users
		for (int i = 0; i < onlineUsersList.size(); i++) {

			// if user is in the list remove it
			if (user.getUserName().equals(onlineUsersList.get(i).getUserName())) {
				onlineUsersList.remove(i);
				logOut = true;
			}
		}
		return logOut;
	}

	@Override
	public boolean logOutAllUsers() {

		boolean allLogedOut = false;

		// remove all from list of online users
		for (int i = 0; i < onlineUsersList.size(); i++) {
			onlineUsersList.remove(i);
		}

		if (onlineUsersList.size() == 0) {
			allLogedOut = true;
		}
		return allLogedOut;
	}

	@Override
	public boolean isAdmin(Users user) {

		boolean admin = false;
		String query = "SELECT isAdmin FROM users WHERE userName = ?";
		ResultSet rs = null;

		try (PreparedStatement ps = conn.prepareStatement(query);) {

			ps.setString(1, user.getUserName());
			rs = ps.executeQuery();

			if (rs.next()) { // if table contains some data

				if (rs.getInt("isAdmin") == 1) {
					admin = true; // if it's admin, return true
				} else
					admin = false;
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
		return admin;
	}

	@Override
	public boolean isActive(Users user) {
		boolean isActive = false;
		for (int i = 0; i < onlineUsersList.size(); i++) {
			if (user.equals(onlineUsersList.get(i))) {
				isActive = true;
			}
		}
		return isActive;
	}

	@Override
	public ArrayList<Users> allUsersFromDBList() {

		String query = "SELECT * FROM users";
		ResultSet rs = null;
		ArrayList<Users> allUsers = new ArrayList<>();

		try (Statement statement = conn.createStatement();) {

			rs = statement.executeQuery(query);

			while (rs.next()) { // while table contains some data

				allUsers.add(new Users(rs.getInt("userID"), rs
						.getString("firstName"), rs.getString("lastName"), rs
						.getString("gender"), rs.getString("email"), rs
						.getString("userName"), rs.getString("password"), rs
						.getInt("isAdmin")));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return allUsers;
	}

	@Override
	public boolean updateUserInDatabase(Users user) {

		String query = " UPDATE users SET firstName = ?, lastName = ?, gender = ?, email = ?, userName = ?, isAdmin = ? WHERE userID = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getGender());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getUserName());
			ps.setInt(6, user.getIsAdmin());
			ps.setInt(7, user.getUserID());

			int affected = ps.executeUpdate(); // execute update
			if (affected == 1) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}

	@Override
	public Users findUserInDatabase(String userData, String column) {

		Users user = null;
		String query = "SELECT * FROM users WHERE " + column + " = ? ";
		ResultSet rs = null;

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, userData);
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new Users(rs.getInt("userID"),
						rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("gender"), rs.getString("email"),
						rs.getString("userName"), rs.getString("password"),
						rs.getInt("isAdmin"));
			}

		} catch (SQLException e) {
			System.err.println(e);
		}
		return user;
	}

	@Override
	public void printUser(Users user) {
		if (user != null) {
			System.out.println("userID: " + user.getUserID() + ", name: "
					+ user.getFirstName() + ", lastname: " + user.getLastName()
					+ ", username: " + user.getUserName() + ", gender: "
					+ user.getGender() + ", email: " + user.getEmail());
		} else {
			System.out.println("No user to print.");
		}
	}

	@Override
	public boolean deleteUser(Users user) {

		boolean deleted = false;
		if (user != null) {
			// create an SELECT SQL query
			String query = "DELETE FROM users WHERE userID = ?";

			try (
			// java.sql.Statement
			PreparedStatement ps = conn.prepareStatement(query);) {

				// fill in the placeholders/parameters
				ps.setInt(1, user.getUserID());

				// execute the query
				int affected = ps.executeUpdate();
				if (affected == 1) {
					deleted = true;
				}
			} catch (SQLException e) {
				System.err.println(e);
				return false;
			}
		}
		return deleted;
	}

	
}
