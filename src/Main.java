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
        ArrayList<String> docContent = Utilities.getDocContents(documents);

        System.out.println("Enter search query: ");
        query = args[0];

        query = query.toUpperCase();

        System.out.println("Documents:");
        for(String docName : docNames) {
            if(docName.toUpperCase().contains(query.toUpperCase())) {
                System.out.println("- " + docName);
            }
        }
    }
}
