package omkar.com.callsettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import omkar.com.callsettings.model.PrefixerBean;
import omkar.com.callsettings.model.PrefixerScreenType;

import static omkar.com.callsettings.model.Constants.PREFIXER_BEAN;
import static omkar.com.callsettings.model.Constants.PREFIXER_SCREEN_TYPE;


public class PrefixerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prefixer);

        final EditText nameTxt = (EditText) findViewById(R.id.nameTxt);
        final EditText findTxt = (EditText) findViewById(R.id.findTxt);
        final EditText replaceTxt = (EditText) findViewById(R.id.replaceTxt);

        Intent intent = getIntent();
        PrefixerScreenType screenType = Enum.valueOf(PrefixerScreenType.class, intent.getStringExtra(PREFIXER_SCREEN_TYPE));

        if (screenType == PrefixerScreenType.Edit) {
            PrefixerBean bean = (PrefixerBean) intent.getSerializableExtra(PREFIXER_BEAN);
            nameTxt.setText(bean.getName());
            findTxt.setText(bean.getStartingWith());
            replaceTxt.setText(bean.getReplaceWith());
        }

        Button saveBtn = (Button) findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefixerBean bean = new PrefixerBean();
                bean.setName(nameTxt.getText().toString());
                bean.setStartingWith(findTxt.getText().toString());
                bean.setReplaceWith(replaceTxt.getText().toString());

                Intent outIntent = new Intent();
                outIntent.putExtra(PREFIXER_BEAN, bean);

                setResult(RESULT_OK, outIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_prefixer, menu);
        return true;
    }
}