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
        String [] datosAleer = {"\"","\'","0","1","2","3","4","5","6","7","8","9"};
                //{"#","a","_","while","for","enterokay","stamp","scegliere","{"};
        for(int i=0;i< datosAleer.length;i++) {
            table.existDerivation("<TD>", datosAleer[i]);
        }

        launch(args);
    }
}
