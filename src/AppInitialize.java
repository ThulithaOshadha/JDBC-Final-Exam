import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class AppInitialize extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/StudentForm.fxml"))));
        primaryStage.setTitle("JDBC FINAL");
        primaryStage.show();

    }
}
