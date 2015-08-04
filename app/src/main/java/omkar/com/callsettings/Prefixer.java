package omkar.com.callsettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import omkar.com.callsettings.adapter.PrefixerListAdapter;
import omkar.com.callsettings.dao.PrefixerDao;
import omkar.com.callsettings.dao.PrefixerDaoFactory;
import omkar.com.callsettings.model.PrefixerBean;
import omkar.com.callsettings.model.PrefixerScreenType;

import static android.widget.Toast.LENGTH_LONG;
import static omkar.com.callsettings.model.Constants.PREFIXER_BEAN;
import static omkar.com.callsettings.model.Constants.PREFIXER_NAME;
import static omkar.com.callsettings.model.Constants.PREFIXER_RESULT_REQ_CODE;
import static omkar.com.callsettings.model.Constants.PREFIXER_SCREEN_TYPE;
import static omkar.com.callsettings.model.Constants.PREFIXER_TRANSACTION_TYPE;
import static omkar.com.callsettings.model.Constants.TRANSACTION_TYPE_SAVE;


public class Prefixer extends AppCompatActivity {

    private PrefixerDao dao;
    PrefixerListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefixer);

        try {
            dao = PrefixerDaoFactory.getDao(this);
        } catch (Exception e) {
            throw new RuntimeException("Exception while loading prefixes from file", e);
        }

        listAdapter = new PrefixerListAdapter(this, dao);

        final ListView prefixerListView = (ListView) findViewById(R.id.prefixerList);
        prefixerListView.setAdapter(listAdapter);

        prefixerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.prefixerNametw);
                try {
                    PrefixerBean bean = dao.getPrefixer(textView.getText().toString(), false);
                    startPrefixerScreenActivity(bean);
                } catch (Exception ex) {
                    Log.d("PrefixerDao", "Exception while getting bean for name: " + textView.getText().toString(), ex);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prefixer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startPrefixerScreenActivity(null);
        return super.onOptionsItemSelected(item);
    }

    private void startPrefixerScreenActivity(PrefixerBean bean) {
        Intent intent = new Intent(this, PrefixerView.class);
        if(bean == null) {
            intent.putExtra(PREFIXER_SCREEN_TYPE, PrefixerScreenType.New.toString());
        } else {
            intent.putExtra(PREFIXER_SCREEN_TYPE, PrefixerScreenType.Edit.toString());
            intent.putExtra(PREFIXER_BEAN, bean);
        }

        startActivityForResult(intent, PREFIXER_RESULT_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PREFIXER_RESULT_REQ_CODE && resultCode == RESULT_OK) {
            if(TRANSACTION_TYPE_SAVE.equals(data.getExtras().getString(PREFIXER_TRANSACTION_TYPE))) {
               PrefixerBean bean = data.getExtras().getParcelable(PREFIXER_BEAN);
                try {
                    dao.savePrefixer(bean);
                    addToListView(bean);
                    Toast.makeText(this, String.format("Prefixer: %s saved successfully", bean.getName()), LENGTH_LONG).show();
                } catch (Exception ex) {
                    Log.d("Prefixer", "Exception while adding prefixer", ex);
                    Toast.makeText(this, "Failed while saving Prefixer", LENGTH_LONG).show();
                }
            } else {
                String prefixername = data.getExtras().getString(PREFIXER_NAME);
                try {
                    dao.deletePrefixer(prefixername);
                    deleteFromListView(prefixername);
                    Toast.makeText(this, String.format("Prefixer: %s deleted successfully", prefixername), LENGTH_LONG).show();
                } catch(Exception ex) {
                    Log.d("Prefixer", "Exception while deleting prefixer", ex);
                    Toast.makeText(this, "Failed while deleting Prefixer", LENGTH_LONG).show();
                }
            }
        }
    }

    private void addToListView(PrefixerBean bean) {
        if(!this.listAdapter.contains(bean.getName())) {
            this.listAdapter.add(bean);
            this.listAdapter.notifyDataSetChanged();
        }
    }

    private void deleteFromListView(String prefixerName) {
        this.listAdapter.remove(prefixerName);
        this.listAdapter.notifyDataSetChanged();
    }
}
