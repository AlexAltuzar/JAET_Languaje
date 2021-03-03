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
       /* this.number = new TokenNumber();
        this.groupSymbols = new TokenGroupSymbols();
        this.variable = new TokenVariable();
        this.mathematicalOperators = new TokenMathematicalOperators();
        this.reservedWords = new TokenReservedWords();
        this.relationalOperators = new TokenRelationalOperators();
        this.symbols = new TokenSymbols();
        this.space = new TokenSpace();*/
        this.existError = false;
    }

    public ArrayList<LexicalToken> analyze(String code){
        ArrayList<String> words = new ArrayList<>();
        char [] variable;
        existError = false;
        lexicon.clear();
        separateWords(code);

        for(int i = 0; i<this.code.length; i++){
            String word = this.code[i];
            if (token.isNumber(word)){
                lexicon.add(new LexicalToken(word, "Numeral"));
                words.add(word);
            }
            else if (word.equals(" "));
            else if (word.equals("")); // Evita error al introducir un solo símbolo reconocido.

            /*else if (word.equals("\"")){
                int pos = cadenaIsValid(i);
                if(pos!=-1)
                    i = pos;
            }

            else if (word.equals("\'")){
                int pos = caracterIsValid(i);
                if(pos!=-1)
                    i = pos;
            }*/

            else if (token.isGroupSymbols(word)) {
                lexicon.add(new LexicalToken(word, "Símbolo de agrupación"));
                words.add(word);
            }
            else if (token.isReservedWords(word)) {
                lexicon.add(new LexicalToken(word, "Palabra reservada"));
                words.add(word);
            }
            //ANALIZAR
            else if (token.isLetter(word)) {

            }
            else if (token.isMathematicalOperator(word)) {
                lexicon.add(new LexicalToken(word, "Operador matemático"));
                words.add(word);
            }
            else if (token.isRelationalOperator(word)) {
                lexicon.add(new LexicalToken(word, "Operador relacional"));
                words.add(word);
            }
            else if (token.isSymbols(word)) {
                lexicon.add(new LexicalToken(word, "Símbolo"));
                words.add(word);
            }
            else if (token.isSpace(word)){}
                //lexicon.add(new LexicalToken(word,"Salto de línea"));

           /* else if (token.isAlphanumerics(word)){
                words.add(word);

            }*/

            else {
                variable = word.toCharArray();
                String text = " ";
                String string = " ";
                for (int j = 0; j < variable.length; j++) {
                    if(token.isLetter(String.valueOf(variable[j]))){
                        words.add(String.valueOf(variable[j]));
                        text += String.valueOf(variable[j]);
                    }else{

                    }

                    //Si hay algun caracter que no sea leido por variable, es un comentario y por lo tanto
                    //no es variable
                    /*if(token.isAlphanumerics(String.valueOf(variable[j]))){
                        string += String.valueOf(variable[j]);
                    }*/
                }

                /*if(!string.equals(" ")){
                    lexicon.add(new LexicalToken(word, "Comentario"));
                }*/

                if(!text.equals(" ")){
                    lexicon.add(new LexicalToken(word, "Variable"));
                }

                /*lexicon.add(new LexicalToken(word,"Declaración inválida"));
                existError = true;
                setChanged();
                notifyObservers("Error léxico. símbolo: \" ' " + word.trim() + " ' no reconocido \" ");*/
            }

        }
        System.out.println(words);
        return lexicon;
    }

    private int cadenaIsValid(int pos){
        String cadena = code[pos]; pos++;
        while (pos < this.code.length){
            if (code[pos].equals("\n"))
                cadena += "\\n";
            else
                cadena = cadena + code[pos];
            if (code[pos].equals("\"")){
                lexicon.add(new LexicalToken(cadena,"Cadena"));
                return pos;
            }
            pos++;
        }
        lexicon.add(new LexicalToken(cadena,"No es una declaración"));
        existError = true;
        setChanged();
        notifyObservers("Error sintaxis. text: \" '"+ " " + cadena.trim() +" ' se esperaba ' \"' \" ");
        return pos;
    }

    private int caracterIsValid(int pos){
        String cadena = code[pos]; pos++;
        while (pos < this.code.length){
            if (code[pos].equals("\n"))
                cadena += "\\n";
            else
                cadena = cadena + code[pos];
            if (code[pos].equals("\'")){
                lexicon.add(new LexicalToken(cadena,"Caracter"));
                return pos;
            }
            pos++;
        }
        lexicon.add(new LexicalToken(cadena,"No es una declaración"));
        existError = true;
        setChanged();
        notifyObservers("Error sintaxis. text: \" '"+ " " + cadena.trim() +" ' se esperaba ' \'' \' ");
        return pos;
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

    public boolean existError(){
        return existError;
    }

}
