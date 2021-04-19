package jaet.Views;


import jaet.Models.LexicalAnalyzer;
import jaet.Models.LexicalToken;
import jaet.Models.SemanticsAnalyzer;
import jaet.Models.SyntacticAnalyzer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class LexicalController {

    @FXML private TextArea terminal;
    @FXML private TableView<LexicalToken> tableLexicon;
    @FXML private TableColumn<LexicalToken, String> tableColumnLexicon;
    @FXML private TableColumn<LexicalToken, String> tableColumnToken;
    @FXML private TextArea txtF;
    @FXML private Button btnAnalyze;

    private LexicalAnalyzer analyzer = new LexicalAnalyzer();
    private ArrayList<LexicalToken> lexical = new ArrayList<>();
    private ArrayList<String> syntactical = new ArrayList<>();
    private SemanticsAnalyzer semanticsAnalyzer = new SemanticsAnalyzer();

    @FXML
    void CompileAnalyzer(MouseEvent event) {
        tableLexicon.getItems().clear();
        lexical = analyzer.analyze(terminal.getText().trim());
        syntactical = analyzer.getSyntactic();
        System.out.println(syntactical);
        SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(syntactical);
        if(syntacticAnalyzer.isValid() == true){
            semanticsAnalyzer.setProcess(syntacticAnalyzer.getProcess());
            if(semanticsAnalyzer.isValid()){
                txtF.setText("Sintaxis correcta\nProceso terminado con código de salida 0");
                txtF.setStyle("-fx-text-fill: green");
            }else{
                txtF.setText("Sintaxis Incorrecta\nProceso detenido\n"+semanticsAnalyzer.getErrorMessage());
                txtF.setStyle("-fx-text-fill: red");
            }
        }else{
            txtF.setText("Sintaxis Incorrecta\nProceso detenido\n"+syntacticAnalyzer.getError());
            txtF.setStyle("-fx-text-fill: red");
        }
        showLexical();



    }

    private void showLexical(){
        tableColumnLexicon.setCellValueFactory(new PropertyValueFactory<>("word"));
        tableColumnToken.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableLexicon.getItems().addAll(this.lexical);
    }


    /*archivio Prueba{
        init main(current_){
        }
    }*/

}
