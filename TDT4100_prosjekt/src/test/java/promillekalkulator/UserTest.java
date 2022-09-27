package promillekalkulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import promillekalkulator.models.Person;
import promillekalkulator.models.UserManager;

public class UserTest {
    private Person person;
    private Person person2;
    private Person person3;
    private UserManager user;
    private  String userfile = "usersTest";
    private  String loggedInHistoryFile = "loggedInHistoryTest";
    ArrayList<String> actualUserList = new ArrayList<>();
  

    @BeforeEach
    public void setup() {
        user = new UserManager();
        person = new Person("anne", 'F', 50.0);
        person2 = new Person("martin", 'M', 70.0);
        person3 = new Person("tor", 'M', 75.0);

        clearUserFile(userfile);
        clearUserFile(loggedInHistoryFile);

        user.addUserToFile(userfile, person);
        
    }

    @Test
    public void getUserTest() {
        //Tester at metoden returnerer personen når personen finnes i userfile
        assertEquals(person.toString(), user.getUser(userfile, person.getName()).toString());

        //Tester at metoden returnerer null når personen ikke finnes i userfile
        assertEquals(null, user.getUser(userfile, person2.getName()));
        
    }

    @Test
    public void checkIfUserExistTest() {
        //Tester at metoden returener true når personen finnes i userfile
        assertEquals(true, user.checkIfUserExists(userfile, person.getName()));

        //Tester at metoden returener false når personen ikke finnes i userfile
        assertEquals(false, user.checkIfUserExists(userfile, person2.getName()));
    }

    @Test
    public void updatUsersFileTest() {
        //Lager en forventet brukerliste som sammenlignes med en liste over linjene i userfile etter at metoden updateUserFile er kjørt.
        //Dette er for teste at metoden fjerner riktig element.
        ArrayList<String> expectedUserList = new ArrayList<>();
        expectedUserList.add(person.toString());
        user.addUserToFile(userfile, person2);
        user.updateUserFile(userfile, person2);
        createActualUsersList(userfile);
        assertEquals(expectedUserList, actualUserList);
        //Tester med en annen forventet brukerliste
        expectedUserList.add(person3.toString());
        user.addUserToFile(userfile, person3);
        user.addUserToFile(userfile, person2);
        user.updateUserFile(userfile, person2);
        createActualUsersList(userfile);
        assertEquals(expectedUserList, actualUserList);




    }


    @Test
    public void addUserToFileTest() {
        //Tester at det legges til en linje i userfile når metoden kjøres
        assertEquals(1, countLinesInFile(userfile));
        }

    @Test
    public void getFilePathTest() {
        //Tester at metoden henter riktig fil
        Path path = Path.of(App.class.getResource("files/").getFile() + userfile + ".txt");        
        assertEquals(path, user.getFilePath(userfile));

    }


    @Test
    public void saveLatestPersonTest() {
        //Tester at det legges til en linje i loggedInHistoryFile når metoden kjøres
        user.saveLatestPerson(loggedInHistoryFile, person);
        assertEquals(1, countLinesInFile(loggedInHistoryFile));

    }


    @Test
    public void getLatestPersonTest() {
        //Tester om metoden henter den personen som er lagret i begge filene 
        user.saveLatestPerson(loggedInHistoryFile, person);  
        assertEquals(person.toString(), user.getLatestPerson(loggedInHistoryFile, userfile).toString());

        //Tester at dersom loggedInHistoryFile er tom, vil metoden returnere null
        clearUserFile(loggedInHistoryFile);
        assertEquals(null, user.getLatestPerson(loggedInHistoryFile, userfile));

        //Tester at dersom userfile er tom, vil metoden returnere null
        clearUserFile(userfile);
        assertEquals(null, user.getLatestPerson(loggedInHistoryFile, userfile));

        //Tester at metoden returnerer null hvis personen med dette brukernavnet finnes i loggedInHistoryFile, men ikke i userfile
        user.saveLatestPerson(loggedInHistoryFile, person2);
        assertEquals(null, user.getLatestPerson(loggedInHistoryFile, userfile));


    }


    public int countLinesInFile(String filename) {
        int counter = 0;
        try (Scanner scanner = new Scanner(user.getFilePath(filename).toFile())) { 
            
            while(scanner.hasNextLine()){
                counter++;
                return counter;
            }
            
        } catch (Exception e) {
            
        }
        return counter;
    }

    public  void clearUserFile(String filename) {
        try (FileWriter fileWriter = new FileWriter(user.getFilePath(filename).toFile(), false)) {
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.print("");

        } catch (Exception e) {
            
        }

    }

    private void createActualUsersList(String filename) { 
                                                                                             
        actualUserList.clear();
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(user.getFilePath(filename).toFile()));

            String user = bufReader.readLine();
            while (user != null) {
                actualUserList.add(user);
                user = bufReader.readLine();
            }
            bufReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    

}
