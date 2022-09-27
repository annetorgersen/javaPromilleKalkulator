package promillekalkulator.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import promillekalkulator.models.Alcohol;
import promillekalkulator.models.Calculations;
import promillekalkulator.models.Person;
import promillekalkulator.models.UserManager;

public class CalculationController extends Controller {

  @FXML
  GridPane gridpane;

  @FXML
  private Button back, calculate;

  @FXML
  private Text promilleOutput, listHeadline, volumeAndGrams, hourError, minutesError, promille1, promille2, promille3;

  @FXML
  private ComboBox<Double> hourBox, minutesBox;

  private ArrayList<Alcohol> alkohol = new ArrayList<>();

  private Person person;

  private UserManager user;

  private ArrayList<Integer> numberOfUnitsList = new ArrayList<>(
      (Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)));

  private ArrayList<Double> volumeList = new ArrayList<>((Arrays.asList(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.75,
      0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0, 2.5, 2.75, 3.3, 5.0, 7.5, 10.0)));

  private ArrayList<Double> prosentList = new ArrayList<>((Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 4.7,
      5.0, 5.5, 6.0, 6.5, 7.0, 7.5, 8.0, 8.5, 9.0, 9.5, 10.0, 10.5, 11.5, 12.0, 12.5, 13.0, 13.5, 14.0, 14.5, 15.0,
      15.5, 16.0, 16.5, 17.0, 17.5, 18.0, 18.5, 19.0, 19.5, 20.0, 20.5, 21.0, 21.9, 21.5, 22.0, 22.5, 23.0, 24.5, 25.0,
      25.5, 26.0, 26.5, 27.0, 27.5, 28.5, 29.0, 29.5, 30.0, 30.5, 31.0, 31.5, 32.0, 32.5, 33.0, 33.5, 34.0, 34.5, 35.0,
      35.5, 36.0, 36.5, 37.5, 38.0, 38.5, 39.0, 39.5, 40.0, 40.5, 41.0, 41.5, 42.0, 42.5, 43.0, 43.5, 44.0, 44.5, 45.0,
      45.5, 46.0, 46.5, 47.0, 47.5, 48.0, 48.5, 49.0, 49.5, 50.0, 50.5, 51.0, 51.5, 52.0, 52.5, 53.0, 53.5, 54.0, 54.5,
      55.0, 55.5, 56.0, 56.5, 57.0, 57.5, 58.0, 58.5, 59.0, 59.5, 60.0)));

  private ArrayList<Double> hourBoxList = new ArrayList<>((Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
      9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0)));

  private ArrayList<Double> minutesBoxList = new ArrayList<>(
      (Arrays.asList(0.0, 5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 35.0, 40.0, 45.0, 50.0, 55.0)));

  @FXML
  private void initialize() {
    user = new UserManager();
    person = user.getLatestPerson("loggedInHistory", "users");

    back.setOnAction(e -> backbutton());
    minutesBox.getItems().addAll((minutesBoxList));
    hourBox.getItems().addAll((hourBoxList));

    Alcohol beer = new Alcohol("ØL", 0, 0.0, 0.0);
    Alcohol wine = new Alcohol("VIN", 0, 0.0, 0.0);
    Alcohol booze = new Alcohol("BRENNEVIN", 0, 0.0, 0.0);
    Alcohol other = new Alcohol("ANNET", 0, 0.0, 0.0);

    alkohol.add(beer);
    alkohol.add(wine);
    alkohol.add(booze);
    alkohol.add(other);

    for (int i = 0; i < alkohol.size(); i++) {
      Alcohol alc = alkohol.get(i);
      Label alcoholLabel = new Label(alc.getAlcoholType());
      ComboBox<Double> volume = new ComboBox<>();
      ComboBox<Integer> antall = new ComboBox<>();
      ComboBox<Double> prosent = new ComboBox<>();
      gridpane.add(alcoholLabel, 0, i);
      gridpane.add(antall, 1, i);
      gridpane.add(volume, 2, i);
      gridpane.add(prosent, 3, i);
      antall.getItems().addAll((numberOfUnitsList));
      volume.getItems().addAll((volumeList));
      prosent.getItems().addAll((prosentList));

      antall.setOnAction(event -> {
        alc.setNumberOfUnits(antall.getValue());
      });

      volume.setOnAction(event -> {
        alc.setVolume(volume.getValue());
      });

      prosent.setOnAction(event -> {
        alc.setPercent(prosent.getValue());
      });

    }
    calculate.setOnAction(event -> calculateButton());

  }

  private void backbutton() {
    changeScene("UserManager.fxml");
  }

  private void calculateButton() {
    Calculations calc = new Calculations();

    hourError.setText("");
    minutesError.setText("");

    boolean invalidTime = false;

    try {
      person.setHours(this.hourBox.getValue());
    } catch (Exception e) {
      invalidTime = true;
      hourError(e.getMessage());
    }

    try {
      person.setMinutes(this.minutesBox.getValue());
    } catch (Exception e) {
      invalidTime = true;
      minutesError(e.getMessage());
    }

    if (invalidTime) {
      return;
    }

    int counter = 0;

    for (Alcohol alc : alkohol) {
      if (alc.getNumberOfUnits() > 0) {
        if (alc.getVolume() == 0 || alc.getPercent() == 0) {
          promilleOutput.setText("Promille kan ikke beregnes...");
          return;
        }
      } else {
        counter++;
      }
    }

    if (counter == alkohol.size()) {
      promilleOutput.setText("Promille kan ikke beregnes...");
      return;

    }

    person.setListOfDrinks(alkohol);

    Double promille = calc.promille(person);
    Double tid = Math.round((person.getHours() + person.getMinutes()) * 100.0) / 100.0;
    Double alkoholgram = Math.round(calc.totalAlcoholGrams(person.getListOfDrinks()) * 100.0) / 100.0;

    if (promille > 0) {
      person.addPromiller(promille);
      promilleOutput.setText("Promillen din er:  " + promille);
      volumeAndGrams.setText("Du drakk i " + tid + " timer," + " og drakk totalt " + alkoholgram + " gram alkohol.");

    } else if (alkohol.isEmpty()) { 
      promilleOutput.setText("mangler input for å beregne promille");

    } else {
      promilleOutput.setText("Har ikke promille");

    }

    user.updateUserFile("users", person);
    user.addUserToFile("users", person);

    if (person.getPromiller().size() > 3) {
      listHeadline.setText("De siste tre promillene du beregnet var: ");
      promille1.setText(person.getPromiller().get(person.getPromiller().size() - 2).toString());
      promille2.setText(person.getPromiller().get(person.getPromiller().size() - 3).toString());
      promille3.setText(person.getPromiller().get(person.getPromiller().size() - 4).toString());
    }

    else if (person.getPromiller().size() == 2) {
      listHeadline.setText("Du har tidligere beregnet følgende promille: ");
      promille1.setText(person.getPromiller().get(0).toString());

    }

    else if (person.getPromiller().size() == 3) {
      listHeadline.setText("Du har tidligere beregnet følgende promiller: ");
      promille1.setText(person.getPromiller().get(0).toString());
      promille2.setText(person.getPromiller().get(1).toString());

    }

    else {
      if (promille > 0) {
        listHeadline.setText("Dette er din første promilleberegning! ");
      } else {
        listHeadline.setText("");
      }
    }
  }

  private void hourError(String message) {
    hourError.setText("Du må velge antall timer");

  }

  private void minutesError(String message) {
    minutesError.setText("Du må velge antall minutter");

  }

}
