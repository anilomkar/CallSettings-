package omkar.com.callsettings.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import omkar.com.callsettings.R;
import omkar.com.callsettings.dao.PrefixerDao;
import omkar.com.callsettings.model.PrefixerBean;

/**
 * Created by AnilOmkar on 4/8/15.
 */
public class PrefixerListAdapter extends BaseAdapter {

    private final Context context;
    private final PrefixerDao dao;
    private final List<PrefixerBean> items;

    public PrefixerListAdapter(Context context, PrefixerDao dao) {
        this.context = context;
        this.dao = dao;

        Map<String, PrefixerBean> prefixers = null;
        try {
            prefixers = dao.getPrefixers(false);
        } catch (Exception e) {}

        this.items = new ArrayList<PrefixerBean>(prefixers.values());
    }


    public boolean contains(String name) {
        for (PrefixerBean bean : items) {
            if(bean.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void add(PrefixerBean item) {
        items.add(item);
    }

    public void remove(String prefixername) {
        items.remove(getItem(prefixername));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PrefixerBean getItem(int position) {
        return items.get(position);
    }

    public PrefixerBean getItem(String prefixerName) {
        for(PrefixerBean bean : items) {
            if(bean.getName().equals(prefixerName)) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = View.inflate(this.context, R.layout.item_list_app, null);
        }

        final PrefixerBean item = getItem(position);

        TextView prefixerNametw = (TextView) convertView.findViewById(R.id.prefixerNametw);
        prefixerNametw.setText(item.getName());


        CheckBox prefixerCheckbox = (CheckBox) convertView.findViewById(R.id.prefixerCheckbox);
        prefixerCheckbox.setChecked(item.isEnabled());

        prefixerCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setIsEnabled(((CheckBox) v).isChecked());
                try {
                    dao.savePrefixer(item);
                } catch (Exception ex) {
                    Log.d("PrefixerDao", "Exception while updating Enable status", ex);
                }
            }
        });

        return convertView;
    }
}
