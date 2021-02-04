package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenVariable extends Analyzer implements Validate {
    public final static String variable = "^([A-Za-z]+(\\d)*)+$|^((_)([A-Za-z]|(\\d))+){1,9}$|^([A-Za-z]+([A-Za-z]|(\\d)|(_))*)$";

    public TokenVariable(){
        super(variable);
    }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }
}
