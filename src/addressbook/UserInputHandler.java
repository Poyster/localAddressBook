package addressbook;

import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInputHandler implements Serializable {

    private Runnable autoSave;
    private Register register = new Register();
    private Runnable externalContactsHandler;
    private FileHandler fileHandler = new FileHandler();
    private final static Logger logger = Logger.getLogger(UserInputHandler.class.getName());

    public void mainMenu() {

        externalContactsHandler = new ExternalContactsHandler(register);
        Thread thread2 = new Thread(externalContactsHandler);
        thread2.start();

        autoSave = new AutoSave(register);
        Thread thread = new Thread(autoSave);
        thread.start();
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
