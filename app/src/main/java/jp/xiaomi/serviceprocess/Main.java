package jp.xiaomi.serviceprocess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * //TODO: Javadoc on jp.xiaomi.serviceprocess.Main
 *
 * @author Damien
 * @version //TODO version
 * @since 2016-07-12
 */
public class Main {

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2001);

        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        System.out.println(sdf.format(date));

        System.out.println(c.get(Calendar.YEAR));
    }
}
