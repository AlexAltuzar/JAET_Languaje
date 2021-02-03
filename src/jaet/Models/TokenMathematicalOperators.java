package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenMathematicalOperators extends Analyzer implements Validate {
    public final static String mathematicalOperator = "^(\\+|\\-|\\*|/|\\^|\\+\\+|\\-\\-)$";

    public TokenMathematicalOperators() { super(mathematicalOperator); }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }
}
