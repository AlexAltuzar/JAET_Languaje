package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenRelationalOperators extends Analyzer implements Validate {

    public final static String relationalOperator = "^(<|>|<=|>=|=|==|!=)$";

    public TokenRelationalOperators() {
        super(relationalOperator);
    }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }
}
