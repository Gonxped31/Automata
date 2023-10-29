package Logic;

import javax.swing.SwingUtilities;

import UI.AutomatonInputGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutomatonInputGUI());
    }
}