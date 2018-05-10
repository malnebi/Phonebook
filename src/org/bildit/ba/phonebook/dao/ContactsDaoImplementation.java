package org.bildit.ba.phonebook.dao;

import java.util.ArrayList;

import org.bildit.ba.phonebook.dto.Contacts;
import org.bildit.ba.phonebook.dto.Users;
import org.bildit.ba.phonebook.main.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDaoImplementation implements ContactsDaoInterface {

	private static Connection conn = ConnectionManager.getInstance()
			.getConnection();

	@Override
	public Contacts addContactToPhonebook(int contactID, String firstName,
			String lastName, String email, String phoneNumber, String address,
			String gender, int userID) {

		// String sql =
		// "ISERT INTO Contacts VALUES (?, ?, ?, ?, ?, ?) WHERE userID = ?";

		String sql = "UPDATE contacts SET firstName = ?, lastName = ?, gender = ?, phoneNumber = ?, email = ?, address = ?"
				+ " WHERE userID = ? AND contactID = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, gender);
			ps.setString(4, phoneNumber);
			ps.setString(5, email);
			ps.setString(6, address);
			ps.setInt(7, userID);
			ps.setInt(8, contactID);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println(e);
		}
		Contacts contact = new Contacts(contactID, firstName, lastName, gender,
				phoneNumber, email, address, userID);
		return contact;
	}

	@Override
	public boolean updateContact(Contacts contact) {

		String query = " UPDATE contacts SET firstName = ?, lastName = ?, gender = ?, email = ?, address = ?, phoneNumber = ? WHERE userID = ? AND contactID = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, contact.getFirstName());
			ps.setString(2, contact.getLastName());
			ps.setString(3, contact.getGender());
			ps.setString(4, contact.getEmail());
			ps.setString(5, contact.getAddress());
			ps.setString(6, contact.getPhoneNumber());
			ps.setInt(7, contact.getUserID());
			ps.setInt(8, contact.getContactID());

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
	public boolean deleteEntryFromContacts(Contacts contact) {

		String sql = "DELETE FROM contacts WHERE userID = ? AND email = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, contact.getUserID());
			ps.setString(2, contact.getEmail());

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
	public Contacts searchContacts(int userID, String column,
			String fieldToSearch) {

		Contacts contact = null;
		String sql = "SELECT * FROM contacts WHERE " + column
				+ " = ? AND userID = ? ";
		ResultSet rs = null;

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, fieldToSearch);
			ps.setInt(2, userID);

			rs = ps.executeQuery();

			if (rs.next()) {
				contact = new Contacts(rs.getInt("contactID"),
						rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("gender"), rs.getString("email"),
						rs.getString("phoneNumber"), rs.getString("address"),
						rs.getInt("userID"));
			}

		} catch (SQLException e) {
			System.err.println(e);
		}
		return contact;
	}

	@Override
	public ArrayList<Contacts> usersContacts(Users user) {

		ArrayList<Contacts> listOfContacts = new ArrayList<>();

		String query = "SELECT * FROM contacts WHERE userID = ?";
		ResultSet rs = null;

		try (PreparedStatement ps = conn.prepareStatement(query);) {
			ps.setInt(1, user.getUserID());
			rs = ps.executeQuery();

			while (rs.next()) { // while table contains some data

				if (rs.getString("firstName") != null
						|| rs.getString("lastName") != null
						|| rs.getString("phoneNumber") != null) {

					listOfContacts
							.add(new Contacts(rs.getInt("contactID"), rs
									.getString("firstName"), rs
									.getString("lastName"), rs
									.getString("gender"),
									rs.getString("email"), rs
											.getString("phoneNumber"), rs
											.getString("address"), rs
											.getInt("userID")));
				}
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return listOfContacts;

	}

	public boolean makeCapacityForNewUserInContacts(int userID) {

		boolean madeCapacity = false;
		int numOfEntries = 0;
		int capacity = 100;
		String sql = "INSERT INTO contacts (contactID, firstName, lastName, gender, phoneNumber, email, address, userID) "
				+ "VALUES ( default, default, default, default, default, default, default, ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userID);

			for (int i = 0; i < capacity; i++) {

				int affected = ps.executeUpdate();
				if (affected == 1) {
					numOfEntries++;
				}
			}
			if (numOfEntries == 100)
				madeCapacity = true;
			else
				madeCapacity = false;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return madeCapacity;
	}

	public void printContact(Contacts contact) {
		if (contact != null) {
			System.out.println("contactID: " + contact.getContactID()
					+ ", name: " + contact.getFirstName() + ", lastname: "
					+ contact.getLastName() + ", phone: "
					+ contact.getPhoneNumber() + ", gender: "
					+ contact.getGender() + ", email: " + contact.getEmail()
					+ " address: " + contact.getAddress());

		} else {
			System.out.println("No contact to print.");
		}
	}

}
