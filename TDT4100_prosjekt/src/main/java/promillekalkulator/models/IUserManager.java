package promillekalkulator.models;

public interface IUserManager {

    Person getUser(String filename, String userName);

    void updateUserFile(String filename, Person person);

    void addUserToFile(String filename, Person person);
}
