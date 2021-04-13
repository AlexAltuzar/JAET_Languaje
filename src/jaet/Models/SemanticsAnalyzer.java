package jaet.Models;

import java.util.ArrayList;

public class SemanticsAnalyzer {

    private String declaration;
    private String name;
    private String value;
    private ArrayList<String> memory = new ArrayList<>();
    private ArrayList<ArrayList<String>> dataFunctions = new ArrayList<>();

    public boolean isValid(){

        if(declaration.equals("<DM>"))
            memory.add(declaration);
        if(memory.size()>1){

            if(existVariable(declaration)){
                System.out.println("YA EXISTE LA VARIABLE");
            }else{
                System.out.println("No existe");
            }
        }

        return false;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public boolean existVariable(String nameVariable){
        for(int i=0;i<memory.size();i++){
            if(memory.get(i).equals(nameVariable))
                return true;
        }
        memory.add(nameVariable);
        return false;
    }
}
