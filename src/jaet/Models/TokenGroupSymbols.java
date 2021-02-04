package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenGroupSymbols extends Analyzer implements Validate {

    public TokenGroupSymbols(){
        super("(" + "\"" + "|" + "\\)" + "|" + "\\(" + "|" + "\\{" + "|" + "\\}" + "|" + "\'" + ")");
    }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }
}
