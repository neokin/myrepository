
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    public static void add(String message) {
        try {
            FileWriter out = new FileWriter("log.txt", true);
            out.write(message);
            out.close();
        } catch (IOException e) {
            Logger.add(e.toString());
        }
    }


    public static void add(Exception exception)
    {
        Logger.add("Exception caught \n" + exception.getMessage() + "\n at" + System.currentTimeMillis() + "\n\r");
    }
}