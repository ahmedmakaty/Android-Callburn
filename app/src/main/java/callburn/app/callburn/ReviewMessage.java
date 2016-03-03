package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import callburn.app.callburn.DataModels.Contact;
import callburn.app.callburn.DataModels.Message;

public class ReviewMessage extends AppCompatActivity {

    Toolbar toolbar;
    TextView title, duration, showGroup, textmsg;
    LinearLayout tmsg, amsg, Schedule, Send, msglay;
    List<Contact> group = new ArrayList<Contact>();
    boolean playing = false;
    boolean type;
    int length = 0;
    MediaPlayer mp;
    Message m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_message);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        textmsg = (TextView) findViewById(R.id.message);
        tmsg = (LinearLayout) findViewById(R.id.text_message);
        amsg = (LinearLayout) findViewById(R.id.audio_msg);
        duration = (TextView) findViewById(R.id.audio_duration);
        msglay = (LinearLayout) findViewById(R.id.msg_wrapper);
        showGroup = (TextView) findViewById(R.id.show_group);
        Schedule = (LinearLayout) findViewById(R.id.lay3);
        Send = (LinearLayout) findViewById(R.id.lay1);

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("Review your message");

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            m = (Message) extras.getSerializable("MESSAGE");
        } else {
            m = (Message) savedInstanceState.getSerializable("MESSAGE");
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

        showGroup.setText("3 recipients ( show " + m.getGroup() + ")");

        showGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReviewMessage.this, EditGroup.class);
                i.putExtra("GROUPNAME", m.getGroup());
                startActivity(i);
            }
        });

        Schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent i = new Intent(ReviewMessage.this, EditGroup.class);
//                i.putExtra("GROUPNAME", m.getGroup());
//                startActivity(i);
            }
        });

        Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReviewMessage.this, Congratulations.class);
                i.putExtra("MESSAGE", m);
                startActivity(i);
            }
        });

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


                            Toast.makeText(ReviewMessage.this, "Media Completed", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
