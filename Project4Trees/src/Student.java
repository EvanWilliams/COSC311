

import java.io.*;
import java.util.*;

/**
 * Class to represent a student record
 * 
 * @author COSC 311
 * 
 */
public class Student {

	private final int LENGTH = 20; // number of characters in first and last
									// names
	private final static int RECSIZE = 92; // size of the student record in
											// bytes
	private String fName; // first name
	private String lName; // last name
	private int ID; // student id
	private double GPA; // student gpa
	private int pointer;

	/**
	 * initialize the instance variables
	 * 
	 * @param first
	 *            The student first name
	 * @param last
	 *            The student last name
	 * @param id
	 *            The student ID
	 * @param gpa
	 *            The student GPA
	 */
	public void setData(String first, String last, int id, double gpa) {
		fName = first;
		lName = last;
		ID = id;
		GPA = gpa;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = pad(fName,LENGTH);
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = pad(lName,LENGTH);
	}

	public double getGPA() {
		return GPA;
	}

	public void setGPA(double gpa) {
		GPA = gpa;
	}

	/**
	 * determine the student record size
	 * 
	 * @return the student record size
	 */
	public int size() {
		return RECSIZE;
	}

	/** return true if this object and the argument object have the same ID */
	public boolean equals(Object other) {
		if (other == null || this.getClass() != other.getClass())
			return false;

		return ID == ((Student) other).ID;
	}

	/**
	 * write a student record to the random access file
	 * 
	 * @param out
	 *            The random access file
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void writeToFile(RandomAccessFile out) throws IOException {
		writeString(out, fName);
		writeString(out, lName);
		out.writeInt(ID);
		out.writeDouble(GPA);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * read a student record from the random access file
	 * 
	 * @param in
	 *            The random access file
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void readFromFile(RandomAccessFile in) throws IOException {
		fName = readString(in);
		lName = readString(in);
		ID = in.readInt();
		GPA = in.readDouble();
	}

	/**
	 * read a fixed size string from the random access file
	 * 
	 * @param in
	 *            The random access file
	 * @return A string of length LENGTH
	 * @throws IOException
	 *             A declared checked exception
	 */
	private String readString(RandomAccessFile in) throws IOException {
		String result = "";
		for (int i = 0; i < LENGTH; i++)
			result += in.readChar();
		return result;
	}

	/**
	 * write a fixed size string to the random access file
	 * 
	 * @param out
	 *            The random access file
	 * @param name
	 *            The fixed size string
	 * @throws IOException
	 *             A declared checked exception
	 */
	private void writeString(RandomAccessFile out, String name)
			throws IOException {
		char[] str = new char[LENGTH];
		str = name.toCharArray();
		for (int i = 0; i < str.length; i++)
			out.writeChar(str[i]);
	}

	/**
	 * read a student record from an input text file and pad the first and last
	 * names with blanks if needed
	 * 
	 * @param in
	 *            The input text file
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void readFromKeyboard(Scanner in) throws IOException {
		fName = in.next();
		lName = in.next();
		ID = in.nextInt();
		GPA = in.nextDouble();
		fName = pad(fName, LENGTH);
		lName = pad(lName, LENGTH);
	}

	/**
	 * append blanks to the first argument, if needed, to make it a string of
	 * length 'size'
	 * 
	 * @param s
	 *            The string that is being padded
	 * @param size
	 *            The length of the padded string
	 * @return A padded string of length 'size'
	 */
	public static String pad(String s, int size) {
		for (int i = s.length(); i < size; i++)
			s += ' ';
		return s;
	}

	/** return a string representing a student record */
	public String toString() {
		return "first name = " + fName + "last name = " + lName
				+ "\tstudent ID = " + ID + "\tGPA = " + GPA + "\n";
	}

}