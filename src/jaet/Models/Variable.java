package jaet.Models;

public class Variable {
    private String name;
    private String type = "NONE";
    private String status = "NONE";
    private String value = "NONE";

    public Variable(){}

    public Variable(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }
}
