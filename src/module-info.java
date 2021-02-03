module LexicalAnalyzer {

    requires javafx.controls;
    requires javafx.fxml;

    opens jaet.Views to javafx.fxml;
    opens jaet.Models to javafx.base;
    exports jaet;
}