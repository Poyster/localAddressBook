package addressbook;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExternalContactsHandler {

    private Register register = new Register();
    private Contact contact;
    private final static Logger logger = Logger.getLogger(ExternalContactsHandler.class.getName());

    public ExternalContactsHandler(Register register) {
        this.register = register;
    }

    public void receiveFromServer(String ipAddress, int serverPort) {

        try {

            Socket socket = new Socket(ipAddress, serverPort);
            logger.log(Level.INFO, "Connection to external server with ip: " + ipAddress+ " and port: " +serverPort+ " successful");
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            writer.println("getall");
            logger.log(Level.INFO, "Sent getall-message to external server with ip: " + ipAddress+ " and port: " +serverPort);
            writer.println("exit");
            logger.log(Level.INFO, "Sent exit-message to external server with ip: " + ipAddress+ " and port: " +serverPort);
            writer.flush();


            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            try {
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    String[] splitLine = line.split(" ");
                    if (splitLine.length > 1) {
                        contact = new Contact(splitLine[1], splitLine[2], splitLine[3], splitLine[0]);
                        register.serverContacts.add(contact);
                        logger.log(Level.INFO, "New external contact added: " + line);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.log(Level.INFO, "ArrayIndexOutOfBoundsException", e);
            }
            reader.close();
            writer.close();
            socket.close();

        } catch (SocketException e) {
            System.out.println("Connection to External Contacts Server with ip: " +ipAddress+ " and port: "+serverPort+ " not available at the moment.");
            logger.log(Level.SEVERE, "Connection to External Contacts Server with ip: " +ipAddress+ " and port: "+serverPort+ " not available at the moment.", e);

        } catch (Exception e) {
            logger.log(Level.INFO, "Something wrong occurred: ", e);
        }
    }
}

