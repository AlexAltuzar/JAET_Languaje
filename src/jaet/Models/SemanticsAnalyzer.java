package jaet.Models;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemanticsAnalyzer {

    private String declaration;
    private String name;
    private String value;
    private ArrayList<String> memory = new ArrayList<>();
    private ArrayList<ArrayList<String>> dataFunctions = new ArrayList<>();

    public SemanticsAnalyzer(){

    }

    public boolean isValid(){

        /*if(declaration.equals("<DM>"))
            memory.add(declaration);
        if(memory.size()>1){

            if(existVariable(declaration)){
                System.out.println("YA EXISTE LA VARIABLE");
            }else{
                System.out.println("No existe");
            }
        }*/

        return false;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }


    private boolean isDV = false;
    private int isRDV = -1;
    private String variable = new String();
    public void getNameDV(String terminal){
        String type = null;
        if(declaration.equals("<DV>")) isDV=true;
        if(isDV && declaration.equals("<L>") || ((declaration.equals("<V>") || declaration.equals("<RV>")) && terminal.equals("_")) ){
            variable += terminal;

        }else if(declaration.equals("<RDV>") && terminal.equals("=")){
            isRDV = 1;
            isDV = false;
        }else if(declaration.equals("<RDV>") && terminal.equals("(")){
            isRDV = 2;
            isDV = false;
        }else if(declaration.equals("<RDV>") && (terminal.equals("++") || terminal.equals("--"))){
            isRDV = 3;
            isDV = false;
        }

        switch(isRDV){
            case 1:
                if(declaration.equals("<S>")){
                    if(isValid(terminal, "^[a-zA-Z]$")) type = "VARIABLE";
                    if(isValid(terminal, "\"")) type = "CADENA";
                    if(isValid(terminal, "true|false")) type = "BOOLEANO";
                    if(isValid(terminal, "^[0-9]$")) type = "ENTERO";
                    System.out.println("********************************************************************"+variable+" - "+type);
                    isRDV = -1;
                    variable = "";
                }
                break;
            case 2:
                type = "METODO";
                System.out.println("********************************************************************"+variable+" - "+type);
                isRDV = -1;
                variable = "";
                break;
            case 3:
                type = "CONTADOR";
                System.out.println("********************************************************************"+variable+" - "+type);
                isRDV = -1;
                variable = "";
                break;
        }


    }

    public boolean isValid(String input, String regex){
        Pattern pattern;
        Matcher match;
        pattern = Pattern.compile(regex);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean existVariable(String nameVariable){
        /*for(int i=0;i<memory.size();i++){
            if(memory.get(i).equals(nameVariable))
                return true;
        }
        memory.add(nameVariable);*/
        return false;
    }
}
