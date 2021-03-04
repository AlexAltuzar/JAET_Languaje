package jaet.Models;

import java.util.ArrayList;
import java.util.Stack;

public class SyntacticAnalyzer {
    private ArrayList<String> string;
    private ArrayList<String> process = new ArrayList<>();
    public SyntacticAnalyzer(ArrayList<String> string) {
        this.string = string;
    }

    public ArrayList<String> getProcess() {
        return process;
    }

    public boolean isValid(){
        Stack pile = new Stack();
        AnalysisTable analysisTable = new AnalysisTable();
        int i = 0;
        pile.push("$");
        pile.push("<DA>");
        do {
            if(i==string.size()) i--;
            String terminal = string.get(i);
            System.out.println("___________________________________________________");
            System.out.println(pile);
            process.add(pile.toString());

            if(!analysisTable.isNotTerminal(pile.peek().toString())){
                if(pile.peek().equals(terminal)){
                    pile.pop();
                    i++;
                }else{
                    System.out.println("[ERROR], Se esperaba: "+pile.peek() + " antes de "+pile.get(pile.size()-2));
                    process.add("[ERROR], Se esperaba: "+pile.peek() + " antes de "+pile.get(pile.size()-2));
                    return false;
                }

            }else{
                if(analysisTable.existDerivation(pile.peek().toString(),terminal)){
                    pile.pop();
                    for (int j = analysisTable.getProduction().size()-1; j >=0 ; j--) {
                        pile.push(analysisTable.getProduction().get(j));
                    }
                }else{
                    System.out.println("[ERROR], No se encuentra la derivación de: "+pile.peek());
                    process.add("[ERROR], No se encuentra la derivación de: "+pile.peek());
                    return false;
                }
            }

        }while(!pile.peek().equals("$"));
        System.out.println(pile);
        process.add(pile.toString());
        return true;
    }
}
