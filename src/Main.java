import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String query;
        HashMap<String, String> documents = new HashMap<>();

        for(String arg : args){
            documents.put(arg,
                    String.join(" ",
                            Files.readAllLines(Path.of(arg))));
        }
//        documents.put("Doc1", "this is a");
//        documents.put("Doc2", "this is a test");
//        documents.put("Doc3", "Doc a");
//        documents.put("Doc4", "of a search engine");

        ArrayList<String> docNames = Utilities.getDocNames(documents);
        ArrayList<String> docContents = Utilities.getDocContents(documents);

        query = "r e";
        System.out.printf("The search query was \"%s\"\n", query);

        ArrayList<String> relevantDocNames = new ArrayList<>();
        for(String docName : docNames) {
            if(docName.toUpperCase().contains(query.toUpperCase())){
                relevantDocNames.add(docName);
            }
        }

        ArrayList<String> relevantDocContents = new ArrayList<>();
        for(String docContent : docContents){
            if(docContent.toUpperCase().contains(query.toUpperCase())){
                relevantDocContents.add(docContent);
            }
        }

        if (!(relevantDocNames.isEmpty())) {
            System.out.println("Documents:");
            for (String docName : relevantDocNames) {
                System.out.println("- " + docName);
            }
        }

        if(!query.contains(" ")) {
            ArrayList<String[]> separatedRelevantDocContents =
                    new ArrayList<>();
            for (String relevantDocContent : relevantDocContents) {
                separatedRelevantDocContents.add(relevantDocContent.split(" "));
            }

            ArrayList<ArrayList<String>> filteredRelevantDocContents =
                    new ArrayList<>();
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

            ArrayList<String> associatedRelevantDocNames = new ArrayList<>();
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

            if (!(filteredRelevantDocContents.isEmpty())) {
                System.out.println("Contents:");
                for (int i = 0; i < filteredRelevantDocContents.size(); i++) {
                    System.out.println("- " + associatedRelevantDocNames.get(i) + " " + filteredRelevantDocContents.get(i));
                }
            }

            if((relevantDocNames.isEmpty()) && (filteredRelevantDocContents.isEmpty())){
                System.out.println("No results found.");
            }
        }

        else{
            int splitNumber = query.split(" ").length;
            ArrayList<ArrayList<String>> filteredRelevantDocContents = new ArrayList<>();

            for(String relevantDocContent : relevantDocContents){
                ArrayList<String> preFilteredRelevantDocContents = new ArrayList<>();
                for(String word : relevantDocContent.split(" ")){
                    preFilteredRelevantDocContents.add(word);
                }

                int sizeOfPreFilteredRelevantDocContents = preFilteredRelevantDocContents.size();

                ArrayList<String> finalPreFilteredRelevantDocContents = new ArrayList<>();

                ArrayList<String> polishedPreFilteredRelevantDocContents = new ArrayList<>();

                    for (int i = 0; i <= (sizeOfPreFilteredRelevantDocContents - splitNumber); i++) {
                        String word = "";
                        for (int j = 0; j < splitNumber; j++) {
                            word += preFilteredRelevantDocContents.get(j) + " ";
                        }
                        finalPreFilteredRelevantDocContents.add(word);
                        preFilteredRelevantDocContents.removeFirst();
                    }

                    for(String finalPreFilteredRelevantDocContent : finalPreFilteredRelevantDocContents){
                        if(finalPreFilteredRelevantDocContent.toUpperCase().contains(query.toUpperCase())){
                            polishedPreFilteredRelevantDocContents.add(finalPreFilteredRelevantDocContent.trim());
                        }
                    }
                    filteredRelevantDocContents.add(polishedPreFilteredRelevantDocContents);
            }

            ArrayList<String> associatedRelevantDocNames = new ArrayList<>();
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

            if (!(filteredRelevantDocContents.isEmpty())) {
                System.out.println("Contents:");
                for (int i = 0; i < filteredRelevantDocContents.size(); i++) {
                    System.out.println("- " + associatedRelevantDocNames.get(i) + " " + filteredRelevantDocContents.get(i));
                }
            }

            if((relevantDocNames.isEmpty()) && (filteredRelevantDocContents.isEmpty())){
                System.out.println("No results found.");
            }
        }

    }
}
