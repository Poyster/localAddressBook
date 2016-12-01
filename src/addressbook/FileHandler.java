package addressbook;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler implements Serializable {
    private File file = new File("AddressBookStart");
    private final static Logger logger = Logger.getLogger(FileHandler.class.getName());


    public ArrayList<Contact> loadOnStart() {
        ArrayList<Contact> contacts = null;
        if (file.exists() && !file.isDirectory()) {

            try (FileInputStream fis = new FileInputStream(file)) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                contacts = (ArrayList<Contact>) ois.readObject();
            } catch (FileNotFoundException e) {
                logger.log(Level.SEVERE, "File not found: ", e);
            } catch (ClassNotFoundException e) {
                logger.log(Level.SEVERE, "Class not found: ", e);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Something wrong occurred: ", e);
            }
            return contacts;
        }
        return contacts;
    }

    public synchronized void saveToFile(ArrayList<Contact> addressBook) {

        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(addressBook);
            logger.log(Level.INFO, "Saving localContacts");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Something wrong occurred: ", e);
        }
    }
}
