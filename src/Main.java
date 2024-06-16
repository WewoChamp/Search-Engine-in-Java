import java.util.HashMap;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String query;
        HashMap<String, String> documents = new HashMap<>();

        documents.put("Doc1", "Hello World");
        documents.put("Doc2", "this is a");
        documents.put("Doc3", "test run");
        documents.put("Doc4", "of a search engine");

        ArrayList<String> docNames = Utilities.getDocNames(documents);
        ArrayList<String> docContents = Utilities.getDocContents(documents);

        query = args[0];
        System.out.printf("The search query was %s\n", query);

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

        ArrayList<String[]> separatedRelevantDocContents =
                new ArrayList<>();
        for(String relevantDocContent : relevantDocContents) {
            separatedRelevantDocContents.add(relevantDocContent.split(" "));
        }

        ArrayList<ArrayList<String>> filteredRelevantDocContents =
                new ArrayList<>();
        for(String[] separatedRelevantDocContent : separatedRelevantDocContents) {
            ArrayList<String> preFilteredRelevantDocContents =
                    new ArrayList<>();
            for(int i = 0 ; i < separatedRelevantDocContent.length ; i++){
                if(separatedRelevantDocContent[i].toUpperCase().contains(query.toUpperCase())){
                    if(!(separatedRelevantDocContent[i-1].isEmpty())){
                        preFilteredRelevantDocContents.add(separatedRelevantDocContent[i-1]);
                    }
                    preFilteredRelevantDocContents.add(separatedRelevantDocContent[i]);
                    if(!(separatedRelevantDocContent[i+1].isEmpty())){
                        preFilteredRelevantDocContents.add(separatedRelevantDocContent[i+1]);
                    }
                }
            }
            filteredRelevantDocContents.add(preFilteredRelevantDocContents);
        }

        if(!(relevantDocNames.isEmpty())){
            System.out.println("Documents:");
            for(String docName : relevantDocNames) {
                System.out.println("- " + docName);
            }
        }

        if(!(filteredRelevantDocContents.isEmpty())){
            System.out.println("Contents:");
            for(ArrayList<String> docContent : filteredRelevantDocContents) {
                System.out.println("- " + docContent);
            }
        }
    }
}
