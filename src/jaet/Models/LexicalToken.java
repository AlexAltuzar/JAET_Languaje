package jaet.Models;

public class LexicalToken implements Comparable<LexicalToken> {

    private String word;
    private String description;

    public LexicalToken(String word, String description){
        this.word = word;
        this.description = description;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "LexicalDescription{" +
                "word='" + word + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(LexicalToken o) {
        if(o.getWord().equals(this.word))
            if (o.getDescription().equals(this.description))
                return 0;
        return -1;
    }
}