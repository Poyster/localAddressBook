package addressbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Printer {
    private final static Logger logger = Logger.getLogger(Printer.class.getName());

    public void printAnSortedList(ArrayList<Contact> contacts, ArrayList<Contact> externalContacts) {
        ArrayList<Contact> sortedTempList = new ArrayList<>();
        sortedTempList.addAll(contacts);
        sortedTempList.addAll(externalContacts);
        Collections.sort(sortedTempList);

        System.out.println(contactsToString(sortedTempList));
        if (!contacts.isEmpty()) {
            logger.log(Level.FINE, "User looked at a list of all localContacts");
        } else if (contacts.isEmpty()) {
            System.out.println("There are currently no localContacts in the address book.");
            logger.log(Level.FINE, "User looked at an empty contact list");
        }
    }

    public void printAnSearchedList(ArrayList<Contact> contacts) {
        ArrayList<Contact> sortedTempList = new ArrayList<>();
        sortedTempList.addAll(contacts);
        Collections.sort(sortedTempList);

        System.out.println(contactsToString(sortedTempList));

    }

    private String contactsToString(ArrayList<Contact> contacts) {

        StringBuilder stringbuilder = new StringBuilder();

        for (Contact contact : contacts) {

            stringbuilder.append("Firstname: " + contact.getFirstName() + '\n' +
                    "Lastname: " + contact.getSurName() + '\n' +
                    "E-mail address: " + contact.getEmailAddress() + '\n' +
                    "ID: " + contact.getUniqueID() + '\n');
            stringbuilder.append("\n");
        }
        return stringbuilder.toString();
    }

}
