package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenSpace extends Analyzer implements Validate {

    public final static String space = "^\n$";

    public TokenSpace(){
        super(space);
    }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }

}
