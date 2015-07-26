package omkar.com.callsettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import omkar.com.callsettings.service.PrefixerService;

/**
 * Created by AnilOmkar on 26/7/15.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Boot", "Received event");
        Intent prefixerService = new Intent(context, PrefixerService.class);
        context.startService(prefixerService);
    }
}
