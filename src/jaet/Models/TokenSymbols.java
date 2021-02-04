package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenSymbols extends Analyzer implements Validate {

    public final static String symbols = "^(,|#|;|:=)$";

    public TokenSymbols(){
        super(symbols);
    }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }
}
