package jaet.Models;

import java.util.ArrayList;

public class Body {

    private String name;
    private ArrayList data = new ArrayList();
    private String type = "NONE";
    private boolean existBody;
    private boolean existReturn;


    public Body(){}

    public Body(String name) {
        this.name = name;
    }

    public void setName(String name) { this.name = name; }

    public void setData(ArrayList data) {
        this.data = data;
    }

    public void setType(String type) {
        this.type= type;
    }

    public void setExistBody(boolean existBody) {
        this.existBody = existBody;
    }

    public void setExistReturn(boolean existReturn) {
        this.existReturn = existReturn;
    }

    public String getName() {
        return name;
    }

    public ArrayList getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public boolean isExistBody() {
        return existBody;
    }

    public boolean isExistReturn() {
        return existReturn;
    }
}
