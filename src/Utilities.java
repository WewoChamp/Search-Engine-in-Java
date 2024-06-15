import java.util.ArrayList;
import java.util.HashMap;

public class Utilities {
    public static ArrayList<String> getDocNames(HashMap<String, String> documents){
        ArrayList<String> docNames = new ArrayList<>();
        docNames.addAll(documents.keySet());
        return docNames;
    }

    public static ArrayList<String> getDocContents(HashMap<String, String> documents){
        ArrayList<String> docContents = new ArrayList<>();
        docContents.addAll(documents.values());
        return docContents;
    }
}
