package omkar.com.callsettings.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import omkar.com.callsettings.dao.PrefixerDao;
import omkar.com.callsettings.dao.PrefixerDaoFactory;
import omkar.com.callsettings.model.PrefixerBean;

/**
 * Created by AnilOmkar on 26/7/15.
 */
public class PrefixerService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(prefixerReceiver, filter);
        Log.d("PrefixerService", "Prefixer receiver registered successfully :)");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(prefixerReceiver);
    }

    private final BroadcastReceiver prefixerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            PrefixerDao dao = PrefixerDaoFactory.getDao(context);

            try {
                Map<String, PrefixerBean> prefixers = dao.getPrefixers(false);
                if(prefixers.size() > 0) {
                    String phoneNumber = getResultData();

                    if (phoneNumber == null) {
                        // We could not find any previous data. Use the original phone number in this case.
                        phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    }

                    for(Map.Entry<String, PrefixerBean> entry : prefixers.entrySet()) {
                        PrefixerBean bean = entry.getValue();
                        if(phoneNumber.startsWith(bean.getStartingWith())) {
                            phoneNumber = phoneNumber.replace(bean.getStartingWith(), bean.getReplaceWith());
                            setResultData(phoneNumber);

                            Toast.makeText(context, "Replaced number using CallerSettings++ :)", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }
            }catch(Exception ex) {
                Log.d("PrefixerReceiver", "Exception while getting prefixers", ex);
            }
        }
    };
}
