package omkar.com.callsettings.dao;

import java.util.Map;

import omkar.com.callsettings.model.PrefixerBean;

/**
 * Created by AnilOmkar on 21/7/15.
 */
public interface PrefixerDao {
    void savePrefixer(PrefixerBean prefixer) throws Exception;
    PrefixerBean getPrefixer(String prefixerName, boolean isReload) throws Exception;
    Map<String, PrefixerBean> getPrefixers(boolean isReload) throws Exception;
}
