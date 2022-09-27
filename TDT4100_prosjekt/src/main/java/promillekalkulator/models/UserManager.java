package promillekalkulator.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import promillekalkulator.App;

public class UserManager implements IUserManager {

    private Person person;
    ArrayList<String> users = new ArrayList<>();

    public UserManager() {
    }

    // Denne metoden leser gjennom brukerfilen og leter etter brukernavnet.
    // Når scanneren finner brukernavnet initialiseres et person-objekt som returneres.
    @Override
    public Person getUser(String filename, String userName) {

        try (Scanner scanner = new Scanner(getFilePath(filename).toFile())) {

            while (scanner.hasNextLine()) {
                String textline[] = scanner.nextLine().split(":");
                String name = textline[0];

                if (name.equals(userName)) {
                    person = new Person(name, textline[1].strip().charAt(0), Double.valueOf(textline[2]));
                    person.setPromiller(textline[3]);
                    return person;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    // Denne metoden returnerer true dersom scanneren finner brukernavnet i en av linjene i brukerfilen.
    public boolean checkIfUserExists(String filename, String userName) {

        try (Scanner scanner = new Scanner(getFilePath(filename).toFile())) {

            while (scanner.hasNextLine()) {
                String textline[] = scanner.nextLine().split(":");
                String name = textline[0];
                if (name.equals(userName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Denne metoden lager en brukerliste med linjene i brukerfilen
    private void createUsersList(String filename) {

        users.clear();
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(getFilePath(filename).toFile()));

            String user = bufReader.readLine();
            while (user != null) {
                users.add(user);
                user = bufReader.readLine();
            }
            bufReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Denne metoden tømmer filen som tas inn som parameter
    private void emptyUserfile(String filename) {
        try (FileWriter fileWriter = new FileWriter(getFilePath(filename).toFile(), false)) {
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.print("");

        } catch (Exception e) {

        }
    }

    // Denne metoden itererer gjennom brukerlisten og fjerner elementet som inneholder brukernavnet til person-objektet som tas inn.
    private void removeUserFromUserList(Person person) {
        for (int i = 0; i < users.size(); i++) {
            String thisUser[] = users.get(i).split(":");

            if (thisUser[0].equals(person.getName())) {
                users.remove(i);
            }
        }
    }

    // Denne metoden fjerner den linjen i filen som inneholder brukernavnet til person-objektet som tas inn.
    public void updateUserFile(String filename, Person person) {

        createUsersList(filename);
        emptyUserfile(filename);
        removeUserFromUserList(person);

        try (FileWriter fileWriter = new FileWriter(getFilePath(filename).toFile(), true)) {
            PrintWriter writer = new PrintWriter(fileWriter);
            for (int index = 0; index < users.size(); index++) {
                writer.println(users.get(index));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Denne metoden legger til en linje i filen.
    // Det som legges til er Person - klassen sin toString() - metode.
    public void addUserToFile(String filename, Person person) {
        try {
            FileWriter fileWriter = new FileWriter(getFilePath(filename).toFile(), true);
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.println(person.toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Denne metoden returnerer pathen til tekstfilen.
    public Path getFilePath(String filename) {
        Path path = Path.of(App.class.getResource("files/").getFile() + filename + ".txt");

        if (path == null)
            System.out.println("No path");

        return path;
    }

    // Denne metoden brukes til å lagre en innlogging eller brukeropprettelse.
    public void saveLatestPerson(String filename, Person person) {
        String name = person.getName();
        char gender = person.getGender();
        double weight = person.getWeight();
        String inputLine = name + ";" + gender + ";" + weight;

        try {
            FileWriter fileWriter = new FileWriter(getFilePath(filename).toFile(), true);
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.println(inputLine);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public Person getLatestPerson(String history, String filename) {
        Person person = null;

        try {
            Scanner scanner = new Scanner(getFilePath(history).toFile());
            String latestLine = "";
            while (scanner.hasNextLine()) {
                latestLine = scanner.nextLine();
            }
            scanner.close();

            String textline[] = latestLine.split(";");
            person = getUser(filename, textline[0]);

        } catch (IOException e) {

        }

        return person;
    }

}
