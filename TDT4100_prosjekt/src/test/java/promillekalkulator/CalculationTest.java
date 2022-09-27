package promillekalkulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import promillekalkulator.models.Alcohol;
import promillekalkulator.models.Calculations;
import promillekalkulator.models.Person;

public class CalculationTest {
    private Calculations calculation;
    private Alcohol alcohol;
    private Alcohol alcohol2;
    private Alcohol alcohol3;
    private Alcohol alcohol5;
    private Alcohol alcohol6;
    private Person person;
    private ArrayList<Alcohol> listOfDrinks;
    private ArrayList<Alcohol> listOfDrinks2;

    @BeforeEach
    public void BeforeEach() {
        calculation = new Calculations();
        person = new Person("anne", 'F', 60.0);
        alcohol = new Alcohol("ØL", 5, 4.7, 5.0);
        alcohol2 = new Alcohol("ØL", 5, 4.0, 0.0);
        alcohol3 = new Alcohol("ØL", 5, 4.0, -1.0);
        alcohol5 = new Alcohol("ØL", 4, 7.0, 10.0);
        alcohol6 = new Alcohol("ØL", 1, 0.1, 1.0);
       listOfDrinks = new ArrayList<>();
       listOfDrinks2 = new ArrayList<>();   
    }

    @Test
    public void alcoholGramsTest() {
        //Tester at kalkulasjonen av alcoholgram blir riktig ved gyldige verdier
        assertEquals(94, calculation.alcoholGram(alcohol));
        assertEquals(0, calculation.alcoholGram(alcohol2));
        
    }

    @Test
    public void induvidualBurnTest() {
        //Tester kalkulasjonen med gyldige verdier
        assertEquals(33, calculation.induvidualBurn(person));

    }

    @Test
    public void burnPerHourTest() {
        person.setHours(3);
        person.setMinutes(20);
    
        assertEquals(0.5, calculation.burnPerHour(person));

    }


    @Test
    public void totalAlcoholGramsTest() {
        
        //teste at summen av alkoholgram av alle de forskjellige drikkene er riktig
        listOfDrinks.add(alcohol);
        listOfDrinks.add(alcohol2);
        person.setListOfDrinks(listOfDrinks);
        assertEquals(94.0, calculation.totalAlcoholGrams(listOfDrinks));

        listOfDrinks.add(alcohol5);
        person.setListOfDrinks(listOfDrinks);
        assertEquals(318.0, calculation.totalAlcoholGrams(listOfDrinks));

        listOfDrinks2.add(alcohol3);  
        listOfDrinks2.add(alcohol6);    
        person.setListOfDrinks(listOfDrinks2);

        //tester at den ikke bruker negative tall
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculation.totalAlcoholGrams(listOfDrinks2), "gram kan ikke være negativt");

    }

    @Test
    public void promilleTest() {
        listOfDrinks.add(alcohol);
        person.setHours(3);
        person.setMinutes(20);
        person.setListOfDrinks(listOfDrinks);

        assertEquals(calculation.promille(person), 2.35);

        //Tester ikke negative verdier fordi dersom tidsrommet blir for stort vil promillen bli negativ og vil tilsvare at brukeren er edru
  
    }
    
}
