package promillekalkulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import promillekalkulator.controllers.Controller;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserManager.fxml"));
        primaryStage.setTitle("Promillekalkulator");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
        ((Controller) loader.getController()).setStage(primaryStage);
    }

}
