package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenGroupSymbols extends Analyzer implements Validate {

    public final static String groupSymbols = "(" + ")" + "{"+"}"+ "\""  + "\'";

    public TokenGroupSymbols(){
        super(groupSymbols);
    }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }
}
