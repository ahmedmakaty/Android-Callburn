package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import callburn.app.callburn.DataModels.Message;

public class Congratulations extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;
    LinearLayout tmsg, amsg, msglay;
    Message m;
    boolean type;
    MediaPlayer mp;
    TextView textmsg, duration;
    boolean playing;
    int length;
    ImageView dash, temp;
    TextView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tmsg = (LinearLayout) findViewById(R.id.text_message);
        amsg = (LinearLayout) findViewById(R.id.audio_msg);
        msglay = (LinearLayout) findViewById(R.id.msg_wrapper);
        textmsg = (TextView) findViewById(R.id.message);
        duration = (TextView) findViewById(R.id.audio_duration);
        dash = (ImageView) findViewById(R.id.dash);
        temp = (ImageView) findViewById(R.id.temp);
        share = (TextView) findViewById(R.id.share);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        title.setText("Message Sent");

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


                            Toast.makeText(Congratulations.this, "Media Completed", Toast.LENGTH_SHORT).show();
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

        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Congratulations.this, MainActivity.class);
                startActivity(i);
            }
        });
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Congratulations.this, MainActivity.class);
                startActivity(i);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Share
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_message_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.done) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
