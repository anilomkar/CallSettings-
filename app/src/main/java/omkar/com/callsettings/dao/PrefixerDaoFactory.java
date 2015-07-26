package omkar.com.callsettings.dao;

import android.content.Context;
import android.util.Log;

/**
 * Created by AnilOmkar on 26/7/15.
 */
public class PrefixerDaoFactory {
    private static PrefixerDao dao;

    public static synchronized PrefixerDao getDao(Context context) {
        if (dao == null) {
            try {
                dao = new PrefixerDaoImpl(context);
            } catch (Exception e) {
                Log.d("PrefixDao", "Exception while initializing Prefixer Dao", e);
                throw new RuntimeException(e);
            }
        }
        return dao;
    }
}
