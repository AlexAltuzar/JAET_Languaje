package jaet.Models;

public class T_NotT {

    private String notTerminal;
    private String terminal;

    public T_NotT(String notTerminal, String terminal) {
        this.notTerminal = notTerminal;
        this.terminal = terminal;
    }

    public void setNotTerminal(String notTerminal) {
        this.notTerminal = notTerminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getNotTerminal() {
        return notTerminal;
    }

    public String getTerminal() {
        return terminal;
    }
}
