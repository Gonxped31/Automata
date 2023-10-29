package Logic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //getAutomateInfo();
        String[] tab = {"A", "B", "C", "D"};
        ArrayList<String> states = new ArrayList<>(Arrays.asList(tab));

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("u");
        alphabet.add("v");
        alphabet.add("w");
        alphabet.add("x");
        alphabet.add("y");
        alphabet.add("z");

        Map<String, Map<String, String>> transitions = new HashMap<>();

        Map<String, String> mapA = new HashMap<>();
        mapA.put("u", "B");
        mapA.put("v", "B");
        mapA.put("w", "B");
        mapA.put("x", "B");
        mapA.put("y", "B");
        mapA.put("z", "B");
        transitions.put("A", mapA);


        Map<String, String> mapB = new HashMap<>();
        mapB.put("u", "C");
        mapB.put("v", "C");
        mapB.put("w", "C");
        mapB.put("x", "C");
        mapB.put("y", "C");
        mapB.put("z", "C");
        transitions.put("B", mapB);

        Map<String, String> mapC = new HashMap<>();
        mapC.put("y", "D");
        transitions.put("C", mapC);

        transitions.put("D", null);

        ArrayList<String> endingStates = new ArrayList<>();
        endingStates.add("D");

        Automate automate = new Automate(states, alphabet, transitions, "A", endingStates);
        ArrayList<String> language = new ArrayList<>();
        language.add("uuyvwyuz");

        System.out.println(automate.recognize(language));
    }

    public static void getAutomateInfo(){

    }

}