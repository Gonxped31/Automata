package controller;

import java.util.ArrayList;
import java.util.Map;

import database.DataBase;
import logic.Automate;


public class Controller {
    public static boolean recognizeAutomate(ArrayList<String> states, ArrayList<String> alphabet,
     Map<String, Map<String, String>> functions, String startingState, ArrayList<String> endingStates,
      ArrayList<String> language){

        Automate automate = new Automate(states, alphabet, functions, startingState, endingStates);

        return automate.recognize(language);
    }

    public static String saveAutomate(ArrayList<String> states, ArrayList<String> alphabet,
     Map<String, Map<String, String>> functions, String startingState, ArrayList<String> endingStates, String automateName){

        Automate automate = new Automate(states, alphabet, functions, startingState, endingStates);
        return DataBase.saveAutomate(automateName, automate);
    }

    public static String deleteAutomate(String automateName){
        return DataBase.removeAutomate(automateName);
    }

    public static ArrayList<ArrayList<String>> getAutomatesNames(){
        return DataBase.getAutomates();
    }
}
