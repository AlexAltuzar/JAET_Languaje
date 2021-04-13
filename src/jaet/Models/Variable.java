package jaet.Models;

public class Variable {
    private String name;
    private String value;
    private String type;

    public Variable(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
