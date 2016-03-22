import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kienseng.koh on 3/22/2016.
 */
public class Logger {
    private static String getDate() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    public static void write(String message) throws Exception{
        System.out.println("DEBUG LOG(" + getDate() + "): "+ message);
    }
}
