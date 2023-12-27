package UI;

import javax.swing.*;

import controller.Controller;
import logic.Automata;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AutomatonInputGUI {
    private JFrame frame;
    private JFrame startMenuFrame;
    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<String> alphabet = new ArrayList<>();
    private Map<String, Map<String, String>> transitions = new HashMap<>();
    private String startingState = "";
    private ArrayList<String> endingStates = new ArrayList<>();


    // Create labels and text fields for input
    JLabel statesLabel = new JLabel("States (comma-separated):");
    JTextField statesField = new JTextField();
    JLabel alphabetLabel = new JLabel("Alphabet (comma-separated):");
    JTextField alphabetField = new JTextField();
    JLabel transitionsLabel = new JLabel("Transitions (comma-separated):");
    JTextField transitionsField = new JTextField();
    JLabel initialStateLabel = new JLabel("Initial State:");
    JTextField initialStateField = new JTextField();
    JLabel endingStatesLabel = new JLabel("Accepting States (comma-separated):");
    JTextField endingStatesField = new JTextField();
    JLabel languageLabel = new JLabel("Language (comma-separated):");
    JTextField languageField = new JTextField();
    JLabel regex = new JLabel("Regular Expression");
    JTextField regexField = new JTextField();

    // Buttons
    JButton submitButton = new JButton("Submit");
    JButton exitButton = new JButton("Exit");
    JButton saveButton = new JButton("Save Automaton");
    JButton deleButton = new JButton("Delete Automaton");
    JButton displayAutomatesCreatedButton = new JButton("My automatons");
    JButton notice = new JButton("Notice");

    public AutomatonInputGUI() {
        startMenu();
    }

    public void startMenu() {
        startMenuFrame = new JFrame("Main menu");
        startMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenuFrame.setSize(400, 300);
        startMenuFrame.setLayout(new GridLayout(3, 1));

        JButton regexToAutomata = new JButton("Regex-To-Automata");
        JButton languageRecon = new JButton("Language-Recognization");
        JButton exit = new JButton("Exit");

        regexToAutomata.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startMenuFrame.setVisible(false);
                regexMenu();
            }
        });

        languageRecon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startMenuFrame.setVisible(false);
                manageAutomata();
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        startMenuFrame.add(regexToAutomata);
        startMenuFrame.add(languageRecon);
        startMenuFrame.add(exit);

        startMenuFrame.setVisible(true);
    }

    public void manageAutomata() {
        frame = new JFrame("Finite Automaton Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(12, 2));

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Parse input and populate the automaton properties
                states = new ArrayList<>(Arrays.asList(statesField.getText().split(",")));
                alphabet = new ArrayList<>(Arrays.asList(alphabetField.getText().split(",")));
                String[] transitionsArray = transitionsField.getText().split(",");
                if (transitionsArray.length >=3 ){
                    for (int i = 0; i < transitionsArray.length; i += 3) {
                        transitions.computeIfAbsent(transitionsArray[i], k -> new HashMap<>()).put(transitionsArray[i + 1], transitionsArray[i + 2]);
                    }
                } else {
                    transitions = new HashMap<>();
                }
                String[] validateStartingState = initialStateField.getText().split(",");
                if (validateStartingState.length > 1 || validateStartingState.length == 0 || states.isEmpty() || alphabet.isEmpty() || transitions.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "Your inputs are invalide.");
                } else {
                    startingState = initialStateField.getText();
                    endingStates = new ArrayList<>(Arrays.asList(endingStatesField.getText().split(",")));

                    ArrayList<String> language = new ArrayList<>(Arrays.asList(languageField.getText().split(",")));

                    boolean result = Controller.recognizeAutomate(states, alphabet, transitions, startingState, endingStates, language);

                    // Display a message or process the automaton input as needed
                    String message = result ? "The automaton recongnize the language" : "The automaton doesn't recognize the language";

                    JOptionPane.showMessageDialog(frame, message);   
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                try {
                    String automatonName = JOptionPane.showInputDialog("Automaton name:");
                    // Parse input and populate the automaton properties
                    states = new ArrayList<>(Arrays.asList(statesField.getText().split(",")));
                    alphabet = new ArrayList<>(Arrays.asList(alphabetField.getText().split(",")));
                    String[] transitionsArray = transitionsField.getText().split(",");
                    if (transitionsArray.length >= 3) {
                        for (int i = 0; i < transitionsArray.length; i += 3) {
                            transitions.computeIfAbsent(transitionsArray[i], k -> new HashMap<>()).put(transitionsArray[i + 1], transitionsArray[i + 2]);
                        }
                    }
                    startingState = initialStateField.getText();
                    endingStates = new ArrayList<>(Arrays.asList(endingStatesField.getText().split(",")));
                    if (automatonName != null) {
                        if (automatonName.equals("")) {
                            JOptionPane.showMessageDialog(frame, "Error: Invalid Name", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String confirmationMessage = "Are you sure ?";
                            int userChoice = JOptionPane.showConfirmDialog(frame, confirmationMessage);

                            if (userChoice == JOptionPane.YES_OPTION) {
                                String response = Controller.saveAutomate(states, alphabet, transitions, startingState, endingStates, automatonName);
                                JOptionPane.showMessageDialog(frame, response);
                            }
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        deleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String automatonName = JOptionPane.showInputDialog("Automaton name:");
                if (!automatonName.equals("")) {
                    int userChoice = JOptionPane.showConfirmDialog(frame, "Are you sure ?");
                    if (userChoice == JOptionPane.YES_OPTION){
                        String response = Controller.deleteAutomate(automatonName);
                        JOptionPane.showMessageDialog(frame, response);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Operation canceled");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: Invalid name", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        displayAutomatesCreatedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ArrayList<ArrayList<String>> list = Controller.getAutomatesNames();
                if (list.get(0).isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "You did not save any automaton yet.");
                } else {
                    StringBuilder automates = new StringBuilder();
                    for (int i = 0; i < list.get(0).size(); i++) {
                        automates .append("****").append("\n")
                            .append("Name: ").append(list.get(0).get(i)).append("\n")
                            .append("States: ").append(list.get(1).get(i)).append("\n")
                            .append("Alphabet: ").append(list.get(2).get(i)).append("\n")
                            .append("Transitions: ").append(list.get(3).get(i)).append("\n")
                            .append("Initial state: ").append(list.get(4).get(i)).append("\n")
                            .append("Ending states: ").append(list.get(5).get(i)).append("\n\n");
                    }

                    JTextArea textArea = new JTextArea(10, 30);
                    textArea.setText(automates.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(
                        frame,
                        scrollPane,
                        "Your automatons",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        notice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                StringBuilder example = new StringBuilder();

                example.append("******** EXAMPLE OF INPUT ********").append("\n")  
                    .append("States:    A,B").append("\n")
                    .append("Alphabet:    a,b").append("\n")
                    .append("Transitions:    A,a,A,A,b,B,B,a,A,B,b,B").append("\n")
                    .append("Initial State:    A").append("\n")
                    .append("Accepting States:    B").append("\n")
                    .append("Language:    abbbab,babbaab").append("\n").append("\n")
                    .append("Note: For the transitions 'A,b,B' for example, the first letter 'A' represent\n")
                    .append("the state that we are leaving, the letter 'b' represent the condition to go\n")
                    .append("to the next state and the letter 'B' represent the arrival state.");

                example.append("\n\n");

                example.append("******** How To Use ********").append("\n")
                    .append("You must enter an automata or a regex to verify if a language is valid.").append("\n")
                    .append("If you enter both, only the regex will be considerated.");

                JOptionPane.showMessageDialog(frame, example.toString());
            }
        });

        // Add components to the frame
        frame.add(statesLabel);
        frame.add(statesField);
        frame.add(alphabetLabel);
        frame.add(alphabetField);
        frame.add(transitionsLabel);
        frame.add(transitionsField);
        frame.add(initialStateLabel);
        frame.add(initialStateField);
        frame.add(endingStatesLabel);
        frame.add(endingStatesField);
        frame.add(new JLabel("---------------------------------------------O"));
        frame.add(new JLabel("R-----------------------------------------------"));
        frame.add(regex);
        frame.add(regexField);
        frame.add(new JLabel("------------------------------------------------"));
        frame.add(new JLabel("------------------------------------------------"));
        frame.add(languageLabel);
        frame.add(languageField);
        frame.add(submitButton);
        frame.add(notice);
        frame.add(saveButton);
        frame.add(deleButton);
        frame.add(displayAutomatesCreatedButton);
        frame.add(exitButton);

        frame.setVisible(true);
    }

    // Convert an automata to a regex
    public void manageRegex(String regexInput) {

    }

    public void regexMenu() {

    }

    // Convert a regex to an automata
    public void convertRegex(String regexInput) {
        String regex = String.join("", regexInput.split(" "));
        
    }
}
