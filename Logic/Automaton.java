package logic;
import java.util.ArrayList;
import java.util.Map;

public class Automaton {
    
    private ArrayList<String> Q;
    private ArrayList<String> sigma;
    private Map<String, Map<String, String>> delta;
    private String q_0;
    private ArrayList<String> F;

    public Automaton(ArrayList<String> states, ArrayList<String> alphabet,
                     Map<String, Map<String, String>> functions, String startingState, ArrayList<String> endingStates) {

        this.Q = states;
        this.sigma = alphabet;
        this.delta = functions;
        this.q_0 = startingState;
        this.F = endingStates;
    }


    public boolean recognize(ArrayList<String> language){
        return language.stream().allMatch(this::recognizeWord);
    }

    private boolean recognizeWord(String word){
        String[] symbols = word.split("");
        int numb = 0;

        // Check if all the symbols are in the alphabet
        for (String symbol : symbols) {
            if (!sigma.contains(symbol)){
                break;
            }
            numb++;
        }

        if (numb == symbols.length){
            String currentState = q_0;
            String nextState = q_0;

            for (String symbol : symbols) {
                Map<String, String> map = delta.get(currentState);
                if (map != null) {
                    if (map.containsKey(symbol)){
                        nextState = map.get(symbol);
                        currentState = nextState;
                    } else {
                        break;
                    }
                }
            }
            return F.contains(currentState);
        } else {
            return false;
        }   

    }

    public ArrayList<String> getQ(){
        return Q;
    }
    public ArrayList<String> getSigma(){
        return sigma;
    }
    public Map<String, Map<String, String>> getDelta(){
        return delta;
    }
    public String getQ_0(){
        return q_0;
    }
    public ArrayList<String> getF(){
        return F;
    }

}