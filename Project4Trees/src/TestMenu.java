

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestMenu {

	/**
	 * A switch to navigate through the menu options of modifying the
	 * RandomAccessFile
	 * 
	 * @throws IOException
	 *             A declared checked exception
	 */
	public static void main(String args[]) throws IOException {
		 Action action = new Action();
		 String filename = null;
		 boolean hasExit = false;
		 boolean hasFile = false;
		 Scanner keyIn = new Scanner(System.in);
		 String menuOP = "";


		RandomAccessFile raf = new RandomAccessFile("dbase", "rw");

		Student rec = new Student();
		int menuOptionPointer = 0;

		while (hasExit != true) {
			System.out.println("1- Create a random-access file " + '\n'
					+ "2- Display a random-access file" + '\n'
					+ "3- Build the Index" + '\n' + "4- Display the Index"
					+ '\n' + "5- Retrieve a Record" + '\n'
					+ "6- Modify a Record" + '\n' + "7- Add a new Record"+ '\n' + "8- Delete a Record"+ '\n' + "9- Exit");

			do {
				System.out
				.println(" Please enter an integer to select what operation you would like ");
				try {
					menuOptionPointer = keyIn.nextInt();
				} catch (InputMismatchException e) {
					menuOptionPointer = 0;
					keyIn.nextLine();
				}
			} while (menuOptionPointer < 1 || menuOptionPointer > 9);

			switch (menuOptionPointer) {

			case 1:
				System.out
				.println("Enter FileName(Default = StudentRecords.txt)\n");
				keyIn.nextLine();
				filename = keyIn.nextLine();
				if (filename.length() == 0)
					filename = "StudentRecords.txt";
				System.out.println(filename);
				action.Create(raf, rec, filename);

				break;

			case 2:
				boolean atEnd = false;
				action.resetPage();
				action.DisplayPage(raf, rec);

				while (!menuOP.equals("M")) {
					System.out
					.println("Please enter a corrosponding character"
							+ '\n'
							+ "Return to Main Menu (M); Next Screen(N); or Display All(A)");
					menuOP = keyIn.next();
					if (menuOP.equals("N")||menuOP.equals("n")) {
						if (atEnd == false)
							action.DisplayPage(raf, rec);
					} else if (menuOP.equals("A")||menuOP.equals("a")) {
						action.Display(raf, rec);
						atEnd = true;
					} else if (menuOP.equals("M")||menuOP.equals("m")) {
						break;
					}

				}
				menuOP = "";
				atEnd = false;
				break;
				
			case 3:
				System.out
				.println("Building an index from existing RandomAccessFile");
				action.BuildIndex(raf);
				System.out.println("Index built");
				break;
			
			case 4:
				action.DisplayIndex(raf, rec);
				break;
					
			case 5:
				System.out
				.println("Enter which Student ID# you would like to retrieve "
						+ "by entering an Integer");
				int ret = keyIn.nextInt();
				System.out.println("You asked for record #" + ret);
				action.Retrieve(raf, ret);

				break;

			case 6:
				action.ModifyARecord(raf);

				break;

			case 7:
				action.AddARecord(raf);

				break;

			case 8:// delete a record
				action.DeleteARecord(raf);

				break;

			case 9:
				System.out.println("Program Exiting");
				keyIn.close();
				System.exit(0);
				

				break;

				// build an index
			

				// Display the index
			

			}

		}
	}
}

