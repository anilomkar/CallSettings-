package omkar.com.callsettings.dao;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import omkar.com.callsettings.model.PrefixerBean;

/**
 * Created by AnilOmkar on 21/7/15.
 */
public class PrefixerDaoImpl implements PrefixerDao {

    private static final String FILE_NAME = "prefixer.dat";
    private final Context context;
    private Map<String, PrefixerBean> prefixers;

    public PrefixerDaoImpl(Context context) throws Exception {
        this.context = context;
        loadPrefixers();
    }

    @Override
    public void savePrefixer(PrefixerBean prefixer) throws Exception {
        prefixers.put(prefixer.getName(), prefixer);
        saveToFile(prefixers);
    }

    @Override
    public PrefixerBean getPrefixer(String prefixerName, boolean isReload) throws Exception {
        if(isReload) {
            loadPrefixers();
        }

        return this.prefixers.get(prefixerName);
    }

    @Override
    public Map<String, PrefixerBean> getPrefixers(boolean isReload) throws Exception {
        if(isReload) {
            loadPrefixers();
        }
        return this.prefixers;
    }

    private void saveToFile(Map<String, PrefixerBean> prefixerBean) throws Exception {
        FileOutputStream outputStream = this.context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        IOUtils.write(toJson(prefixers), outputStream);
    }

    private void loadPrefixers() throws Exception {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = this.context.openFileInput(FILE_NAME);
        } catch(FileNotFoundException ex) {
            //File not found.. create empty Map
            prefixers = new HashMap<String, PrefixerBean>();
            return;
        }

        StringWriter sw = new StringWriter();
        IOUtils.copy(fileInputStream, sw);
        prefixers = fromJson(sw.toString());
    }

    private Map<String, PrefixerBean> fromJson(String jsonString) {
        Gson gson = new Gson();
        Type stringPrefixerBeanMap = new TypeToken<Map<String, PrefixerBean>>(){}.getType();
        return gson.fromJson(jsonString, stringPrefixerBeanMap);
    }

    private String toJson(Map<String, PrefixerBean> prefixers) {
        Gson gson = new Gson();
        Type stringPrefixerBeanMap = new TypeToken<Map<String, PrefixerBean>>(){}.getType();
        return gson.toJson(prefixers, stringPrefixerBeanMap);
    }
}
