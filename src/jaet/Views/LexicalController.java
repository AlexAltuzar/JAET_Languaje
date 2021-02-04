package jaet.Views;

import jaet.Models.LexicalAnalyzer;
import jaet.Models.LexicalToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class LexicalController {

    @FXML private TextArea terminal;
    @FXML private TableView<LexicalToken> tableLexicon;
    @FXML private TableColumn<LexicalToken, String> tableColumnLexicon;
    @FXML private TableColumn<LexicalToken, String> tableColumnToken;
    @FXML private Button btnAnalyze;

    private LexicalAnalyzer analyzer = new LexicalAnalyzer();
    private ArrayList<LexicalToken> lexical = new ArrayList<>();

    @FXML
    void CompileAnalyzer(MouseEvent event) {
        tableLexicon.getItems().clear();
        lexical = analyzer.analyze(terminal.getText().trim());
        showLexical();

    }

    private void showLexical(){
        tableColumnLexicon.setCellValueFactory(new PropertyValueFactory<>("word"));
        tableColumnToken.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableLexicon.getItems().addAll(this.lexical);
    }


}
