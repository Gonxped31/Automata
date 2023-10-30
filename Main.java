
import javax.swing.SwingUtilities;

import UI.AutomatonInputGUI;
import database.DataBase;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutomatonInputGUI());
    }
}