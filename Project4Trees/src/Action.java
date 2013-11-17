import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Action {
	int recordcount = 0;
	boolean hasFile = false;
	boolean hasExit = false;
	Scanner keyIn = new Scanner(System.in);

	String menuOP = "";
	String filename = null;
	int pP = 0;

	BinarySearchTree<KeyAddress> bst;

	public Action() {
		bst = new BinarySearchTree<KeyAddress>();
	}

	/**
	 * Creates a random access file with the name of the inputed fileName sets
	 * hasFile flag to true
	 * 
	 * @param raf
	 *            The random access file
	 * @param rec
	 *            The student record
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void Create(RandomAccessFile raf, Student rec, String fileName)
			throws IOException {

		Scanner fin = new Scanner(new FileInputStream(fileName));
		// /** read student records from the input file write them to the random
		// access file */
		while (fin.hasNext()) {

			rec.readFromKeyboard(fin);
			rec.writeToFile(raf);

		}
		hasFile = true;
	}

	/**
	 * Display the first 5 records in the RandomAccessFile checking if the
	 * records have "-1" in the ID If they do the display method skips over but
	 * counts for the record
	 * 
	 * @param raf
	 *            The random access file
	 * @param rec
	 *            The student record
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void DisplayPage(RandomAccessFile raf, Student rec)
			throws IOException {

		raf.seek(pP * rec.size());
		try {
			for (int i = 0; i < 5; i++) {
				rec.readFromFile(raf);
				recordcount++;
				if (rec.getID() != -1) // -1 indicates Deleted Record
				{

					System.out.print("Record Num = " + recordcount + " ");

					System.out.println(rec);
					pP++;
				}

			}
		} catch (EOFException e) {
		}

	}

	/*
	 * reset record count to 0 so that Display page does not have incorrect
	 * record numbers
	 */
	public void resetPage() {
		recordcount = 0;
	}

	/**
	 * This Method creates a index of the RandomAccessFile by making a singly
	 * linked List of object "KeyAddress" that have both a key and an address
	 * data field and a next node linking them to the next key address object
	 * 
	 * @param raf
	 *            The random access file
	 * @param rec
	 *            The student record
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void BuildIndex(RandomAccessFile raf)
			throws IOException {
		Student rec = new Student();
		int indexAddr = 0;
		raf.seek(0);

		try {
			while (true) {
				KeyAddress work = new KeyAddress();

				rec.readFromFile(raf);
				if (rec.getID() != -1) {
					work.setKey(rec.getID());
					work.setAddress(indexAddr++);
					bst.add(work);
				}

			}
		} catch (EOFException e) {

		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

	}

	/**
	 * sets iterator node at the head of the linked list and goes to the
	 * iterator.next until it reaches a null reference or it goes to a given
	 * spot in the linked list and continues until it hits a null reference.
	 * 
	 * @param raf
	 *            The random access file
	 * @param rec
	 *            The student record
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void DisplayIndex(RandomAccessFile raf, Student rec) {
		KeyAddress Start = new KeyAddress();
		String DispInd = "";
		System.out
		.println("Would you like to see the entire Index or part of the index?"
				+ '\n'
				+ "Enter (1) to veiw the entire index or (2) to see part of the index");
		// TODO breadth First traversal here

		while (!(DispInd.equals("1") || DispInd.equals("2"))) {
			DispInd = keyIn.nextLine();
			if (DispInd.equals("1")) {
				// bst.setIterator(bst.head);
				bst.breadthFirstTraversal();
				System.out.println();
				// while (bst.iterator != null) {
				// System.out.println((bst.iterator.getData()).getKey()
				// + " ==> ");
				// bst.next();
				// }

			} else if (DispInd.equals("2")) {
				int get = 0;
				System.out
				.println("Enter a student number that you would like to start veiwing from");

				get = keyIn.nextInt();
				Start.setKey(get);
				bst.breadthFirstTraversal(bst.findNode(Start));
				
				// if (get > 0 && get <= bst.size) {
				// bst.setIterator(bst.getNode(get));
				//
				// while (bst.iterator != null) {
				// System.out.print((bst.iterator.getData()).getKey()
				// + " ==> ");
				//
				// bst.next();
				// }
				// System.out.println("");
				// }
			}
		}
	}

	public int Retrieve(RandomAccessFile raf, int r)
			throws IOException {
		/**
		 * display the record corresponding with the studentId(r) in the random
		 * access file
		 * 
		 * @param raf
		 *            The random access file
		 * @param rec
		 *            The student record
		 * @throws IOException
		 *             A declared checked exception
		 */
		Student rec = new Student();
		KeyAddress target = new KeyAddress();
		int e;
		target.setKey(r);

		// sll.setIterator(sll.head);
		//
		// while ((sll.iterator) != null
		// && sll.iterator.getData().getKey() != retrieve) {
		// sll.next();
		// }
		// if (sll.iterator == null) {
		// System.out
		// .println("The student ID # specified could not be retreived");
		// return;
		// }
		// e=sll.iterator.getData().getAddress();
		if (bst.isEmpty()) {
			System.out.println("Unable to return a record from an Empty Tree");
			return -1;
		} else {
			target = bst.Find(target);
			if (target == null) {
				System.out.println("Record not found");
				
				return -1;
			}
			e = target.getAddress();

			raf.seek((e) * rec.size());
			rec.readFromFile(raf);
			System.out.println(rec);
		}
		return e;
	}

	public void AddARecord(RandomAccessFile raf)
			throws IOException {
		/**
		 * read a student record and write it at the end of the random access
		 * file
		 */
		Student rec = new Student();
		KeyAddress work = new KeyAddress();
		System.out
		.print("Enter your first name, last name, student ID, and GPA: ");
		rec.readFromKeyboard(keyIn);
		raf.seek(raf.length());
		int address = (int) (raf.length() / rec.size());
		rec.writeToFile(raf);
		work.setKey(rec.getID());
		work.setAddress(address);
		bst.add(work);

	}

	/**
	 * First confirm the file to be deleted then set the record's ID to "-1" and
	 * write to the RandomAccessFile
	 * 
	 * @param raf
	 *            The random access file
	 * @param rec
	 *            The student record
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void DeleteARecord(RandomAccessFile raf)
			throws IOException {
			Student rec = new Student();
			KeyAddress target = new KeyAddress();
		if (bst.isEmpty()) {
			System.out.println("Unable to delete record from an Empty Tree");
			return;
		} else {
			System.out
			.print("Please enter a record number to be deleted from the random access file" + '\n');
			int e = keyIn.nextInt();
			System.out.println("You picked #" + e + "");
			int index = Retrieve(raf, e);
			target.setKey(e);
			if(index !=-1){
				
			
			System.out
			.println("Do you wish to delete this record?"
					+ '\n'
					+ "Enter Y to delete this record, any other keystroke will cancel deletion ");
			if (keyIn.next().equals("Y")) {

				bst.delete(target);
				raf.seek((index) * rec.size());
				rec.readFromFile(raf);
				raf.seek((index) * rec.size());
				rec.setID(-1);
				rec.writeToFile(raf);
			}
			}
		}
	}

	public void ModifyARecord(RandomAccessFile raf) throws IOException {
		// /**
		// * read a student record then modify it and write it at the same spot
		// in
		// * the random access file also find a node that has the corresponding
		// Id#
		// * and write to that location the modified data.
		// * Also has a switch to determine when to leave and what to change
		// fname/lname/gpa
		// *
		// * @throws IOException
		// * A declared checked exception
		// */
		// int modifyOptionPointer = 0;

		Student rec = new Student();
		int recNum = 0;
		int modifyOptionPointer;
		int modify = 0;
		KeyAddress target = new KeyAddress();
		KeyAddress targetNode = null;

		if (bst.isEmpty()) {
			System.out.println("Unable to modify record--Empty Tree");
			return;
		}
		do {
			System.out
			.println("Enter a student ID for the student you wish to retrieve or 0 to exit");
			modify = keyIn.nextInt();
			target.setKey(modify);
			targetNode = bst.Find(target);
			if (targetNode == null)
				System.out.println("Student ID could not be found- retrying");
		} while (targetNode == null && modify!= 0);

		recNum = targetNode.getAddress();
		raf.seek((recNum) * rec.size());
		rec.readFromFile(raf);
		System.out.println(rec);
		System.out.println("Is " + targetNode
				+ " the Student you were looking for? ");

		if (keyIn.next().equalsIgnoreCase("y")) {

			do {
				System.out
				.print("Please specify which data feild you wish to modify"
						+ '\n'
						+ "(1) First Name "
						+ '\n'
						+ "(2) Last Name "
						+ '\n'
						+ "(3) GPA "
						+ '\n'
						+ " Enter an integer other than 1-3 to exit ");
				modifyOptionPointer = keyIn.nextInt();

				if (modifyOptionPointer > 0 && modifyOptionPointer < 4) {
					switch (modifyOptionPointer) {

					case 1:

						System.out
						.println("Type the new First name for the selected student ");
						String Fn = keyIn.next();
						rec.setfName(Fn);
						break;

					case 2:
						String Ln = "";
						System.out
						.println("Type the new Last name for the selected student ");
						Ln = keyIn.next();
						rec.setlName(Ln);
						break;

					case 3:
						Double gpa = 0.0;
						System.out
						.println("Type the new GPA for the selected student ");
						gpa = keyIn.nextDouble();
						rec.setGPA(gpa);
						break;
					}
				}
			} while (modifyOptionPointer > 0 && modifyOptionPointer < 4);

			raf.seek((recNum) * rec.size());
			rec.writeToFile(raf);
		}

		// if (sll.iterator.getData().getKey() == modify) {
		// e = sll.iterator.getData().getAddress();
		// } else {
		// while ((sll.getNext(sll.iterator) != null)
		// && sll.iterator.getData().getKey() != modify) {
		// sll.next();
		// if (sll.iterator.getData().getKey() == modify) {
		// e = sll.iterator.getData().getAddress();
		// }
		// }
		// if (sll.getNext(sll.iterator) == null) {
		// System.out
		// .println("The student ID # specified could not be retreived");
		// return;
		// }
		// }
		//

	}

	/**
	 * display the random access file
	 * 
	 * @param raf
	 *            The random access file
	 * @param rec
	 *            The student record
	 * @throws IOException
	 *             A declared checked exception
	 */
	public void Display(RandomAccessFile raf, Student rec) throws IOException {
		raf.seek(0);
		pP = 0;
		int recordcount = 0;
		try {
			while (true) {

				rec.readFromFile(raf);
				recordcount++;

				if (rec.getID() != -1) { // -1 indicates Deleted Record
					System.out.print("Record Num = " + recordcount + " ");
					System.out.println(rec);
				}
			}
		} catch (EOFException e) {
		}

	}
}
