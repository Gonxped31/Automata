package Tests;
import Logic.Automate;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class AutomateTest {
    
    @Test
    public void testCase1() {
        ArrayList<String> states = new ArrayList<>();
        states.add("A");
        states.add("B");
        states.add("C");
        states.add("D");

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("m");
        alphabet.add("o");
        alphabet.add("t");
        alphabet.add("s");
        alphabet.add("y");
        alphabet.add("z");

        Map<String, String> mapA = new HashMap<>();
        mapA.put("m", "B");

        Map<String, String> mapB = new HashMap<>();
        mapB.put("o", "C");

        Map<String, String> mapC = new HashMap<>();
        mapC.put("t", "D");

        ArrayList<String> endingStates = new ArrayList<>();
        endingStates.add("D");

        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("A", mapA);
        transitions.put("B", mapB);
        transitions.put("C", mapC);
        transitions.put("D", new HashMap<>());

        Automate automate = new Automate(states, alphabet, transitions, "A", endingStates);

        ArrayList<String> validLanguage = new ArrayList<>();
        validLanguage.add("mot");
        assertTrue(automate.recognize(validLanguage));
    }

    @Test
    public void testCase2() {
        ArrayList<String> states = new ArrayList<>();
        states.add("A");
        states.add("B");
        states.add("C");
        states.add("D");
        states.add("F");

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");

        Map<String, String> mapA = new HashMap<>();
        mapA.put("a", "B");
        mapA.put("b", "A");

        Map<String, String> mapB = new HashMap<>();
        mapB.put("a", "B");
        mapB.put("b", "C");

        Map<String, String> mapC = new HashMap<>();
        mapC.put("a", "B");
        mapC.put("b", "D");

        Map<String, String> mapD = new HashMap<>();
        mapD.put("a", "F");
        mapD.put("b", "C");

        ArrayList<String> endingStates = new ArrayList<>();
        endingStates.add("F");

        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("A", mapA);
        transitions.put("B", mapB);
        transitions.put("C", mapC);
        transitions.put("D", mapD);
        transitions.put("F", new HashMap<>());

        Automate automate = new Automate(states, alphabet, transitions, "A", endingStates);

        ArrayList<String> validLanguage = new ArrayList<>();
        validLanguage.add("abba");
        validLanguage.add("aababbabab");
        assertTrue(automate.recognize(validLanguage));

        ArrayList<String> inValidLanguage = new ArrayList<>();
        inValidLanguage.add("abbab");
        inValidLanguage.add("babab");
        assertFalse(automate.recognize(inValidLanguage));
    }
}
