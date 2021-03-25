package jaet.Models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer extends Observable {
    private ArrayList<LexicalToken> lexicon;
    private ArrayList<String> syntactic;
    private Token token;
    private String[] code;
    private boolean existError;

    public LexicalAnalyzer(){
        this.lexicon = new ArrayList<>();
        this.syntactic = new ArrayList<>();
        this.code = null;
        this.token = new Token();
        this.existError = false;
    }

    public ArrayList<LexicalToken> analyze(String code){
        ArrayList<String> words = new ArrayList<>();
        char [] variable;
        existError = false;
        lexicon.clear();
        syntactic.clear();
        separateWords(code);
        for(int i = 0; i<this.code.length; i++){
            String word = this.code[i];
            if(isNumber(word)){
                String number = String.valueOf(word);
                char[] digits = number.toCharArray();
                for(int j = 0; j < digits.length; j++) {
                    if (token.isNumber(String.valueOf(digits[j]))){
                        syntactic.add(String.valueOf(digits[j]));
                    }
                }
                lexicon.add(new LexicalToken(word, "Numeral"));
            }
            else if (word.equals(" "));
            else if (word.equals(""));
            else if (token.isGroupSymbols(word)) {
                lexicon.add(new LexicalToken(word, "Símbolo de agrupación"));
                syntactic.add(word);
            }
            else if (token.isReservedWords(word)) {
                lexicon.add(new LexicalToken(word, "Palabra reservada"));
                syntactic.add(word);
            }
            /*else if (token.isLetter(word)) {

            }*/
            else if (token.isMathematicalOperator(word)) {
                lexicon.add(new LexicalToken(word, "Operador matemático"));
                syntactic.add(word);
            }
            else if (token.isRelationalOperator(word)) {
                lexicon.add(new LexicalToken(word, "Operador relacional"));
                syntactic.add(word);
            }
            else if (token.isSymbols(word)) {
                lexicon.add(new LexicalToken(word, "Símbolo"));
                syntactic.add(word);
            }
            else if (token.isSpace(word)){
                syntactic.add(word);
            }
                //lexicon.add(new LexicalToken(word,"Salto de línea"));
           /* else if (token.isAlphanumerics(word)){
                words.add(word);

            }*/

            else {
                words.clear();
                variable = word.toCharArray();
                String text = " ";
                boolean isVariable = true;
                for (int j = 0; j < variable.length; j++) {
                    words.add(String.valueOf(variable[j]));
                    syntactic.add(String.valueOf(variable[j]));
                    /*if(token.isLetter(String.valueOf(variable[j]))){
                        words.add(String.valueOf(variable[j]));
                        text += String.valueOf(variable[j]);
                    }else{
                        isVariable = false;
                        break;
                    }*/
                }
                /*if(!text.equals(" ") && isVariable==true){
                    lexicon.add(new LexicalToken(word, "Variable"));
                    for(int j=0;j<words.size();j++){
                        //lexicon.add(new LexicalToken(words.get(j),"Letra"));
                        syntactic.add(words.get(j));
                    }
                    //System.out.println(words);
                }else{
                    lexicon.add(new LexicalToken(word,"Declaración inválida"));
                    existError = true;
                }*/
                /*
                setChanged();
                notifyObservers("Error léxico. símbolo: \" ' " + word.trim() + " ' no reconocido \" ");*/
            }


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

    public ArrayList<String> getSyntactic() {
        return syntactic;
    }

    public boolean isNumber(String number){
        try {
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /*
    public boolean existError(){
        return existError;
    }*/

}
