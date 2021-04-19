package jaet.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemanticsAnalyzer {

    private String notTerminal;
    private String terminal;
    private String errorMessage = "";

    private ArrayList memory = new ArrayList();
    private ArrayList<T_NotT> process = new ArrayList<>();
    private ArrayList<ArrayList<T_NotT>> bodys = new ArrayList<>();
    private ArrayList<Function> callFunctions = new ArrayList<>();
    private ArrayList<T_NotT> tempBody = new ArrayList<>();
    private Body body = new Body();
    private int positionSubBody = 0;
    private ArrayList<Variable> parameters = new ArrayList<>();
    private Variable var = new Variable();

    //VARIABLE
    private String variable = new String();
    private boolean isDV = false;
    private int isRDV = 0;
    public boolean isVar = false;

    //FOR
    private int isDF = -1;
    private String type = null;
    private boolean analizeExpression = false;

    //SCEGLIERE
    private int isDSC = -1;
    private String dataTypeDSC = "";

    //MAIN
    private boolean isDM = false;

    //LLAMADA DE FUNZIONE
    private boolean isCallFunction = false;
    private boolean isSentence = false;
    //FUNZIONE
    private int isDFUN = 0;
    private Function function = new Function();
    //STAMP
    private boolean isDS = false;
    //ENTEROKAY
    private boolean isDE = false;

    //RETORNO
    private boolean analyzeReturn = false;

    //IF Y WHILE
    private int isDIForDW = -1;
    private int isStructure = -1;
    private String types [] = new String[2];
    private String operator;
    private String structure;


    public void setNotTerminal(String notTerminal) {
        this.notTerminal = notTerminal;
    }

    public void setProcess(ArrayList<T_NotT> process) {
        this.process = process;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isValid(){
        clearAll();
        for (int i = 0; i < process.size(); i++) {
            notTerminal = process.get(i).getNotTerminal();
            terminal = process.get(i).getTerminal();
            setMain();
            setFunction();
        }
        if(verifyFunctions()){
            return false;
        }
        extractBodys();
        extractReturn();
        if(!extractVariables()){
            return false;
        }
        return true;
    }

    public void extractBodys(){
        ArrayList<T_NotT> bodyMemory = new ArrayList<>();
        int j =  process.size()-1;
        int position;

        while(j>=0){
            notTerminal = process.get(j).getNotTerminal();
            terminal = process.get(j).getTerminal();
            bodyMemory.add(new T_NotT(notTerminal, terminal));
            if(notTerminal.equals("<DFUN>")){
                position = j;
                bodys.add(bodyMemory);
                bodyMemory = new ArrayList<>();
                j = position;
            }else if(notTerminal.equals("<DM>")){
                position = j;
                bodys.add(bodyMemory);
                bodyMemory = new ArrayList<>();
                j = position;
            }
            j--;
        }
        Collections.reverse(bodys);
        for (int i = 0; i < bodys.size(); i++) {
            ArrayList<T_NotT> temp = bodys.get(i);
            for (int k = temp.size()-1; k >=0; k--) {
                System.out.println("NotTerminal "+temp.get(k).getNotTerminal()+" - Terminal "+temp.get(k).getTerminal()+" - "+k);
            }
            System.out.println("*****************************************************************");
        }
    }


    public boolean extractVariables(){
        for (int i = 0; i < memory.size(); i++) {
            if(memory.get(i).getClass().getSimpleName().equals("Function")){
                analyzeReturn = true;
                Function temp = (Function) memory.get(i);
                body = temp.getBody();
            }else{ body = (Body) memory.get(i); }

            tempBody = bodys.get(i);
            positionSubBody = tempBody.size()-1;
            for (positionSubBody = tempBody.size()-1; positionSubBody >= 0 ; positionSubBody--) {
                if(!analizeProcess(body)){
                    return false;
                }
                //System.out.println("CONTADOR "+positionSubBody);
            }

        }

        for (int i = 0; i < memory.size(); i++) {
            if(memory.get(i).getClass().getSimpleName().equals("Function")){
                Function b = (Function) memory.get(i);
                System.out.println(b.getBody().getType());
                System.out.println(b.getBody().getData().toString());
            }else{
                Body m = (Body) memory.get(i);
                System.out.println(m.getData().toString());
            }
        }

        Body w = (Body) memory.get(0);
        for (int i = 0; i < w.getData().size(); i++) {
            if(w.getData().get(i).getClass().getSimpleName().equals("Variable")){
                Variable c = (Variable) w.getData().get(i);
                System.out.println("i "+i+" - "+w.getData().get(i)+" - "+c.getName());
            }else if(w.getData().get(i).getClass().getSimpleName().equals("Body")){
                Body e = (Body) w.getData().get(i);
                System.out.println("i "+i+" - "+w.getData().get(i)+" - "+e.getName());
                System.out.println("Cuerpo en e - "+e.getData().size());
                for (int j = 0; j < e.getData().size(); j++) {
                    if(e.getData().get(j).getClass().getSimpleName().equals("Variable")){
                        Variable c = (Variable) e.getData().get(j);
                        System.out.println("j "+j+" - "+e.getData().get(j)+" - "+c.getName());
                    }else if(e.getData().get(j).getClass().getSimpleName().equals("Body")){
                        Body g = (Body) e.getData().get(j);
                        System.out.println("j "+j+" - "+e.getData().get(j)+" - "+g.getName());
                        System.out.println("Cuerpo en g- "+g.getData().size());
                    }
                }


            }
        }

        return true;

    }

    public boolean setMemory(Body bodyV){
        while(positionSubBody>=0){
            positionSubBody--;
            if(!analizeProcess(bodyV)){
                return false;
            }
            if(!existBody()){
                System.out.println("AQUI TERMINA "+bodyV.getName()+" - "+positionSubBody);
                break;
            }
        }
        return true;
    }

    private boolean analizeProcess(Body bodyV) {
        notTerminal = tempBody.get(positionSubBody).getNotTerminal();
        terminal = tempBody.get(positionSubBody).getTerminal();
        if(!setDV(bodyV)){
            return false;
        }
        if(!setDIForDW(bodyV)){
            return false;
        }
        if(!setDE(bodyV)){
            return false;
        }
        if(!setDS(bodyV)){
            return false;
        }
        if(!setDF(bodyV)){
            return false;
        }
        if(!setDSC(bodyV)){
            return false;
        }
        return true;
    }

    public void setMain(){
        if(notTerminal.equals("<DM>")) {
            isDM=true;
            body.setName("<DM>");
        }
        if(isDM  && notTerminal.equals("<CUR>")){
            body.setExistBody(existBody());
            memory.add(body);
            isDM=false;
        }
    }

    public void setFunction(){
        if(notTerminal.equals("<DFUN>")) isDFUN=1;
        if(isDFUN==1){
            setNameVariable();
            if(notTerminal.equals("<P>")){
                isDFUN = 2;
                function = new Function(variable, existParameter());
                function.setBody(new Body("<DFUN>"));
                variable="";
            }
        }
        if(isDFUN==2){
            if(function.isExistParameter()){
                setNameVariable();
                if(notTerminal.equals("<RP>")){
                    var = new Variable(variable);
                    var.setType("<PARAMETER>");
                    var.setStatus("<DV>");
                    function.getBody().getData().add(var);
                    parameters.add(var);
                    variable="";
                }
            }
            if((notTerminal.equals("<RP>") || notTerminal.equals("<P>")) && terminal.equals(")")){
                function.setQuantityV(parameters.size());
                if(function.isExistParameter()){
                    function.setVariables(parameters.toArray(new Variable[parameters.size()]));
                    parameters.clear();
                }
                isDFUN=3;
            }
        }
        if(isDFUN==3 && notTerminal.equals("<CUR>")){
             function.getBody().setExistBody(existBody());
             isDFUN=4;
        }
        if(isDFUN==4 && notTerminal.equals("<RRET>")){
            function.getBody().setExistReturn(existReturn());
            memory.add(function);
            isDFUN=0;
        }
    }


    public boolean setDV(Body bodyV){
        String type = null;
        //System.out.println(bodyV.getType());
        if(notTerminal.equals("<DV>")) isDV=true;
        if(isDV){
            setNameVariable();
            if(notTerminal.equals("<RDV>")){
                if(terminal.equals("=")){
                    isRDV = 1;
                }else if(terminal.equals("++") || terminal.equals("--")){
                    isRDV = 2;
                }else if(terminal.equals("(")){
                    isRDV = 3;
                }
                isDV = false;
            }
        }
        switch(isRDV){
            case 1:
                if(notTerminal.equals("<S>")){
                    if(isMatch(terminal, "^[a-zA-Z]$")) {
                        var = new Variable(variable);
                        if(!existVariable(bodyV, var)){
                            return false;
                        }
                        isVar = true;
                        variable = "";
                    }else{
                        var = new Variable(variable);
                        if(isMatch(terminal, "\"")) type = "STRING";
                        if(isMatch(terminal, "true|false")) type = "BOOLEAN";
                        if(isMatch(terminal, "^[0-9]$")) type = "INT";
                        var.setType(type);
                        if(!existVariable(bodyV, var)){
                            return false;
                        }
                        isRDV = 0;
                        variable = "";
                    }
                }
                if(isVar){
                    setNameVariable();
                    if(notTerminal.equals("<LLF>")){
                        Variable value = new Variable(variable);
                        if(terminal.equals("\n")){
                            if(!callVariable(value, bodyV)){
                                return false;
                            }
                            isRDV = 0;
                            variable = "";
                            isVar=false;
                        }else if(terminal.equals("(")){
                            var.setValue(value.getName());
                            bodyV.getData().set(bodyV.getData().size()-1,var);
                            isRDV=3;
                            isVar=false;
                        }
                    }
                }
                break;
            case 2:
                var = new Variable(variable);
                var.setType("INT");
                var.setStatus("<CONT>");
                if(!existVariable(bodyV, var)){
                    return false;
                }
                isRDV = 0;
                variable = "";
                break;
            case 3:
                if(!setCallFunctions(bodyV)){
                    return false;
                }
                break;
        }
        return true;
    }


    public boolean setDIForDW(Body bodyV){
        if(notTerminal.equals("<DIF>") || notTerminal.equals("<DW>")) {
            structure = notTerminal;
            isDIForDW=0;
            isStructure = 0;
        }
        if(isDIForDW==0){
            if(isStructure==0 || isStructure==1){
                if(!typeSentence(bodyV)){
                    return false;
                }
                if(notTerminal.equals("<OR>")){ operator = terminal; }
                if(notTerminal.equals("<CUR>")){
                    if(!compareExpression()){
                        return false;
                    }
                    if(existBody()){
                        Body temp = new Body(structure);
                        temp.setExistBody(existBody());
                        passData(bodyV, temp);
                        isDIForDW=-1;
                        if(!setMemory(temp)){
                            return false;
                        }
                        bodyV.getData().add(temp);
                    }
                    isDIForDW=3;
                }
            }
        }

        if(notTerminal.equals("<RE>")){
            if(terminal.equals("{")){
                isDIForDW=2;
            }
        }
        if (isDIForDW==2){
            if(existBody()){
                Body temp = new Body("<RE>");
                temp.setExistBody(existBody());
                passData(bodyV, temp);
                isDIForDW=-1;
                if(!setMemory(temp)){
                    return false;
                }
                bodyV.getData().add(temp);
            }
        }
        if(isDIForDW==3){
            if(analyzeReturn){
                if(existReturn()){
                    isDIForDW = 4;
                }else{
                    isDIForDW = -1;
                }
            }else{
                isDIForDW = -1;
            }
        }
        if (isDIForDW==4) {
            if(!analizeReturn(bodyV)){
                return false;
            }
        }
        return true;
    }


    public boolean typeSentence(Body bodyV){
        if(notTerminal.equals("<S>")){
            if(isMatch(terminal, "^[a-zA-Z]$")) {
                isSentence = true;
            }else{
                if(isMatch(terminal, "\"")) types[isStructure] = "STRING";
                if(isMatch(terminal, "true|false")) types[isStructure] = "BOOLEAN";
                if(isMatch(terminal, "^[0-9]$")) types[isStructure] = "INT";
                isStructure=1;
            }
        }
        if(isSentence){
            setNameVariable();
            if(notTerminal.equals("<LLF>") && terminal.equals("(")){
                isCallFunction = true;
            }
            if(notTerminal.equals("<OR>") || notTerminal.equals("<CUR>") || terminal.equals(";")){
                var = new Variable(variable);
                variable="";
                var.setStatus("<VCALL>");
                if(!existVariable(bodyV, var)){
                    return false;
                }
                types[isStructure] = var.getType();
                isStructure=1;
                isSentence = false;
            }
            if(isCallFunction){
                if(!setCallFunctions(bodyV)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean compareExpression(){
        if(!types[0].equals(types[1])){
            System.out.println("[ERROR]: TIPOS INCOMPATIBLES -> "+types[0]+" "+operator+" "+types[1]);
            errorMessage = "[ERROR]: TIPOS INCOMPATIBLES -> "+types[0]+" "+operator+" "+types[1];
            return false;
        }else{
            if(types[0].equals("BOOLEAN") || types.equals("STRING")){
                if(!isMatch(operator, "^(==|!=)$")){
                    System.out.println("[ERROR]: TIPO DE OPERADOR '"+operator+"' ES INVALIDO PARA -> "+types[0]);
                    errorMessage = "[ERROR]: TIPO DE OPERADOR '"+operator+"' ES INVALIDO PARA -> "+types[0];
                    return false;
                }
            }
        }
        return true;
    }

    public boolean setDF(Body bodyV){
        if(notTerminal.equals("<DF>")){
            isStructure=0;
            isDF=0;
        }
        if(isDF==0){
            setNameVariable();
            if(notTerminal.equals("<RV>") && terminal.equals("=")){
                var = new Variable(variable);
                variable="";
                isDF=1;
            }
        }
        if(isDF==1){
            if(notTerminal.equals("<S>")){
                if(isMatch(terminal, "^[a-zA-Z]$")) {
                    isDF = 2;
                }else{
                    if(isMatch(terminal, "\"")) type = "STRING";
                    if(isMatch(terminal, "true|false")) type = "BOOLEAN";
                    if(isMatch(terminal, "^[0-9]$")) type = "INT";
                    var.setType(type);
                    if(!existVariable(bodyV, var)){
                        return false;
                    }
                    isDF=3;
                }
            }
        }
        if(isDF==2){
            setNameVariable();
            if(notTerminal.equals("<LLF>")){
                Variable value = new Variable(variable);
                if(notTerminal.equals(";")){
                    if(!callVariable(value, bodyV)){
                        return false;
                    }
                    isDF = 3;
                    variable = "";
                }else if(terminal.equals("(")){
                    var.setValue(value.getName());
                    bodyV.getData().set(bodyV.getData().size()-1,var);
                    //variable = "";
                    isDF=4;
                }
            }
        }
        if(isDF==3){
            if(notTerminal.equals("<EX>")){
                analizeExpression = true;
            }
            if(analizeExpression && (isStructure==0 || isStructure==1)){
                if(!typeSentence(bodyV)){
                    return false;
                }
                if(notTerminal.equals("<OR>")){ operator = terminal; }
                if(terminal.equals(";")){
                    if(!compareExpression()){
                        return false;
                    }
                    isDF=5;
                }
            }
        }
        if(isDF==4){
            if(!setCallFunctions(bodyV)){
                return false;
            }
            if((notTerminal.equals("<RP>") || notTerminal.equals("<P>")) && terminal.equals(")")){
                isDF=3;
            }
        }
        if(isDF==5){
            setNameVariable();
            if(notTerminal.equals("<CONT>")){
                var = new Variable(variable);
                var.setType("INT");
                var.setStatus("<CONT>");
                if(!existVariable(bodyV, var)){
                    return false;
                }
                variable = "";
            }
            if(notTerminal.equals("<CUR>")){
                if(existBody()){
                    Body temp = new Body("<DF>");
                    temp.setExistBody(existBody());
                    passData(bodyV, temp);
                    isDF=-1;
                    if(!setMemory(temp)){
                        return false;
                    }
                    bodyV.getData().add(temp);
                }
                isDF=6;
            }
        }
        if(isDF==6){
            if(analyzeReturn){
                if(existReturn()){
                    isDF = 7;
                }else{
                    isDF = -1;
                }
            }else{
                isDF = -1;
            }
        }
        if (isDF==7) {
            if(!analizeReturn(bodyV)){
               return false;
            }
        }
        return true;
    }

    public boolean setDE(Body bodyV){
        if(notTerminal.equals("<DE>")) isDE=true;
        if(isDE){
            setNameVariable();
            if(notTerminal.equals("<RV>") && terminal.equals(")")){
                var = new Variable(variable);
                variable="";
                var.setStatus("<VCALL>");
                if(!existVariable(bodyV, var)){
                    return false;
                }
                isDE=false;
            }
        }
        return true;
    }

    public boolean setDS(Body bodyV){
        if(notTerminal.equals("<DS>")) isDS=true;
        if(isDS){
            setNameVariable();
            if(notTerminal.equals("<LLF>") && terminal.equals("(")){
                isCallFunction = true;
            }
            if(isCallFunction){
                if(!setCallFunctions(bodyV)){
                    return false;
                }
            }
            if(notTerminal.equals("<RC>")){
                if(terminal.equals(",")){
                    var = new Variable(variable);
                    variable="";
                    var.setStatus("<VCALL>");
                    if(!existVariable(bodyV, var)){
                        return false;
                    }
                }else if(terminal.equals(")")){
                    var = new Variable(variable);
                    variable="";
                    var.setStatus("<VCALL>");
                    if(!existVariable(bodyV, var)){
                        return false;
                    }
                    isDS = false;
                }

            }
        }
        return true;
    }


    private boolean setDSC(Body bodyV){
        if(notTerminal.equals("<DSC>")){
            isDSC = 0;
        }
        if(isDSC==0){
            setNameVariable();
            if(terminal.equals("{")){
                var = new Variable(variable);
                variable="";
                var.setStatus("<VCALL>");
                if(!existVariable(bodyV, var)){
                    return false;
                }
                dataTypeDSC = var.getType();
                isDSC=1;
            }
        }
        if(isDSC==1){
            if(notTerminal.equals("<TC>")){
                String dataType = "";
                if(isMatch(terminal, "\"")) dataType = "STRING";
                if(isMatch(terminal, "\'")) dataType = "STRING";
                if(isMatch(terminal, "^[0-9]$")) dataType  = "INT";
                if(!dataType.equals(dataTypeDSC)){
                    System.out.println("[ERROR]: TIPOS INCOMPATIBLES \n"+var.getName() +" -> "+dataTypeDSC+" != "+dataType);
                    errorMessage = "[ERROR]: TIPOS INCOMPATIBLES \n"+var.getName() +" -> "+dataTypeDSC+" != "+dataType;
                    return false;
                }
                isDSC=2;
            }
        }
        if(isDSC == 2){
            if(notTerminal.equals("<CUR>")){
                if(existBody()){
                    Body temp = new Body("<DSC><TC>");
                    temp.setExistBody(existBody());
                    passData(bodyV, temp);
                    isDSC=-1;
                    if(!setMemory(temp)){
                        return false;
                    }
                    bodyV.getData().add(temp);
                }
                isDSC=3;
            }
        }
        if(isDSC==3){
            if(notTerminal.equals("<CT>")){ isDSC=1; }
            if(notTerminal.equals("<ROPC>")){ isDSC=4; }
            if(analyzeReturn){
                if(existReturn()){
                    isDSC = 5;
                }
            }
        }
        if(isDSC==4){
            if(notTerminal.equals("<CUR>")){
                if(existBody()){
                    Body temp = new Body("<DSC><ROPC>");
                    temp.setExistBody(existBody());
                    passData(bodyV, temp);
                    isDSC=-1;
                    if(!setMemory(temp)){
                        return false;
                    }
                    bodyV.getData().add(temp);
                }
                isDSC = 3;
            }

        }
        if(isDSC==5){
            if(!analizeReturn(bodyV)){
                return false;
            }
        }
        return true;
    }

    public boolean setCallFunctions(Body bodyV){
        if(notTerminal.equals("<P>")){
            function = new Function(variable, existParameter());
            variable="";
        }
        if(function.isExistParameter()){
            setNameVariable();
            if(notTerminal.equals("<RP>")){
                var = new Variable(variable);
                var.setStatus("<VCALL>");
                if(!existVariable(bodyV, var)){
                    return false;
                }
                parameters.add(var);
                variable="";
            }
        }
        if((notTerminal.equals("<RP>") || notTerminal.equals("<P>")) && terminal.equals(")")){
            function.setQuantityV(parameters.size());
            if(function.isExistParameter()){
                function.setVariables(parameters.toArray(new Variable[parameters.size()]));
                parameters.clear();
            }
            callFunctions.add(function);
            isCallFunction=false;
            isRDV=-1;
        }
        return true;
    }

    public boolean extractReturn(){
        ArrayList<T_NotT> bodyMemory;
        String type = "";
        for (int i = 0; i < memory.size(); i++) {
            if(memory.get(i).getClass().getSimpleName().equals("Function")){
                Function funzione = (Function) memory.get(i);
                if(funzione.getBody().isExistReturn()){
                    bodyMemory = bodys.get(i);
                    for (int j = 0; j < bodyMemory.size(); j++) {
                        notTerminal = bodyMemory.get(j).getNotTerminal();
                        terminal = bodyMemory.get(j).getTerminal();
                        if(notTerminal.equals("<RRET>") && terminal.equals("}")){
                            //ERROR
                            //System.out.println("ERROR");
                            errorMessage = "[ERROR] FALTA DE DECLARACIÓN DE RETORNO EN EL MÉTODO -> "+funzione.getName();
                            return false;
                        }else{
                            if(notTerminal.equals("<S>")){
                                if(isMatch(terminal, "\"")) type = "STRING";
                                if(isMatch(terminal, "true|false")) type = "BOOLEAN";
                                if(isMatch(terminal, "^[0-9]$")) type = "INT";
                                if(isMatch(terminal, "^[a-zA-Z]$")) type = "VARIABLE";
                                funzione.getBody().setType(type);
                                break;
                            }else if(terminal.equals(")")){
                                type = "FUNCTION";
                                funzione.getBody().setType(type);
                                break;
                            }

                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean verifyFunctions(){
        int bandera = 0;
        int cont=0;
        for (int i = 0; i < memory.size(); i++) {
            if(memory.get(i).getClass().getSimpleName().equals("Function")){
                Function funzione = (Function) memory.get(i);



                for (int j = 0; j < memory.size(); j++) {
                    if(memory.get(j).getClass().getSimpleName().equals("Function")){
                        Function temp = (Function) memory.get(j);
                        if(funzione.getName().equals(temp.getName())){
                            if(funzione.getQuantityV()==temp.getQuantityV()){

                                    /*if(!(funzione.getQuantityV()==0)){
                                        for(int k=0;k<funzione.getVariables().length;i++){
                                            if(funzione.getVariables()[k].getType().equals(temp.getVariables()[k].getType())){
                                                cont++;
                                            }
                                        }
                                    }
                                    if(cont==funzione.getQuantityV()){
                                        bandera++;
                                    }*/
                                bandera++;
                            }
                        }
                        if(bandera==2){
                            errorMessage = "[ERROR] METODO YA DEFINIDO EN CUERPO ARCHIVIO -> "+funzione.getName();
                            return true;
                        }
                    }
                }
                bandera = 0;
            }
        }
        return false;
    }

    private boolean callVariable(Variable value, Body bodyV) {
        value.setStatus("<VCALL>");
        if(!existVariable(bodyV, value)){
            return false;
        }
        if(var.getStatus().equals("<V>")){
            if(!var.getType().equals(value.getType())){
                errorMessage = "[ERROR]: TIPOS INCOMPATIBLES \n"+var.getName()+" = "+value.getName();
                System.out.println("[ERROR]: TIPOS INCOMPATIBLES \n"+var.getName()+" = "+value.getName());
                return false;
            }
        }else if(var.getStatus().equals("<DV>")){
            var.setType(value.getType());
            bodyV.getData().set(bodyV.getData().size()-1,var);
        }
        return true;
    }

    private int isDR = -1;
    private String typeReturn = "";
    private boolean analizeReturn(Body bodyV){
        if(notTerminal.equals("<DR>")){ isDR = 1; }
        if(isDR==1){
            if(notTerminal.equals("<S>")){
                if(isMatch(terminal, "^[a-zA-Z]$")) {
                    isDR = 2;
                }else{
                    if(isMatch(terminal, "\"")) typeReturn = "STRING";
                    if(isMatch(terminal, "true|false")) typeReturn = "BOOLEAN";
                    if(isMatch(terminal, "^[0-9]$")) typeReturn = "INT";
                    isDR=3;
                }
            }
        }
        if(isDR==2){
          setNameVariable();
          if(notTerminal.equals("<CUR>")){
              var = new Variable(variable);
              variable = "";
              var.setStatus("<VCALL>");
              isDIForDW = -1;
              isDSC = -1;
              isDF = -1;
            if(!existVariable(bodyV,var)){
               return false;
            }
            typeReturn = var.getType();
            isDR=3;
          }
        }
        if(isDR==3){
            if(!typeReturn.equals(bodyV.getType())){
                errorMessage = "[ERROR] TIPO DE RETORNO INCOMPATIBLE "+bodyV.getType()+" != "+typeReturn;
                return false;
            }
            isDR=-1;
        }
        return true;
    }

    public boolean existBody(){
        if(notTerminal.equals("<CUR>") && (terminal.equals("}") || terminal.equals("return"))){
            return false;
        }
        return true;
    }

    public boolean existParameter(){
        if(isMatch(terminal,"^[a-zA-Z]$")){
            return true;
        }
        return false;
    }

    public boolean existReturn(){
        if(notTerminal.equals("<RRET>") && terminal.equals("}") ){
            return false;
        }
        return true;
    }

    public boolean existVariable(Body bodyV, Variable variable){
        for (int i = 0; i < bodyV.getData().size(); i++) {
            if(bodyV.getData().get(i).getClass().getSimpleName().equals("Variable")){
                Variable temp = (Variable) bodyV.getData().get(i);
                if (variable.getName().equals(temp.getName())) {
                    if(variable.getType().equals(temp.getType())){
                        variable.setStatus("<V>");
                        variable.setType(temp.getType());
                    }else if(variable.getStatus().equals("<VCALL>")){
                        variable.setStatus("<V>");
                        variable.setType(temp.getType());
                    }else if(temp.getType().equals("<PARAMETER>")){
                        temp.setType(variable.getType());
                        variable.setStatus("<V>");
                        System.out.println(temp.getName()+" - "+temp.getType());
                    }else{
                        //System.out.println(var.getName()+" - "+temp.getName());
                        System.out.println("[ERROR]: TIPOS INCOMPATIBLES \n"+variable.getType()+" != "+temp.getType());
                        errorMessage = "[ERROR]: TIPOS INCOMPATIBLES \n"+variable.getType()+" != "+temp.getType();
                        return false;
                    }
                }
            }
        }
        if(variable.getStatus().equals("<VCALL>") || variable.getStatus().equals("<CONT>")){
            System.out.println("ERROR: NO SE ENCUENTRA LA VARIABLE: "+variable.getName());
            errorMessage = "[ERROR]: NO SE ENCUENTRA LA VARIABLE: "+variable.getName();
            return false;
        }else if(!variable.getStatus().equals("<V>")){
            variable.setStatus("<DV>");
            bodyV.getData().add(variable);
        }
        return true;
    }

    public boolean isMatch(String input, String regex){
        Pattern pattern;
        Matcher match;
        pattern = Pattern.compile(regex);
        match = pattern.matcher(input);
        return match.find();
    }

    public void setNameVariable(){
        if(notTerminal.equals("<L>") || notTerminal.equals("<D>")|| ((notTerminal.equals("<V>") || notTerminal.equals("<RV>")) && terminal.equals("_")) ) {
            variable += terminal;
        }
    }

    public void clearAll(){
        memory.clear();
        bodys.clear();
        tempBody.clear();
        parameters.clear();
        callFunctions.clear();
        types = new String[2];
        variable = "";
        body = new Body();
        function = new Function();
        var = new Variable();
        isStructure = -1;
        typeReturn = "";

    }

    public void passData(Body bodyV, Body temp){
        temp.setType(bodyV.getType());
        for (int i = 0; i < bodyV.getData().size(); i++) {
            if(bodyV.getData().get(i).getClass().getSimpleName().equals("Variable")){
                Variable tempVar = (Variable) bodyV.getData().get(i);
                temp.getData().add(tempVar);
            }
        }
    }










}

