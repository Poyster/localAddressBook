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

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            writer.println("getall");
            writer.flush();


            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            try {
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    String[] splitLine = line.split(" ");
                    if (splitLine.length > 1) {
                        contact = new Contact(splitLine[0], splitLine[1], splitLine[2], splitLine[3]);
                        register.serverContacts.add(contact);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            reader.close();
            writer.close();
            socket.close();

        } catch (SocketException e) {
            System.out.println("Connection to External Contacts Server lost.");
            logger.log(Level.SEVERE, "Connection to External Contacts Server lost.", e);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

