package jaet;

import jaet.Models.AnalysisTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("Views/lexicalAnalyzer.fxml"));
        primaryStage.setTitle("Lexical Analyzer");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        AnalysisTable table = new AnalysisTable();
        //table.contarTabla();
        /*
        System.out.println(table.existDerivation("<DCM>","#"));
        System.out.println(table.existDerivation("<RC>","\\{"));
        System.out.println(table.existDerivation("<DCM>","#"));
        System.out.println(table.existDerivation("<DCM>","#"));
        System.out.println(table.existDerivation("<DCM>","#"));
        System.out.println(table.existDerivation("<DCM>","#"));
        System.out.println(table.existDerivation("<DCM>","#"));*/
        launch(args);
    }
}
