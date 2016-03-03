package callburn.app.callburn;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import callburn.app.callburn.DataModels.Message;
import callburn.app.callburn.DataModels.Template;

public class SaveTemplate extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;
    Template template = new Template();
    LinearLayout tmsg;
    LinearLayout amsg;
    LinearLayout msglay;
    LinearLayout saveIt;
    Message m;
    boolean type;
    MediaPlayer mp;
    TextView textmsg;
    TextView duration;
    boolean playing;
    int length;
    String requestType;

    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_template);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tmsg = (LinearLayout) findViewById(R.id.text_message);
        amsg = (LinearLayout) findViewById(R.id.audio_msg);
        saveIt = (LinearLayout) findViewById(R.id.save_it);
        msglay = (LinearLayout) findViewById(R.id.msg_wrapper);
        textmsg = (TextView) findViewById(R.id.message);
        duration = (TextView) findViewById(R.id.audio_duration);
        name = (EditText) findViewById(R.id.temp_name);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        title.setText("Save Template");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            requestType = extras.getString("REQUESTTYPE");

            if(requestType.matches("new")) {
                m = (Message) extras.getSerializable("MESSAGE");
            }else if(requestType.matches("edit")){
                Template t = (Template) extras.getSerializable("Template");
                try {
                    m = new Message("Hello", true, "",0,"name");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Template t = (Template) extras.getSerializable("Template");
                try {
                    m = new Message("Hello", true, "",0,"name");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {

            requestType = savedInstanceState.getString("REQUESTTYPE");
            if(requestType.matches("new")) {
                m = (Message) savedInstanceState.getSerializable("MESSAGE");
            }else if(requestType.matches("edit")){
                Template t = (Template) savedInstanceState.getSerializable("Template");
                try {
                    m = new Message("Hello", true, "",0,"name");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Template t = (Template) savedInstanceState.getSerializable("Template");
                try {
                    m = new Message("Hello", true, "",0,"name");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        type = m.isText();

        if (type) {
            textmsg.setText(m.getMessage());
        } else {

            mp = new MediaPlayer();
            try {
                mp.setDataSource(m.getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            tmsg.setVisibility(View.GONE);
            amsg.setVisibility(View.VISIBLE);

            int dur = (int) m.getDuration();

            int seconds = dur / 1000;
            int sec = seconds % 60;
            int min = seconds / 60;
            duration.setText(String.format("%02d", min) + ":" + String.format("%02d", sec));
        }

        msglay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(SendMessages.this, m.getMessage(), Toast.LENGTH_SHORT).show();

                if (!playing) {
                    mp.seekTo(length);
                    mp.start();
                    playing = true;
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        public void onCompletion(MediaPlayer mp) {


                            Toast.makeText(SaveTemplate.this, "Media Completed", Toast.LENGTH_SHORT).show();
                            playing = false;
                            mp.seekTo(0);
                            length = 0;
                        }
                    });
                } else {
                    //mp.stop();
                    mp.pause();
                    length = mp.getCurrentPosition();
                    playing = false;
                }
            }
        });

        saveIt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                template.setName(name.getText().toString());
                if(requestType.matches("new")) {
                    Toast.makeText(SaveTemplate.this, requestType, Toast.LENGTH_SHORT).show();
                }else if(requestType.matches("edit")){
                    Toast.makeText(SaveTemplate.this, requestType, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SaveTemplate.this, requestType, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
//            Intent parentIntent = NavUtils.getParentActivityIntent(this);
//            parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(parentIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
