package jaet.Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer extends Observable {
    private ArrayList<LexicalToken> lexicon;

    /*private TokenMathematicalOperators mathematicalOperators;
    private TokenRelationalOperators relationalOperators;
    private TokenReservedWords reservedWords;
    private TokenSymbols symbols;
    private TokenGroupSymbols groupSymbols;
    private TokenVariable variable;
    private TokenNumber number;
    private TokenSpace space;*/
    private Token token;

    private String[] code;
    private boolean existError;

    public LexicalAnalyzer(){
        this.lexicon = new ArrayList<>();
        this.code = null;
        this.token = new Token();
        this.existError = false;
    }

    public ArrayList<LexicalToken> analyze(String code){
        ArrayList<String> words = new ArrayList<>();
        char [] variable;
        existError = false;
        lexicon.clear();
        words.clear();
        separateWords(code);

        for(int i = 0; i<this.code.length; i++){
            String word = this.code[i];
            if (token.isNumber(word)){
                lexicon.add(new LexicalToken(word, "Numeral"));
                //words.add(word);
            }
            else if (word.equals(" "));
            else if (word.equals(""));
            else if (token.isGroupSymbols(word)) {
                lexicon.add(new LexicalToken(word, "Símbolo de agrupación"));
                //words.add(word);
            }
            else if (token.isReservedWords(word)) {
                lexicon.add(new LexicalToken(word, "Palabra reservada"));
                //words.add(word);
            }
            /*else if (token.isLetter(word)) {

            }*/
            else if (token.isMathematicalOperator(word)) {
                lexicon.add(new LexicalToken(word, "Operador matemático"));
                //words.add(word);
            }
            else if (token.isRelationalOperator(word)) {
                lexicon.add(new LexicalToken(word, "Operador relacional"));
                //words.add(word);
            }
            else if (token.isSymbols(word)) {
                lexicon.add(new LexicalToken(word, "Símbolo"));
                //words.add(word);
            }
            else if (token.isSpace(word)){}
                //lexicon.add(new LexicalToken(word,"Salto de línea"));

           /* else if (token.isAlphanumerics(word)){
                words.add(word);

            }*/

            else {
                variable = word.toCharArray();
                String text = " ";
                boolean isVariable = true;
                for (int j = 0; j < variable.length; j++) {
                    if(token.isLetter(String.valueOf(variable[j]))){
                        words.add(String.valueOf(variable[j]));
                        text += String.valueOf(variable[j]);
                    }else{
                        isVariable = false;
                        break;
                    }
                }
                if(!text.equals(" ") && isVariable==true){
                    //lexicon.add(new LexicalToken(word, "Variable"));
                    for(int j=0;j<words.size();j++){
                        lexicon.add(new LexicalToken(words.get(j),"Letra"));
                    }
                    //System.out.println(words);
                }else{
                    lexicon.add(new LexicalToken(word,"Declaración inválida"));
                    existError = true;
                }
                /*
                setChanged();
                notifyObservers("Error léxico. símbolo: \" ' " + word.trim() + " ' no reconocido \" ");*/
            }


        }
        for(int z=0;z< lexicon.size();z++){
            System.out.println(lexicon.get(z));
        }
        return lexicon;
    }

    private void separateWords(String code){
        String espacio = "\\?espacio\\?%";
        code = code+"\n";
        Pattern p;
        Matcher m;
        p=Pattern.compile("==");
        m=p.matcher(code);
        code = m.replaceAll("f5df58oBleIguaLlkwemf");
        p=Pattern.compile("<=");
        m=p.matcher(code);
        code = m.replaceAll("lkwrmfMenorIgualkfL");
        p=Pattern.compile(">=");
        m=p.matcher(code);
        code = m.replaceAll("lkwmfmayorIguaLlwkmf");
        p=Pattern.compile("!=");
        m=p.matcher(code);
        code = m.replaceAll("wbksmd8iferentEdnfusn");
        p=Pattern.compile(":=");
        m=p.matcher(code);
        code = m.replaceAll("qwertyinicioExpresionasdfg");
        p=Pattern.compile("\\+\\+");
        m=p.matcher(code);
        code = m.replaceAll("?incrementar?%");
        p=Pattern.compile("--");
        m=p.matcher(code);
        code = m.replaceAll("?disminuir?%");

        /*
        ArrayList<String> notTerminals = new ArrayList<>();

        notTerminals.add("<DC>");
        notTerminals.add("<CA>");
        notTerminals.add("<DM>");
        notTerminals.add("<DCM>");
        notTerminals.add("<RCM>");
        notTerminals.add("<DV>");
        notTerminals.add("<RDV>");
        notTerminals.add("<DW>");
        notTerminals.add("<DF>");
        notTerminals.add("<EF>");
        notTerminals.add("<DE>");
        notTerminals.add("<DS>");
        notTerminals.add("<CONC>");
        notTerminals.add("<RC>");
        notTerminals.add("<DIF>");
        notTerminals.add("<RIF>");
        notTerminals.add("<RE>");
        notTerminals.add("<DFUN>");
        notTerminals.add("<P>");
        notTerminals.add("<RP>");
        notTerminals.add("<DSC>");
        notTerminals.add("<OP>");
        notTerminals.add("<ROPC>");
        notTerminals.add("<ED>");
        notTerminals.add("<TC>");
        notTerminals.add("<RTC>");
        notTerminals.add("<CT>");
        notTerminals.add("<DR>");
        notTerminals.add("<R>");
        notTerminals.add("<RR>");
        notTerminals.add("<V>");
        notTerminals.add("<LLF>");
        notTerminals.add("<C>");
        notTerminals.add("<CUR>");
        notTerminals.add("<RV>");
        notTerminals.add("<EX>");
        notTerminals.add("<S>");
        notTerminals.add("<OPR>");
        notTerminals.add("<RO>");
        notTerminals.add("<N>");
        notTerminals.add("<CF>");
        notTerminals.add("<RRET>");
        notTerminals.add("<OR>");
        notTerminals.add("<B>");
        notTerminals.add("<O>");
        notTerminals.add("<D>");
        notTerminals.add("<L>");
        notTerminals.add("<CONT>");
        for (int i = 0; i < notTerminals.size(); i++){
            p = Pattern.compile(notTerminals.get(i));
            m = p.matcher(code);
            code = m.replaceAll(espacio+notTerminals.get(i)+espacio);
        }
        this.code = code.split("(\t|"+espacio+")+");
*/
        ArrayList<String> signos = new ArrayList<>();

        signos.add("\\+");
        signos.add("-");
        signos.add("\\*");
        signos.add("/");
        signos.add("\\^");
        signos.add("#");
        signos.add(",");
        signos.add(";");
        signos.add("<");
        signos.add(">");
        signos.add("=");
        signos.add("\"");
        signos.add("\\(");
        signos.add("\\)");
        signos.add("\\{");
        signos.add("\\}");
        signos.add("\'");
        signos.add("\n");
        signos.add(" ");

        for (int i = 0; i < signos.size(); i++){
            p = Pattern.compile(signos.get(i));
            m = p.matcher(code);
            code = m.replaceAll(espacio+signos.get(i)+espacio);
        }
        p=Pattern.compile("f5df58oBleIguaLlkwemf");
        m=p.matcher(code);
        code = m.replaceAll(espacio+"=="+espacio);
        p=Pattern.compile("lkwrmfMenorIgualkfL");
        m=p.matcher(code);
        code = m.replaceAll(espacio+"<="+espacio);
        p=Pattern.compile("lkwmfmayorIguaLlwkmf");
        m=p.matcher(code);
        code = m.replaceAll(espacio+">="+espacio);
        p=Pattern.compile("wbksmd8iferentEdnfusn");
        m=p.matcher(code);
        code = m.replaceAll(espacio+"!="+espacio);

        p=Pattern.compile("qwertyinicioExpresionasdfg");
        m=p.matcher(code);
        code = m.replaceAll(espacio+":="+espacio);

        p=Pattern.compile("\\?incrementar\\?%");
        m=p.matcher(code);
        code = m.replaceAll(espacio+"++"+espacio);
        p=Pattern.compile("\\?disminuir\\?%");
        m=p.matcher(code);
        code = m.replaceAll(espacio+"--"+espacio);

        code = code.trim();

        this.code = code.split("(\t|"+espacio+")+");

    }

    public boolean existError(){
        return existError;
    }

}
