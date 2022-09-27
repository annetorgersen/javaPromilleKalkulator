package promillekalkulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import promillekalkulator.models.Person;


public class PersonTest {
    private Person anne;
    private Person Martin;
    

    @BeforeEach
    public void BeforeEach() {
        anne = new Person("anne", 'F', 60.0);
        Martin = new Person("Martin", 'M', 75.0);

    }

    @Test
    public void testGender() {
        assertEquals(anne.getGender(), 'F');
        assertEquals(Martin.getGender(), 'M');

        // Tester med ugyldige verdier
        Assertions.assertThrows(IllegalArgumentException.class, () -> Martin.setGender('A'), "feil kjønn");
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setGender('5'),("feil kjønn, kjønn kan ikke være et tall"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setGender('f'), ("feil kjønn, skal være F"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Martin.setGender('m'), "feil kjønn");

    }

    @Test
    public void testGetName() {
        // Tester med gydlige verdier, riktig navn
        assertEquals(anne.getName(), "anne");
        assertEquals(Martin.getName(), "Martin");

        // Tester ugyldig navn
        Assertions.assertThrows(IllegalArgumentException.class, () -> Martin.setName("123"), "Navnet kan ikke inneholde tall");
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setName(".-/()="), "Navnet er ikke gyldig");
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setName("anne123"), "Navnet er ikke gyldig");
    }

    @Test
    public void testGetWeight() {
        // tester med gyldige verdier
        assertEquals(anne.getWeight(), 60.0);
        assertEquals(Martin.getWeight(), 75.0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> Martin.setWeight(-1.0), "vekten kan ikke være negativ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setWeight(0.0), "vekten kan ikke være 0");
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setWeight(300.00), "vekten kan ikke være over 200");
        
    }

    @Test
    public void testGetHours() {
        anne.setHours(4);
        assertEquals(anne.getHours(), 4);
        Martin.setHours(10);
        assertEquals(Martin.getHours(), 10);

        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setHours(25), "timene kan ikke være over 24");
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setHours(-1), "timene kan ikke være negative");
    }
    

    @Test
    public void testGetMinutes() {
        anne.setMinutes(5);
        assertEquals(anne.getMinutes(), 0.08333333333333333);
        Martin.setMinutes(10);
        assertEquals(Martin.getMinutes(), 0.16666666666666666);

        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setMinutes(70), "timene kan ikke være over 60");
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setMinutes(-1), "timene kan ikke være negative");
        Assertions.assertThrows(IllegalArgumentException.class, () -> anne.setMinutes(12), "timene må være deleige med 5");
    }

    @Test
    public void testGetPromiller() {
        anne.addPromiller(0.2);
        anne.addPromiller(4.0);

        assertEquals(anne.getPromiller(), Arrays.asList(0.2,4.0));
    
    }

    @Test
    public void setPromillerTest() {
       assertEquals(Martin.getPromiller(), Arrays.asList());
       anne.addPromiller(0.2);
       anne.addPromiller(4.0);
       assertEquals(anne.getPromiller(), Arrays.asList(0.2, 4.0));
    }


    @Test
    public void getPromillerWithSemiTest() {
        
        assertEquals(Martin.getPromillerWithSemi(), "[ ]");

        anne.addPromiller(0.2);
        anne.addPromiller(4.0);
        assertEquals(anne.getPromillerWithSemi(), ("[ 0.2;4.0]"));

    }

    @Test
    public void addPromillerTest() {
       anne.addPromiller(0.22);
       anne.addPromiller(0.55);

        assertEquals(Arrays.asList(0.22,0.55), anne.getPromiller());

    }


}
