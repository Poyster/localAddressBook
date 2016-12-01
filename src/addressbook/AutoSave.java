package addressbook;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoSave implements Runnable{
    private Register register = new Register();
    private final static Logger logger = Logger.getLogger(AutoSave.class.getName());
    private FileHandler fileHandler = new FileHandler();

    public AutoSave(Register register) {
        this.register = register;
    }

    @Override
    public void run() {
        while (true){
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                logger.log(Level.SEVERE, "InterruptedException", e);
            }
            fileHandler.saveToFile(register.getLocalContacts());

        }

    }
}