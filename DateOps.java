import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class DateOps {
    public static Double currentTimeUnix(){
        Long sec = Instant.now().getEpochSecond();
        return sec.doubleValue();
    }

    public static Date secToDate(Double sec){
        Date date = new Date(sec.longValue() * 1000L);
        return date;
    }

    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
}
