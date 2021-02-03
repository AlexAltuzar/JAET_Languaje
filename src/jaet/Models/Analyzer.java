package jaet.Models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
    protected Pattern pattern;
    protected Matcher match;

    public Analyzer(String regex){
        this.pattern = Pattern.compile(regex);
        this.match = null;
    }
}
