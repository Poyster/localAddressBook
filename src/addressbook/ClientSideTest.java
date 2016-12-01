package addressbook;

import java.io.*;
import java.net.Socket;

public class ClientSideTest {

    private Register register = new Register();

    private Contact contact;


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

                    contact = new Contact(splitLine[0], splitLine[1], splitLine[2], splitLine[3]);
                    register.serverContacts.add(contact);
                }

            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("");
            }
            reader.close();
            writer.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void printServerContacts(){
       register.showAllContacts(register.serverContacts);
    }

}

