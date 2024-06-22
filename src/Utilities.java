import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Utilities {
    public static void readDocuments(HashMap<String, String> documents,
                                     String[] input) throws IOException {
        for(String document : input){
            documents.put(document,
                    String.join(" ",
                            Files.readAllLines(Path.of(document))));
        }
    }

    public static void getDocInfo(Set<String> info,
                                               ArrayList<String> docInfo){
        docInfo.addAll(info);
    }

    public static void getDocInfo(Collection<String> info,
                                  ArrayList<String> docInfo){
        docInfo.addAll(info);
    }

    public static void displayQuery(String query){
        System.out.printf("The search query was \"%s\"\n", query);
    }

    public static void getRelevantInfo(ArrayList<String> relevantDocInfo,
                                       ArrayList<String> docInfo, String query){
        for(String info : docInfo) {
            if(info.toUpperCase().contains(query.toUpperCase())){
                relevantDocInfo.add(info);
            }
        }
    }

    public static void displayDocNames(ArrayList<String> docNames){
        if (!(docNames.isEmpty())) {
            System.out.println("Documents:");
            for (String docName : docNames) {
                System.out.println("- " + docName);
            }
        }
    }

    public static void separateRelevantDocContents(ArrayList<String[]> separatedRelevantDocContents, ArrayList<String> relevantDocContents){
        for (String relevantDocContent : relevantDocContents) {
            separatedRelevantDocContents.add(relevantDocContent.split(" "));
        }
    }

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

    public static void displayDocContents(ArrayList<ArrayList<String>> filteredRelevantDocContents, ArrayList<String> associatedRelevantDocNames){
        if (!(filteredRelevantDocContents.isEmpty())) {
            System.out.println("Contents:");
            for (int i = 0; i < filteredRelevantDocContents.size(); i++) {
                System.out.println("- " + associatedRelevantDocNames.get(i) + " " + filteredRelevantDocContents.get(i));
            }
        }
    }

    public static void displayNoResults(ArrayList<String> relevantDocNames,
                                        ArrayList<ArrayList<String>> filteredRelevantDocContents){
        if((relevantDocNames.isEmpty()) && (filteredRelevantDocContents.isEmpty())){
            System.out.println("No results found.");
        }
    }

    public static void splitRelevantDocContents(ArrayList<String> preFilteredRelevantDocContents, String relevantDocContent){
        for(String word : relevantDocContent.split(" ")){
            preFilteredRelevantDocContents.add(word);
        }
    }

    public static void chooseFromGroupedRelevantDocContents(ArrayList<String> finalPreFilteredRelevantDocContents, ArrayList<String> polishedPreFilteredRelevantDocContents, String query){
        for(String finalPreFilteredRelevantDocContent : finalPreFilteredRelevantDocContents){
            if(finalPreFilteredRelevantDocContent.toUpperCase().contains(query.toUpperCase())){
                polishedPreFilteredRelevantDocContents.add(finalPreFilteredRelevantDocContent.trim());
            }
        }
    }

    public static void filterRelevantDocContents(ArrayList<ArrayList<String>> filteredRelevantDocContents, String query, ArrayList<String> relevantDocContents, int splitNumber){
        for(String relevantDocContent : relevantDocContents){
            ArrayList<String> preFilteredRelevantDocContents = new ArrayList<>();
            Utilities.splitRelevantDocContents(preFilteredRelevantDocContents,
                    relevantDocContent);
            int sizeOfPreFilteredRelevantDocContents = preFilteredRelevantDocContents.size();
            ArrayList<String> finalPreFilteredRelevantDocContents = new ArrayList<>();
            ArrayList<String> polishedPreFilteredRelevantDocContents = new ArrayList<>();
            Utilities.groupRelevantDocContents(sizeOfPreFilteredRelevantDocContents, splitNumber, preFilteredRelevantDocContents, finalPreFilteredRelevantDocContents);
            Utilities.chooseFromGroupedRelevantDocContents(finalPreFilteredRelevantDocContents, polishedPreFilteredRelevantDocContents, query);
            filteredRelevantDocContents.add(polishedPreFilteredRelevantDocContents);
        }
    }

    public static void getRelevantDocContents(ArrayList<ArrayList<String>> filteredRelevantDocContents, String query, ArrayList<String> relevantDocContents, int splitNumber){
        if(query.contains(" ")) {
            Utilities.filterRelevantDocContents(filteredRelevantDocContents, query, relevantDocContents, splitNumber);
        }
        else{
            Utilities.filterRelevantDocContents(filteredRelevantDocContents,
                    query, relevantDocContents);
        }
    }

    public static void searchQuery(String query, String[] args) throws IOException {
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
