package addressbook;

import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInputHandler implements Serializable {

    private Runnable autoSave;
    private Register register = new Register();
    private ExternalContactsHandler externalContactsHandler = new ExternalContactsHandler(register);
    private FileHandler fileHandler = new FileHandler();
    private final static Logger logger = Logger.getLogger(UserInputHandler.class.getName());

    public void mainMenu() {

        externalContactsHandler = new ExternalContactsHandler(register);
        new Thread(() -> externalContactsHandler.receiveFromServer("localhost", 61616)).start();
        new Thread(() -> externalContactsHandler.receiveFromServer("localhost", 61614)).start();

        autoSave = new AutoSave(register);
        new Thread(autoSave).start();
        logger.log(Level.INFO, "User starts the Address Book");

        String menuInput = "";
        Scanner sc = new Scanner(System.in);

        while (!menuInput.equals("quit")) {

            System.out.println("\nWelcome! Type 'help' to see available commands" + '\n');

            menuInput = sc.nextLine();
            String[] splitInput = menuInput.split(" ");
            try {
                switch (splitInput[0]) {

                    case "add":
                        register.addContact(splitInput[1], splitInput[2], splitInput[3]);
                        if (splitInput.length > 4) {
                            register.toMuchInputRemovedAndAddedContact();
                            break;
                        }
                        break;

                    case "list":
                        register.showAllContacts(register.localContacts, register.serverContacts);
                        break;

                    case "search":
                        register.searchForContacts(splitInput[1]);
                        break;

                    case "help":
                        register.showHelpMenu();
                        break;
                    case "delete":
                        register.deleteContact(splitInput[1]);
                        break;

                    case "quit":
                        fileHandler.saveToFile(register.getLocalContacts());
                        register.quitProgram();

                    default:
                        register.invalidCommand(splitInput[0]);
                        break;
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                register.arrayIndex(e);
            }
        }
    }
}
