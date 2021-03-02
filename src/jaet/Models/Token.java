package jaet.Models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {
    private String mathematicalOperator = "^(\\+|\\-|\\*|/|\\^|\\+\\+|\\-\\-)$";
    private String number = "^[0-9]$";
    private String groupSymbols = "^(" + "\"" + "|" + "\\)" + "|" + "\\(" + "|" + "\\{" + "|" + "\\}" + "|" + "\'" + ")$";
    private String relationalOperator = "^(<|>|<=|>=|=|==|!=)$";
    private String reservedWords = "^(archivio|init|main|current_|funzione|stamp|scegliere|if|else|while|for|return|enterokay|true|false|break|default)\\z";
    private String space = "^\n$";
    private String symbols = "^(,|#|;|:=)$";
    private String letter = "^[a-z]$";
    private String alphanumerics = "^[\\s|\\S]*$";
    private Pattern pattern;
    private Matcher match;


    public Token() {

    }

    public boolean isMathematicalOperator(String input){
        pattern = Pattern.compile(mathematicalOperator);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isNumber(String input){
        pattern = Pattern.compile(number);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isGroupSymbols(String input){
        pattern = Pattern.compile(groupSymbols);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isRelationalOperator(String input){
        pattern = Pattern.compile(relationalOperator);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isReservedWords(String input){
        pattern = Pattern.compile(reservedWords);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isSpace(String input){
        pattern = Pattern.compile(space);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isSymbols(String input){
        pattern = Pattern.compile(symbols);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isLetter(String input){
        pattern = Pattern.compile(letter);
        match = pattern.matcher(input);
        return match.find();
    }

    public boolean isAlphanumerics(String input){
        pattern = Pattern.compile(alphanumerics);
        match = pattern.matcher(input);
        return match.find();
    }




}
