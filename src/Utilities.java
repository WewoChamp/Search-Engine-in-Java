import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Utilities {

    public static String choice = "Y";

    /**
     * Gets all the filenames from the given folder and stores them in a String
     * Array.
     * @param folderName    The folder containing the files to be searched.
     * @return              Returns a String Array of all the file names in
     *                      the folder
     * @throws IOException  In case there's an error while reading the paths
     *                      within the folder
     */
    public static String[] getFileNames (String folderName) throws IOException {
        ArrayList<String> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folderName))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    if(path.toString().endsWith(".txt")) {
                        fileList.add(path.toString());
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Error: [" + e.getMessage() + "] is an " +
                    "invalid path!");
        }
        String[] fileNames = new String[fileList.size()];
        for(int i = 0; i < fileNames.length; i++){
            fileNames[i] = fileList.get(i);
        }
        return fileNames;
    }

    /**
     * Separates each file provided in the configuration into the file's name
     * and the file's contents and then stores and associates both in a HashMap.
     * @param documents    The HashMap that will store the separated file names
     *                     and file contents.
     * @param input        The String array of the file names of files to be
     *                     read.
     * @throws IOException Just in case there's an error reading a file's
     *                     contents.
     */
    public static void readDocuments(HashMap<String, String> documents,
                                     String[] input) throws IOException {
        for(String document : input){
            documents.put(document,
                    String.join(" ",
                            Files.readAllLines(Path.of(document))));
        }
    }

    /**
     * Stores all the keys in a HashMap in an ArrayList storing the same data
     * type as the key.
     * @param info    The set of the HashMap's keys.
     * @param docInfo The ArrayList storing just the keys of the HashMap.
     */
    public static <t> void getDocInfo(Set<t> info,
                                               ArrayList<t> docInfo){
        docInfo.addAll(info);
    }

    /**
     * Stores all the values in a HashMap in an ArrayList storing the same data
     * type as the value.
     * @param info    The collection of the HashMap's values.
     * @param docInfo The ArrayList storing just the values of the HashMap.
     */
    public static <t> void getDocInfo(Collection<t> info,
                                      ArrayList<t> docInfo){
        docInfo.addAll(info);
    }

    /**
     * Displays the search query.
     * @param query The query as set by the user of the program.
     */
    public static void displayQuery(String query){
        System.out.printf("The search query was \"%s\"\n", query);
    }

    /**
     * Goes through an Arraylist of Strings and checks for the query in each
     * String adding each String that contains the query into a new ArrayList.
     * @param relevantDocInfo The new ArrayList for the Strings containing the
     *                        query.
     * @param docInfo         The ArrayList containing the Strings to be
     *                        checked for the query.
     * @param query           The String acting as the search parameter.
     */
    public static void getRelevantInfo(ArrayList<String> relevantDocInfo,
                                       ArrayList<String> docInfo, String query){
        for(String info : docInfo) {
            if(info.toUpperCase().contains(query.toUpperCase())){
                relevantDocInfo.add(info.trim());
            }
        }
    }

    /**
     * Displays all the file names containing the query.
     * @param docNames The ArrayList of file names containing the query.
     */
    public static void displayDocNames(ArrayList<String> docNames){
        if (!(docNames.isEmpty())) {
            int noOfDocsFound = docNames.size();
            System.out.println("Documents:");
            for (String docName : docNames) {
                System.out.println("- " + docName);
            }
            System.out.println(noOfDocsFound + " DOCUMENTS FOUND.");
        }
    }

    /**
     * Splits each String in an ArrayList of Strings into an Array of its
     * constituent words and then stores each String Array into another
     * ArrayList of String Arrays.
     * @param separatedRelevantDocContents The ArrayList of String Arrays
     *                                     containing the Strings separated
     *                                     into their constituent words.
     * @param relevantDocContents          The ArrayList containing the
     *                                     Strings before they have been split.
     */
    public static void separateRelevantDocContents(ArrayList<String[]> separatedRelevantDocContents, ArrayList<String> relevantDocContents){
        for (String relevantDocContent : relevantDocContents) {
            separatedRelevantDocContents.add(relevantDocContent.split(" "));
        }
    }

    /**
     * Filters an ArrayList of Strings representing the contents of each
     * document to an ArrayList of the words containing the search query from
     * each document's contents and then stores this ArrayList in another
     * ArrayList. This process is repeated for all documents whose contents
     * contain the query.
     * @param filteredRelevantDocContents The ArrayList containing the
     *                                    relevant words from each document's
     *                                    contents.
     * @param query                       The search parameter.
     * @param relevantDocContents         The ArrayList storing each
     *                                    document's contents.
     */
    public static void filterRelevantDocContents(ArrayList<ArrayList<String>> filteredRelevantDocContents, String query, ArrayList<String> relevantDocContents){
        ArrayList<String[]> separatedRelevantDocContents =
                new ArrayList<>();
        Utilities.separateRelevantDocContents(separatedRelevantDocContents,
                relevantDocContents);
        for (String[] separatedRelevantDocContent : separatedRelevantDocContents) {
            ArrayList<String> preFilteredRelevantDocContents =
                    new ArrayList<>();
            for (String separatedRelevantDocContentWord : separatedRelevantDocContent) {
                if (separatedRelevantDocContentWord.toUpperCase().contains(query.toUpperCase())) {
                    preFilteredRelevantDocContents.add(separatedRelevantDocContentWord);
                }
            }
            filteredRelevantDocContents.add(preFilteredRelevantDocContents);
        }
    }

    /**
     * Groups an ArrayList of Strings in a specific number using each String
     * in the ArrayList as the first word for each grouping and then stores each
     * grouping in a different ArrayList.
     * @param sizeOfPreFilteredRelevantDocContents The length of the ArrayList
     *                                             to be Grouped.
     * @param splitNumber                          The number that the
     *                                             Strings in the ArrayList
     *                                             are to be grouped by.
     * @param preFilteredRelevantDocContents       The ArrayList to be grouped.
     * @param finalPreFilteredRelevantDocContents  The ArrayList that will
     *                                             store the groupings of the
     *                                             old ArrayList.
     */
    public static void groupRelevantDocContents(int sizeOfPreFilteredRelevantDocContents, int splitNumber, ArrayList<String> preFilteredRelevantDocContents, ArrayList<String> finalPreFilteredRelevantDocContents){
        for (int i = 0; i <= (sizeOfPreFilteredRelevantDocContents - splitNumber); i++) {
            String word = "";
            for (int j = 0; j < splitNumber; j++) {
                word += preFilteredRelevantDocContents.get(j) + " ";
            }
            finalPreFilteredRelevantDocContents.add(word);
            preFilteredRelevantDocContents.removeFirst();
        }
    }

    /**
     * Associates the ArrayLists of Strings containing the file contents with
     * the query to their document names.
     * @param associatedRelevantDocNames  The Arraylist of Strings containing
     *                                    the associated file names.
     * @param filteredRelevantDocContents The ArrayList containing the file
     *                                    contents with the query for each file.
     * @param documents                   The HashMap mapping file names to
     *                                    all of their contents.
     */
    public static void associateDocNames(ArrayList<String> associatedRelevantDocNames, ArrayList<ArrayList<String>> filteredRelevantDocContents, HashMap<String, String> documents){
        for (ArrayList<String> filteredRelevantDocContent :
                filteredRelevantDocContents) {
            int i = 0;
            int k = 0;
            for (String docName : documents.keySet()) {
                for (String relevantDocContent : filteredRelevantDocContent) {
                    if (documents.get(docName).toUpperCase().contains(relevantDocContent.toUpperCase())) {
                        k++;
                    }
                }
                if (k > i) {
                    i = k;
                    associatedRelevantDocNames.add(docName);
                }
            }
        }
    }

    /**
     * Displays all the file contents containing the query and the file names
     * where the contents came from.
     * @param filteredRelevantDocContents The ArrayList containing the file
     *                                    contents with the query.
     * @param associatedRelevantDocNames  The ArrayList containing the
     *                                    associative file names from where
     *                                    the file contents in
     *                                    filteredRelevantDocContents came from.
     */
    public static void displayDocContents(ArrayList<ArrayList<String>> filteredRelevantDocContents, ArrayList<String> associatedRelevantDocNames){
        if (!(filteredRelevantDocContents.isEmpty())) {
            int noOfDocContentFound = 0;
            System.out.println("Contents:");
            for (int i = 0; i < filteredRelevantDocContents.size(); i++) {
                System.out.println("- " + associatedRelevantDocNames.get(i) + " " + filteredRelevantDocContents.get(i));
            }
            for(ArrayList<String> filteredRelevantDocContent : filteredRelevantDocContents) {
                for(String filteredRelevantDocContentWord : filteredRelevantDocContent) {
                    noOfDocContentFound++;
                }
            }
            System.out.println(noOfDocContentFound + " RESULTS FOUND.");
        }
    }

    /**
     * Displays "No results found." in the case that no file names or file
     * contents contain the query.
     * @param relevantDocNames            An ArrayList of each document name
     *                                    containing the query.
     * @param filteredRelevantDocContents An ArrayList of each file's
     *                                    contents with the query.
     */
    public static void displayNoResults(ArrayList<String> relevantDocNames,
                                        ArrayList<ArrayList<String>> filteredRelevantDocContents){
        if((relevantDocNames.isEmpty()) && (filteredRelevantDocContents.isEmpty())){
            System.out.println("No results found.");
        }
    }

    /**
     * Splits a String into an ArrayList of Strings in which each element from
     * the ArrayList is a word from the original String.
     * @param preFilteredRelevantDocContents The ArrayList containing each
     *                                       word from the original String.
     * @param relevantDocContent             The String to be split.
     */
    public static void splitRelevantDocContents(ArrayList<String> preFilteredRelevantDocContents, String relevantDocContent){
        for(String word : relevantDocContent.split(" ")){
            preFilteredRelevantDocContents.add(word);
        }
    }

    /**
     * Filters an ArrayList of Strings representing the contents of each
     * document to an ArrayList of the words containing the search query from
     * each document's contents and then stores this ArrayList in another
     * ArrayList. This process is repeated for all documents whose contents
     * contain the query.
     * @param filteredRelevantDocContents The ArrayList containing the
     *                                    relevant words from each document's
     *                                    contents.
     * @param query                       The search parameter.
     * @param relevantDocContents         The ArrayList storing each
     *                                    document's contents provided they
     *                                    generally contain the query.
     * @param splitNumber                 For the case where the query is more
     *                                    than a word and needs to be
     *                                    separated on each space, this is
     *                                    the number of times the query is
     *                                    split.
     */
    public static void filterRelevantDocContents(ArrayList<ArrayList<String>> filteredRelevantDocContents, String query, ArrayList<String> relevantDocContents, int splitNumber){
        for(String relevantDocContent : relevantDocContents){
            ArrayList<String> preFilteredRelevantDocContents = new ArrayList<>();
            Utilities.splitRelevantDocContents(preFilteredRelevantDocContents,
                    relevantDocContent);
            int sizeOfPreFilteredRelevantDocContents = preFilteredRelevantDocContents.size();
            ArrayList<String> finalPreFilteredRelevantDocContents = new ArrayList<>();
            ArrayList<String> polishedPreFilteredRelevantDocContents = new ArrayList<>();
            Utilities.groupRelevantDocContents(sizeOfPreFilteredRelevantDocContents, splitNumber, preFilteredRelevantDocContents, finalPreFilteredRelevantDocContents);
            Utilities.getRelevantInfo(polishedPreFilteredRelevantDocContents,
                    finalPreFilteredRelevantDocContents, query);
            filteredRelevantDocContents.add(polishedPreFilteredRelevantDocContents);
        }
    }

    /**
     * Gets all the words from the files in the configuration that contain
     * the search query.
     * @param filteredRelevantDocContents The ArrayList to store the words
     *                                    from each file's contents that
     *                                    contain the query.
     * @param query                       The search parameter.
     * @param relevantDocContents         The ArrayList storing each
     *                                    document's contents provided they
     *                                    generally contain the query.
     * @param splitNumber                 For the case where the query is more
     *                                    than a word and needs to be
     *                                    separated on each space, this is
     *                                    the number of times the query is
     *                                    split.
     */
    public static void getRelevantDocContents(ArrayList<ArrayList<String>> filteredRelevantDocContents, String query, ArrayList<String> relevantDocContents, int splitNumber){
        if(query.contains(" ")) {
            Utilities.filterRelevantDocContents(filteredRelevantDocContents, query, relevantDocContents, splitNumber);
        }
        else{
            Utilities.filterRelevantDocContents(filteredRelevantDocContents,
                    query, relevantDocContents);
        }
    }

    /**
     * Searches for a query in a list of files.
     * @param query          The search parameter.
     * @param folder         The folder of files to be searched.
     * @throws IOException   Just in case there's an error reading a file's
     *                       contents.
     */
    public static void searchQuery(String query, String folder) throws IOException {
        String[] args = Utilities.getFileNames(folder);
        int splitNumber = query.split(" ").length;

        HashMap<String, String> documents = new HashMap<>();
        Utilities.readDocuments(documents, args);

        ArrayList<String> docNames = new ArrayList<>();
        Utilities.getDocInfo(documents.keySet(), docNames);
        ArrayList<String> docContents = new ArrayList<>();
        Utilities.getDocInfo(documents.values(), docContents);

        Utilities.displayQuery(query);

        ArrayList<String> relevantDocNames = new ArrayList<>();
        Utilities.getRelevantInfo(relevantDocNames, docNames, query);
        ArrayList<String> relevantDocContents = new ArrayList<>();
        Utilities.getRelevantInfo(relevantDocContents, docContents, query);

        Utilities.displayDocNames(relevantDocNames);

        ArrayList<ArrayList<String>> filteredRelevantDocContents =
                new ArrayList<>();

        Utilities.getRelevantDocContents(filteredRelevantDocContents, query, relevantDocContents, splitNumber);

        ArrayList<String> associatedRelevantDocNames = new ArrayList<>();
        Utilities.associateDocNames(associatedRelevantDocNames,
                filteredRelevantDocContents, documents);

        Utilities.displayDocContents(filteredRelevantDocContents,
                associatedRelevantDocNames);

        Utilities.displayNoResults(relevantDocNames, filteredRelevantDocContents);
    }
}
