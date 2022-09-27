package promillekalkulator.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import promillekalkulator.models.Person;
import promillekalkulator.models.UserManager;

public class PersonController extends Controller {

    @FXML
    private Button next;

    @FXML
    private RadioButton female, male;

    @FXML
    private Slider weight = new Slider(40, 150, 1);

    @FXML
    private TextField name;

    @FXML
    private Text invalidUserName, userNameTaken, genderError;

    private Person person;

    @FXML
    public void initialize() {
        person = new Person();
        next.setOnAction(event -> nextbutton());

        weight.setShowTickMarks(true);
        weight.setShowTickLabels(true);
        weight.setSnapToTicks(true);
        weight.showTickMarksProperty();
        weight.setMajorTickUnit(10.0);
        weight.setBlockIncrement(5.0);

        female.setOnAction(event -> onSetFemale());
        male.setOnAction(e -> onSetMale());
    }

    private void nextbutton() {
        UserManager user = new UserManager();

        invalidUserName.setText("");
        userNameTaken.setText("");

        try {
            if (name.getText().isEmpty()) {
                userNameTaken.setText("Du må lage et brukernavn!");

            } else if (female.isSelected() == false && male.isSelected() == false) {
                genderError.setText("Du må velge et kjønn!");

            } else if (user.checkIfUserExists("users", name.getText()) == false) {
                this.person.setWeight(Double.valueOf((int) weight.getValue()));
                this.person.setName(name.getText());
                user.addUserToFile("users", person);
                user.saveLatestPerson("loggedInHistory", person);
                changeScene("Calculation.fxml");

            } else if (user.checkIfUserExists("users", name.getText()) == true) {
                userNameTaken.setText("Brukernavnet er allerde i bruk!");
            }
        } catch (Exception e) {
            invalidUserName(e.getMessage());
        }
    }

    private void invalidUserName(String message) {
        invalidUserName.setText("Navnet kan kun inneholde bokstavene a-z");
    }

    private void onSetFemale() {
        person.setGender('F');
    }

    private void onSetMale() {
        person.setGender('M');
    }

}
