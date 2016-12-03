package addressbook;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ExternalContactsHandler implements Runnable {

    private Register register = new Register();
    private Contact contact;

    public ExternalContactsHandler(Register register) {
        this.register = register;
    }


    public void receiveFromServer() {
        try {

            Socket socket = new Socket("localhost", 61616);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            writer.println("getAll");
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        receiveFromServer();

    }
}

