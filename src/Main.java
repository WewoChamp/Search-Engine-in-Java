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

public class Main {
    public static void main(String[] args) throws IOException {
        String query = "is b"; //Set the query to the text you would like
                              //to search for in the list of files in the
                              //configuration
                              //You may add more text files to the list
                              //and name them whatever you want, but the files
                              //need to be compatible text files ie. tsv and txt
                              //files and need to be added to the input folder
        Utilities.searchQuery(query, args);
    }
}
