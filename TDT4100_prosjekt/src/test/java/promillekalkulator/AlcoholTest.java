package promillekalkulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import promillekalkulator.models.Alcohol;

public class AlcoholTest {
    private Alcohol alcohol;

    @BeforeEach
    public void beforeEach() {
        alcohol = new Alcohol("ØL", 5, 4.7, 5.0);
    }


   @Test
    public void testGetAlcoholType() {
        alcohol.setAlcoholType("ØL");
        assertEquals(alcohol.getAlcoholType(), "ØL");
        alcohol.setAlcoholType("VIN");
        assertEquals(alcohol.getAlcoholType(), "VIN");
        alcohol.setAlcoholType("BRENNEVIN");
        assertEquals(alcohol.getAlcoholType(), "BRENNEVIN");
        alcohol.setAlcoholType("ANNET");
        assertEquals(alcohol.getAlcoholType(), "ANNET");

        // Tester med ugyldige verdier
        Assertions.assertThrows(IllegalArgumentException.class, () -> alcohol.setAlcoholType("3"), "kan ikke være et tall");
        Assertions.assertThrows(IllegalArgumentException.class, () -> alcohol.setAlcoholType("-./"), "kan ikke være tegn");
    }

    @Test
    public void testGetPercent() {
        alcohol.setPercent(4.0);
        assertEquals(alcohol.getPercent(), 4.0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> alcohol.setPercent(-1.0), "kan ikke være negativ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> alcohol.setPercent(100.0), "kan ikke være over 60");
    }

    @Test
    public void testGetVolume() {
        alcohol.setVolume(0.5);
        assertEquals(alcohol.getVolume(), 0.5);

        Assertions.assertThrows(IllegalArgumentException.class, () -> alcohol.setVolume(-1.0), "kan ikke være negativ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> alcohol.setVolume(16.0), "kan ikke være mer enn 10");
    }

    @Test
    public void testGetNumberOfUnits() {
        alcohol.setNumberOfUnits(6);
        assertEquals(alcohol.getNumberOfUnits(), (6));

        Assertions.assertThrows(IllegalArgumentException.class, () -> alcohol.setNumberOfUnits(-1), "kan ikke være negativ");
        Assertions.assertThrows(IllegalArgumentException.class, () -> alcohol.setNumberOfUnits(50), "kan ikke være mer enn 20");
    }


    
}
