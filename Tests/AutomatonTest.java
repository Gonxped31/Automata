package tests;
import org.junit.jupiter.api.Test;

import logic.Automata;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AutomatonTest {
    
    // Recognize the word 'mot'
    @Test
    public void testCase1() {
        ArrayList<String> states = new ArrayList<>();
        states.addAll(Arrays.asList("A", "B", "C", "D"));

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.addAll(Arrays.asList("m", "o", "t", "s", "y", "z"));

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

        Automata automaton = new Automata(states, alphabet, transitions, "A", endingStates);

        ArrayList<String> validLanguage = new ArrayList<>();
        validLanguage.add("mot");
        assertTrue(automaton.recognize(validLanguage));
    }

    // Recognize any word that contains 'abba' in the defined alphabet
    @Test
    public void testCase2() {
        ArrayList<String> states = new ArrayList<>();
        states.addAll(Arrays.asList("A", "B", "C", "D", "F"));

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.addAll(Arrays.asList("a", "b"));

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

        Automata automaton = new Automata(states, alphabet, transitions, "A", endingStates);

        ArrayList<String> validLanguage = new ArrayList<>();
        validLanguage.add("abba");
        validLanguage.add("aababbabab");
        assertTrue(automaton.recognize(validLanguage));

        ArrayList<String> inValidLanguage = new ArrayList<>();
        inValidLanguage.add("abbab");
        inValidLanguage.add("babab");
        assertFalse(automaton.recognize(inValidLanguage));
    }

    // Recognize any word that start with 'a' and end with 'b'
    @Test
    public void testCase3() {
        ArrayList<String> states = new ArrayList<>();
        states.addAll(Arrays.asList("A", "B", "C"));

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.addAll(Arrays.asList("a", "b"));

        Map<String, String> mapA = new HashMap<>();
        mapA.put("a", "B");

        Map<String, String> mapB = new HashMap<>();
        mapB.put("a", "B");
        mapB.put("b", "C");

        Map<String, String> mapC = new HashMap<>();
        mapC.put("a", "B");
        mapC.put("b", "C");

        ArrayList<String> endingStates = new ArrayList<>();
        endingStates.add("C");

        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("A", mapA);
        transitions.put("B", mapB);
        transitions.put("C", mapC);
        transitions.put("D", new HashMap<>());

        Automata automaton = new Automata(states, alphabet, transitions, "A", endingStates);

        ArrayList<String> validLanguage = new ArrayList<>();
        validLanguage.add("abbababbbaaabab");
        validLanguage.add("ab");
        validLanguage.add("aabbbababbab");
        assertTrue(automaton.recognize(validLanguage));

        ArrayList<String> inValidLanguage = new ArrayList<>();
        inValidLanguage.add("bbaab");
        inValidLanguage.add("baba");
        inValidLanguage.add("ababa");
        assertFalse(automaton.recognize(inValidLanguage));
    }

    @Test
    public void testEmptyAutomaton() {
        ArrayList<String> states = new ArrayList<>();
        ArrayList<String> alphabet = new ArrayList<>();
        Map<String, Map<String, String>> transitions = new HashMap<>();
        String startingState = "A";
        ArrayList<String> endingStates = new ArrayList<>();
        
        Automata automaton = new Automata(states, alphabet, transitions, startingState, endingStates);

        ArrayList<String> language = new ArrayList<>();
        language.add("");
        
        assertFalse(automaton.recognize(language));
    }

    @Test
    public void testNonexistentTransition() {
        ArrayList<String> states = new ArrayList<>(Arrays.asList("A", "B"));
        ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("a", "b"));
        
        Map<String, String> mapA = new HashMap<>();
        mapA.put("a", "B");

        ArrayList<String> endingStates = new ArrayList<>(Collections.singletonList("B"));
        
        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("A", mapA);

        Automata automaton = new Automata(states, alphabet, transitions, "A", endingStates);

        ArrayList<String> validLanguage = new ArrayList<>(Collections.singletonList("a"));
        ArrayList<String> inValidLanguage = new ArrayList<>(Collections.singletonList("b"));
        
        assertTrue(automaton.recognize(validLanguage));
        assertFalse(automaton.recognize(inValidLanguage));
    }

    @Test
    public void testNonexistentInitialState() {
        ArrayList<String> states = new ArrayList<>(Arrays.asList("A", "B"));
        ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("a", "b"));
        
        Map<String, String> mapA = new HashMap<>();
        mapA.put("a", "B");

        ArrayList<String> endingStates = new ArrayList<>(Collections.singletonList("B"));
        
        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("A", mapA);

        // "C" is not a valid initial state.
        Automata automaton = new Automata(states, alphabet, transitions, "C", endingStates);

        ArrayList<String> validLanguage = new ArrayList<>(Collections.singletonList("a"));
        ArrayList<String> inValidLanguage = new ArrayList<>(Collections.singletonList("b"));
        
        assertFalse(automaton.recognize(validLanguage));
        assertFalse(automaton.recognize(inValidLanguage));
    }

    @Test
    public void testInvalidSymbolInLanguage() {
        ArrayList<String> states = new ArrayList<>(Arrays.asList("A", "B"));
        ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("a", "b"));
        
        Map<String, String> mapA = new HashMap<>();
        mapA.put("a", "B");
    
        ArrayList<String> endingStates = new ArrayList<>(Collections.singletonList("B"));
        
        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("A", mapA);
    
        Automata automaton = new Automata(states, alphabet, transitions, "A", endingStates);
    
        ArrayList<String> invalidLanguage = new ArrayList<>(Collections.singletonList("c"));
        
        assertFalse(automaton.recognize(invalidLanguage));
    }
    
    @Test
    public void testLanguageWithMultipleWords() {
        ArrayList<String> states = new ArrayList<>(Arrays.asList("A", "B"));
        ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("a", "b"));
        
        Map<String, String> mapA = new HashMap<>();
        mapA.put("a", "A");
        mapA.put("b", "B");
    
        ArrayList<String> endingStates = new ArrayList<>(Collections.singletonList("B"));
        
        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("A", mapA);
    
        Automata automaton = new Automata(states, alphabet, transitions, "A", endingStates);
    
        ArrayList<String> validLanguage = new ArrayList<>(Arrays.asList("b", "ab", "ba", "baab"));
        
        assertTrue(automaton.recognize(validLanguage));
        assertFalse(automaton.recognize(new ArrayList<>(Arrays.asList("a")))); // "a" should not be recognized.
    }

    @Test
    public void testInvalidLanguage() {
        ArrayList<String> states = new ArrayList<>(Arrays.asList("A", "B"));
        ArrayList<String> alphabet = new ArrayList<>(Arrays.asList("a", "b"));
        
        Map<String, String> mapA = new HashMap<>();
        mapA.put("a", "A");
    
        ArrayList<String> endingStates = new ArrayList<>(Collections.singletonList("B"));
        
        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("A", mapA);
    
        Automata automaton = new Automata(states, alphabet, transitions, "A", endingStates);
    
        ArrayList<String> invalidLanguage = new ArrayList<>(Collections.singletonList("a"));
        
        assertFalse(automaton.recognize(invalidLanguage));
    }
    
    @Test
    public void testLargeAutomatonAndLanguage() {
        ArrayList<String> states = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            states.add("Q" + i);
        }
    
        ArrayList<String> alphabet = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(String.valueOf(c));
        }
    
        Map<String, Map<String, String>> transitions = new HashMap<>();
        for (String state : states) {
            Map<String, String> transitionMap = new HashMap<>();
            for (String symbol : alphabet) {
                // Create random transitions to different states.
                transitionMap.put(symbol, "Q" + (new Random().nextInt(100)));
            }
            transitions.put(state, transitionMap);
        }
    
        String startingState = "Q0";
        ArrayList<String> endingStates = new ArrayList<>();
        endingStates.add("Q99");
    
        Automata automaton = new Automata(states, alphabet, transitions, startingState, endingStates);
    
        ArrayList<String> language = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            StringBuilder word = new StringBuilder();
            int wordLength = new Random().nextInt(20) + 1;
            for (int j = 0; j < wordLength; j++) {
                word.append(alphabet.get(new Random().nextInt(26)));
            }
            language.add(word.toString());
        }
    
        assertFalse(automaton.recognize(language));
    }

}
