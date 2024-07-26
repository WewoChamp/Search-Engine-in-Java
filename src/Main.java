/**
 * @author Folawewo Osibo
 * @version v10
 * <p>
 *   Searches for all appearances of a query in a list of documents provided
 * <p>
 *   Separates the list of documents provided in the configuration into a
 *   hashmap containing the respective file names and their file contents.
 *   the program then searches through the file names stored in the hashmap
 *   for the search query and displays all the file names that contain the
 *   search query first before doing the same with the file contents and
 *   displaying the file contents that contain the search query
 */

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        while(Utilities.choice.equals("Y")) {
            System.out.println("********************************");
            System.out.println("Document Search Engine");
            System.out.println("********************************");
            System.out.println("Note:");
            System.out.println("- Only txt files can be searched " +
                    "through at the moment!");

            System.out.println(
                    "***************************************************************************************");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the pathname of the folder you would like " +
                    "to search in (Pathname must not contain spaces): ");
            //Please note that a sample input folder has been provided to
            //test the program (You could just copy the input folder's path).
            String folder = scanner.nextLine();

            System.out.println(
                    "***************************************************************************************");

            System.out.println("Search: ");
            String query = scanner.nextLine();

            System.out.println(
                    "***************************************************************************************");

            String[] fileNames = Utilities.getFileNames(folder);
            Utilities.searchQuery(query, fileNames);

            System.out.println(
                    "***************************************************************************************");

            System.out.println("Would you like to search again? (Y/N)");
            Utilities.choice = scanner.nextLine().trim().toUpperCase();
            while (!Utilities.choice.equals("Y") && !Utilities.choice.equals("N")) {
                System.out.println("Please enter \"Y\" for yes or \"N\" for no.");
                Utilities.choice = scanner.nextLine().trim().toUpperCase();
            }
        }
    }
}
