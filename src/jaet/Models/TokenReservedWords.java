package jaet.Models;

import jaet.Models.Interfaces.Validate;

public class TokenReservedWords extends Analyzer implements Validate {

   public TokenReservedWords(){
       super("^(archivio|init|main|current_|funzione|stamp|scegliere|if|else|while|for|return|enterokay|true|false|break| default)\\z");
   }

    @Override
    public boolean isValid(String input) {
        match = pattern.matcher(input);
        return match.find();
    }
}
