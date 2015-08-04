package omkar.com.callsettings.util;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by AnilOmkar on 4/8/15.
 */
public class AppUtil {
    public static void vibrate(final Context context, final int milliSeconds) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(milliSeconds);
            }
        }).start();
    }
}
