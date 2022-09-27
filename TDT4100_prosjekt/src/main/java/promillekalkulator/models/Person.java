package promillekalkulator.models;

import java.util.ArrayList;

public class Person {

    private String name = "";
    private char gender;
    private double weight;
    private ArrayList<Alcohol> listOfDrinks = new ArrayList<>();
    private double hours;
    private double minutes;
    private ArrayList<Double> promiller; // liste over promiller beregnet i calculations

    public Person() {
    }

    public Person(String name, char gender, double weight) { // neste - knapp, kjør denne her og lagre som person obj
        setGender(gender);
        setName(name);
        setWeight(weight);
        this.promiller = new ArrayList<Double>(); // Når et personobjekt opprettes, lages det en tom liste for promiller
    }

    public void setName(String name) {
        if (name.matches("[a-zA-Z]+")) {
            this.name = name;
            return;
        }
        throw new IllegalArgumentException("Wrong name input");
    }

    public String getName() {
        if (name.matches("[a-zA-Z]+")) {
            return name;
        } else {
            throw new IllegalArgumentException("Name should only contain letters!");
        }
    }

    public void setGender(char gender) {
        if (gender == 'F' || gender == 'M') {
            this.gender = gender;
            return;
        }
        throw new IllegalArgumentException("Wrong gender input!");
    }

    public char getGender() throws IllegalArgumentException {
        return gender;
    }

    public void setWeight(double weight) {
        if ((weight >= 40.0) && (weight <= 200.0)) {
            this.weight = weight;
            return;
        }
        throw new IllegalArgumentException("wrong weight input");
    }

    public double getWeight() {
        return this.weight;
    }

    public void setHours(double hours) {
        if ((hours >= 0) && (hours <= 24)) {
            this.hours = hours;
        } else {
            throw new IllegalArgumentException("not valid hours input");
        }
    }

    public double getHours() {
        if ((hours >= 0) && (hours <= 24)) {
            return hours;
        } else {
            throw new IllegalArgumentException("Not a valid number of hours!");
        }
    }

    public double getMinutes() {
        return minutes;
    }

    // Returner minutter gjort om til timer.
    public void setMinutes(double minutes) {
        if ((minutes >= 0) && (minutes <= 55) && (minutes % 5 == 0)) {
            minutes = minutes / 60;
            this.minutes = minutes;
        } else {
            throw new IllegalArgumentException("Invalid minutes ");
        }
    }

    public ArrayList<Alcohol> getListOfDrinks() {
        return new ArrayList<>(this.listOfDrinks);
    }

    public void setListOfDrinks(ArrayList<Alcohol> listOfDrinks) {
        this.listOfDrinks.clear();
        for (Alcohol alcohol : listOfDrinks) {
            this.listOfDrinks.add(alcohol);
        }
    }

    // Tar inn promillelisten som String og gjør om til liste av promiller
    public void setPromiller(String promiller) {
        if (promiller.equals((" [ ]"))) {
            return;
        } else {

            String[] promilleList = promiller.replace("[", "").replace("]", "").split(";");
            for (String promillen : promilleList) {
                this.promiller.add(Double.parseDouble(promillen));
            }
        }
    }

    public ArrayList<Double> getPromiller() {
        return promiller;
    }

    // Gjør promillelisten til String
    public String getPromillerWithSemi() {
        String listStreng = " ";
        if (promiller == null) {
            return "[ ]";
        }
        for (Double promillen : promiller) {
            listStreng += promillen + ";";
        }
        if (promiller.size() > 0) {
            listStreng = listStreng.substring(0, listStreng.length() - 1);
        }
        listStreng = "[" + listStreng + "]";
        return listStreng;
    }

    public void addPromiller(double promille) {
        if (promille > 0) {
            promiller.add(promille);
        } else {
            throw new IllegalArgumentException("Vil ikke legge til negativ promille");
        }
    }

    // toStringen brukes til å skrive brukerinformasjonen til fil
    @Override
    public String toString() {
        return getName() + ": " + getGender() + ": " + getWeight() + ": " + getPromillerWithSemi();
    }

}
