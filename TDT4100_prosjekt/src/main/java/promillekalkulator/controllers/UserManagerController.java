package promillekalkulator.controllers;

import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import promillekalkulator.models.UserManager;

public class UserManagerController extends Controller {

    String filename = "users";

    @FXML
    private Button logIn, newUser;

    @FXML
    private TextField userName;

    @FXML
    private Text feilmelding;

    @FXML
    public void initialize() {
        logIn.setOnAction(event -> {
            try {
                logInButton();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        newUser.setOnAction(event -> newUserButton());
    }

    private void logInButton() throws FileNotFoundException {
        UserManager user = new UserManager();
        feilmelding.setText("");
        if (userName.getText().isEmpty()) {
            feilmelding.setText("Skriv inn brukernavn");
        }

        else if (user.checkIfUserExists("users", userName.getText()) == true) {
            user.getUser("users", userName.getText());
            user.saveLatestPerson("loggedInHistory", user.getUser(filename, userName.getText()));
            changeScene("Calculation.fxml");

        } else {
            feilmelding.setText("Dette brukernavnet eksisterer ikke!");
        }

    }

    private void newUserButton() {
        changeScene("Person.fxml");
    }

}
