package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

import callburn.app.callburn.DataModels.Message;

public class AddTemplate extends AppCompatActivity {

    private boolean isSpeakButtonLongPressed = false;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private boolean recording = false;

    Toolbar toolbar;
    TextView title;
    ImageButton btnSend, btnRecord;
    EditText msg;
    Chronometer timer;
    LinearLayout container;
    long elapsedMillis;
    boolean playing = false;
    MediaPlayer mp = new MediaPlayer();
    int currentTrack = 500000;
    int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        btnRecord = (ImageButton) findViewById(R.id.record);
        timer = (Chronometer) findViewById(R.id.timer);
        msg = (EditText) findViewById(R.id.send);
        long name = new Date().getTime();
        btnSend = (ImageButton) findViewById(R.id.sendbtn);
        container = (LinearLayout) findViewById(R.id.timer_container);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        title.setText("New Template");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRecord.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View pView) {
                // Do something when your hold starts here.
                try {
                    if (!recording) {
                        recording = true;
                        isSpeakButtonLongPressed = true;
                        msg.setVisibility(View.GONE);
                        container.setVisibility(View.VISIBLE);
                        timer.setBase(SystemClock.elapsedRealtime());
                        timer.start();
                        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + "recording.3gp";
                        setRecorderSettings();
                        Toast.makeText(AddTemplate.this, "Start recording", Toast.LENGTH_SHORT).show();
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                recording = true;
                isSpeakButtonLongPressed = true;
                return true;
            }
        });

        btnRecord.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View pView, MotionEvent pEvent) {
                pView.onTouchEvent(pEvent);
                // We're only interested in when the button is released.
                if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                    // We're only interested in anything if our speak button is currently pressed.
                    if (isSpeakButtonLongPressed) {
                        // Do something when the button is released.
                        if (recording) {
                            Toast.makeText(AddTemplate.this, "stopped recording", Toast.LENGTH_SHORT).show();
                            myAudioRecorder.stop();
                            myAudioRecorder.release();
                            timer.stop();
                            elapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
                            container.setVisibility(View.GONE);
                            msg.setVisibility(View.VISIBLE);
                            myAudioRecorder = null;
                            setRecorderSettings();
                            recording = false;
                        }
                        try {
                            sendAudio(outputFile, elapsedMillis);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        isSpeakButtonLongPressed = false;
                    }
                }
                return false;
            }
        });

        final EditText send = (EditText) findViewById(R.id.send);
        send.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (msg.getText() + "" == "") {
                    //Toast.makeText(SendMessages.this, msg.getText().toString(), Toast.LENGTH_SHORT).show();
                    btnRecord.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                } else {
                    btnRecord.setVisibility(View.GONE);
                    btnSend.setVisibility(View.VISIBLE);
                }


            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (send.getText() + "" != "") {
                    try {
                        sendMessage(send.getText() + "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    send.setText(null);
                }
            }
        });
    }

    private void setRecorderSettings() {
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
    }

    private void sendMessage(String s) throws IOException {

        Intent i = new Intent(AddTemplate.this, SaveTemplate.class);
        i.putExtra("REQUESTTYPE", "new");
        i.putExtra("MESSAGE", new Message(s, true, "", 0, "group"));
        startActivity(i);
    }

    private void sendAudio(String s, long d) throws IOException {

        Intent i = new Intent(AddTemplate.this, SaveTemplate.class);
        i.putExtra("REQUESTTYPE", "new");
        i.putExtra("MESSAGE", new Message("Delivery in progress...", false, s, d, "group"));
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            Intent parentIntent = NavUtils.getParentActivityIntent(this);
            parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(parentIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
