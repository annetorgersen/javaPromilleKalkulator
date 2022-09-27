package promillekalkulator.controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import promillekalkulator.App;

public abstract class Controller {
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(String fxml) {
        this.changeScene(fxml, null);
    }

    public void changeScene(String fxml, String title) {

        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
        try {
            this.stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ((Controller) loader.getController()).setStage(this.stage);
        if (title != null)
            this.stage.setTitle(title);
    }
}
