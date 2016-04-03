package Global;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kien Seng on 03-Apr-16.
 */
public class Time {

    public String getCurrentTime(String timeFormat) throws Exception{
        SimpleDateFormat simpleDate = new SimpleDateFormat(timeFormat);

        Date now = new Date();
        String date = simpleDate.format(now);

        return date;
    }
}
