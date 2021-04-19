package jaet.Models;

import java.util.ArrayList;

public class Function {

    private String name;
    private int quantityV;
    private boolean existParameter;
    private Variable [] variables;
    private Body body;

    public Function(){}

    public Function(String name, boolean existParameter) {
        this.name = name;
        this.existParameter = existParameter;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setQuantityV(int quantityV) {
        this.quantityV = quantityV;
    }

    public void setExistParameter(boolean existParameter) {
        this.existParameter = existParameter;
    }

    public void setVariables(Variable[] variables) {
        this.variables = variables;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public int getQuantityV() {
        return quantityV;
    }

    public boolean isExistParameter() {
        return existParameter;
    }

    public Variable[] getVariables() {
        return variables;
    }

    public Body getBody() {
        return body;
    }

}
