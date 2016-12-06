package addressbook;


import static addressbook.LogHandler.setupLogging;

public class Program {

    private UserInputHandler userInputHandler = new UserInputHandler();


    public void run() {
        setupLogging();
        userInputHandler.mainMenu();
    }
}