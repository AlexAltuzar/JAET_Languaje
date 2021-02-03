package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenNumber extends Analyzer implements Validate {
    public final static String number = "^(([1-9]{1}[0-9]{0,9})|[0])$";

    public TokenNumber(){
        super(number);
    }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }
}
