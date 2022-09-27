package promillekalkulator.models;

import java.util.ArrayList;

public class Calculations {
    // konstant som finner antall gram alkohol per desiliter
    private final double GRAM = 0.8; 
    // andelen av kroppsmassen som alkoholen fordeler seg på for kvinner
    private final double FEMALE = 0.55;
    // andelen av kroppsmassen som alkoholen fordeler seg på for menn 
    private final double MALE = 0.65; 
    // Forbrenningskonstant (hvor raskt kroppen forbrenner alkohol)
    private final double BURN = 0.15; // Forbrenningskonstant (hvor raskt kroppen forbrenner alkohol)


    public Calculations() {
    }

    // Regner ut alkoholen i gram
    public double alcoholGram(Alcohol alcohol) {
        double volume = alcohol.getVolume() * alcohol.getNumberOfUnits();
        double percent = alcohol.getPercent();

        return volume * percent * GRAM;
    }

    // Regner ut person - objektets forbrenning basert på kjønn og vekt
    public double induvidualBurn(Person person) {
        double weight = person.getWeight();
        char gender = person.getGender();

        if (!(person.getWeight() < 40.0 || person.getWeight() > 200.0)) {
            if (gender == 'F') {
                return weight * FEMALE;
            } else {
                return weight * MALE;
            }

        } else {
            throw new IllegalArgumentException("Weight invalid");
        }
    }

    // Regner ut Person - objektets forbrenning per time
    public double burnPerHour(Person person) {
        double time = person.getHours() + (person.getMinutes());
        double burnPerHour = BURN * time;
        return burnPerHour;
    }

    // Itererer gjennom listOfDrinks og regner ut alkoholen i gram for hver alkoholtype i listen.
    public double totalAlcoholGrams(ArrayList<Alcohol> listOfDrinks) {
        double sum = 0.0;
        if (listOfDrinks.isEmpty()) {
            throw new IllegalArgumentException("list of drinks is empty");
        } else {
            for (int i = 0; i < listOfDrinks.size(); i++) {
                Alcohol alcohol = listOfDrinks.get(i);
                sum += alcoholGram(alcohol);
                if (sum < 0) {
                    throw new IllegalArgumentException("Invalid sum of total alcoholgrams");
                }
            }
        }
        return sum;
    }

    // Regner ut Person - objektets promille og runder av til to desimaler.
    public double promille(Person person) {
        double alcoholGram = totalAlcoholGrams(person.getListOfDrinks());
        double induvidualBurn = induvidualBurn(person);
        double burnPerHour = burnPerHour(person);

        return Math.round(((alcoholGram / induvidualBurn) - burnPerHour) * 100.0) / 100.0;
    }

}
