package UI;

import javax.swing.*;

import logic.Automate;
import controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AutomatonInputGUI {
    private JFrame frame;
    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<String> alphabet = new ArrayList<>();
    private Map<String, Map<String, String>> transitions = new HashMap<>();
    private String startingState = "";
    private ArrayList<String> endingStates = new ArrayList<>();

    public AutomatonInputGUI() {
        frame = new JFrame("Finite Automaton Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(10, 2));

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
        JLabel automateNameLabel = new JLabel("Automate name:");
        JTextField automateNameField = new JTextField();

        // Buttons
        JButton submitButton = new JButton("Submit");
        JButton exitButton = new JButton("Exit");
        JButton saveButton = new JButton("Save Automate");
        JButton deleButton = new JButton("Delete Automate");
        JButton displayAutomatesCreatedButton = new JButton("My automates");

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
                startingState = initialStateField.getText();
                endingStates = new ArrayList<>(Arrays.asList(endingStatesField.getText().split(",")));

                ArrayList<String> language = new ArrayList<>(Arrays.asList(languageField.getText().split(",")));

                boolean result = Controller.recognizeAutomate(states, alphabet, transitions, startingState, endingStates, language);

                // Display a message or process the automaton input as needed
                String message = result ? "L'automate reconnait ce language" : "L'automate ne reconnait pas ce language";

                JOptionPane.showMessageDialog(frame, message);

                // Clear the text fields
                /*statesField.setText("");
                alphabetField.setText("");
                transitionsField.setText("");
                initialStateField.setText("");
                endingStatesField.setText("");
                languageField.setText("");*/
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Define the action to exit the application
                System.exit(0);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // Parse input and populate the automaton properties
                states = new ArrayList<>(Arrays.asList(statesField.getText().split(",")));
                alphabet = new ArrayList<>(Arrays.asList(alphabetField.getText().split(",")));
                String[] transitionsArray = transitionsField.getText().split(",");
                for (int i = 0; i < transitionsArray.length; i += 3) {
                    transitions.computeIfAbsent(transitionsArray[i], k -> new HashMap<>()).put(transitionsArray[i + 1], transitionsArray[i + 2]);
                }
                startingState = initialStateField.getText();
                endingStates = new ArrayList<>(Arrays.asList(endingStatesField.getText().split(",")));
                String automateName = automateNameField.getText();

                boolean response = Controller.saveAutomate(states, alphabet, transitions, startingState, endingStates, automateName);

                String message = response ? "Automate saved successfuly!" : "Error occured.";
                JOptionPane.showMessageDialog(frame, message);
            }
        });

        deleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String automateName = automateNameField.getText();
                boolean response = Controller.deleteAutomate(automateName);
                String message = response ? "Automate deleted successfully" : "Error durin the process.";
                JOptionPane.showMessageDialog(frame, message);
            }
        });

        displayAutomatesCreatedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                StringBuilder automates = new StringBuilder();
                automates.append("YOUR AUTOMATES: ").append("\n");
                ArrayList<ArrayList<String>> list = Controller.getAutomatesNames();
                
                for (int i = 0; i < list.get(0).size(); i++) {
                    automates .append("**Automate-" + i + "**").append("\n")
                        .append("Name: ").append(list.get(0).get(i)).append("\n")
                        .append("States: ").append(list.get(1).get(i)).append("\n")
                        .append("Alphabet: ").append(list.get(2).get(i)).append("\n")
                        .append("Transitions: ").append(list.get(3).get(i)).append("\n")
                        .append("Initial state: ").append(list.get(4).get(i)).append("\n")
                        .append("Ending states: ").append(list.get(5).get(i)).append("\n\n");
                }

                JOptionPane.showMessageDialog(frame, automates.toString());
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
        frame.add(languageLabel);
        frame.add(languageField);
        frame.add(submitButton);
        frame.add(exitButton);
        frame.add(automateNameLabel);
        frame.add(automateNameField);
        frame.add(saveButton);
        frame.add(deleButton);
        frame.add(displayAutomatesCreatedButton);

        frame.setVisible(true);
    }
}
