import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class DateOps {
    public static Double currentTimeUnix(){
        Long milsec = Instant.now().getEpochSecond();
        return milsec.doubleValue();
    }

    public static Date milToDate(Double milsec){
        Date date = new Date(milsec.longValue() * 1000L);
        return date;
    }

    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
}
