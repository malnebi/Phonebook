package org.bildit.ba.phonebook.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilities {

	/**
	 * Method checks inputs for first and last number
	 * 
	 * @return returns selected int
	 */
	public static int checkInputInt(int from, int to, Scanner inputScan) {

		int selected = 0;
		boolean error = true;
		Scanner input = inputScan;
		do {
			try {
				selected = input.nextInt();

				if (selected >= from && selected <= to) {
					error = false;
				} else {
					System.out
							.println("Out  of range! Select number in range between"
									+ from + " - " + to);
				}

			} catch (InputMismatchException e) {
				System.out.println("You must enter integer value between "
						+ from + " - " + to);
				input.nextLine();
			}

		} while (error);
		return selected;
	}

	/**
	 * Method checks inputs for first and last number
	 * 
	 * @return returns selected int
	 */
	public static String checkInputString(Scanner input, String[] array) {

		String selected = "";
		boolean error = true;
		do {
			try {

				selected = input.nextLine();

				for (int i = 0; i < array.length; i++) {
					if (array[i].equalsIgnoreCase(selected)) {
						error = false;
					}
					if (i == array.length && error == true) {
						System.out
								.println("Try again. Enter string value between this (");
						for (i = 0; i < array.length; i++) {
							System.out.print(array[i] + ", ");
						}
					}

				}
			} catch (InputMismatchException e) {
				System.out
						.println("You must enter string value between this (");
				for (int i = 0; i < array.length; i++) {
					System.out.print(array[i] + ", ");
				}
				input.nextLine();
			}

		} while (error);
		return selected;
	}

}
