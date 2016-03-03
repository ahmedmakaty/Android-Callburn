package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import callburn.app.callburn.DataModels.Message;

public class SendMessages extends AppCompatActivity {

    private boolean isSpeakButtonLongPressed = false;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private boolean recording = false;

    String groupName = "Temp Group";
    Toolbar toolbar;
    TextView title;
    ImageButton btnSend, btnRecord;
    EditText msg;
    List<Message> Messages = new ArrayList<Message>();
    ListView convList;
    MessageAdapter adapter;
    RelativeLayout rlay;
    Chronometer timer;
    LinearLayout container, overlay;
    long elapsedMillis;
    boolean playing = false;
    MediaPlayer mp = new MediaPlayer();
    int currentTrack = 500000;
    int length = 0;
    boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_messages);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        try {
            Messages.add(new Message("Hello, i love you why are you immature goal hahaha ya noob", true, "", 0, groupName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Messages.add(new Message("Hello, oo", true, "", 0, groupName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Messages.add(new Message("Hello, nooooo", true, "", 0, groupName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout main = (LinearLayout) findViewById(R.id.message_main_layout);
        convList = (ListView) main.findViewById(R.id.message_list);
        rlay = (RelativeLayout) main.findViewById(R.id.noMessage);
        btnRecord = (ImageButton) findViewById(R.id.record);
        timer = (Chronometer) findViewById(R.id.timer);
        msg = (EditText) findViewById(R.id.send);
        container = (LinearLayout) findViewById(R.id.timer_container);


        long name = new Date().getTime();

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
                        Toast.makeText(SendMessages.this, "Start recording", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SendMessages.this, "stopped recording", Toast.LENGTH_SHORT).show();
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

        if (Messages.size() > 0) {
            rlay.setVisibility(View.GONE);
            convList.setVisibility(View.VISIBLE);
        }

        adapter = new MessageAdapter();
        convList.setAdapter(adapter);
        convList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        //convList.setStackFromBottom(true);

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        title.setText(groupName);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSend = (ImageButton) findViewById(R.id.sendbtn);

//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//
//                groupName = extras.getString("GROUPNAME");
//
//        } else {
//            groupName = (String) savedInstanceState.getSerializable("GROUPNAME");
//        }

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


        convList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message m = Messages.get(position);
                //Toast.makeText(SendMessages.this, m.getMessage(), Toast.LENGTH_SHORT).show();

                if (currentTrack != position) {
                    playing = false;
                    length = 0;
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
                }
                if (!playing) {
                    mp.seekTo(length);
                    mp.start();
                    playing = true;
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        public void onCompletion(MediaPlayer mp) {


                            Toast.makeText(SendMessages.this, "Media Completed", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(SendMessages.this, mp.getDuration() + "", Toast.LENGTH_SHORT).show();
                currentTrack = position;
            }
        });

        convList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                overlay = (LinearLayout) view.findViewById(R.id.overlay);
                overlay.setVisibility(View.VISIBLE);

                Runnable mRunnable;
                Handler mHandler = new Handler();

                mRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stu
                        overlay.setVisibility(View.GONE);

                    }
                };

                mHandler.postDelayed(mRunnable, 5 * 1000);
                return false;
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

//    private View.OnLongClickListener speakHoldListener = new View.OnLongClickListener() {
//
//        @Override
//        public boolean onLongClick(View pView) {
//            // Do something when your hold starts here.
//            try {
//                myAudioRecorder.prepare();
//                myAudioRecorder.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            isSpeakButtonLongPressed = true;
//            return true;
//        }
//    };
//
//    private View.OnTouchListener speakTouchListener = new View.OnTouchListener() {
//
//        @Override
//        public boolean onTouch(View pView, MotionEvent pEvent) {
//            pView.onTouchEvent(pEvent);
//            // We're only interested in when the button is released.
//            if (pEvent.getAction() == MotionEvent.ACTION_UP) {
//                // We're only interested in anything if our speak button is currently pressed.
//                if (isSpeakButtonLongPressed) {
//                    // Do something when the button is released.
//                    myAudioRecorder.stop();
//                    myAudioRecorder.release();
//                    myAudioRecorder  = null;
//
//                    sendAudio(outputFile);
//                    isSpeakButtonLongPressed = false;
//                }
//            }
//            return false;
//        }
//    };

    private void sendMessage(String s) throws IOException {
        Messages.add(new Message(s, true, "", 0, groupName));
        adapter.notifyDataSetChanged();
        Intent i = new Intent(SendMessages.this, ReviewMessage.class);
        i.putExtra("MESSAGE", new Message(s, true, "", 0, groupName));
        startActivity(i);
    }

    private void sendAudio(String s, long d) throws IOException {
        Messages.add(new Message("Delivery in progress...", false, s, d, groupName));
        adapter.notifyDataSetChanged();
        Intent i = new Intent(SendMessages.this, ReviewMessage.class);
        i.putExtra("MESSAGE", new Message("Delivery in progress...", false, s, d, groupName));
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
//            Intent parentIntent = NavUtils.getParentActivityIntent(this);
//            parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//            startActivity(parentIntent);
//            finish();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class MessageAdapter extends BaseAdapter {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            return Messages.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Message getItem(int position) {
            return Messages.get(position);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(final int pos, View v, ViewGroup arg2) {
            final Message m = getItem(pos);
            if (m.isText()) {
                v = getLayoutInflater().inflate(R.layout.text_message_item, null);
                TextView lbl = (TextView) v.findViewById(R.id.relative_date);
                lbl.setText(DateUtils.getRelativeDateTimeString(SendMessages.this, m.getDate(), DateUtils.SECOND_IN_MILLIS,
                        DateUtils.DAY_IN_MILLIS, 0));

                lbl = (TextView) v.findViewById(R.id.message);
                lbl.setText(m.getMessage());

                Toast.makeText(SendMessages.this, "a7a", Toast.LENGTH_SHORT).show();

//                lbl = (TextView) v.findViewById(R.id.status);
//                lbl.setText(m.getStatus());

//                lbl = (TextView) v.findViewById(R.id.info);
//                lbl.setText("- " + m.getPrice());
            } else {


                v = getLayoutInflater().inflate(R.layout.audio_message_item, null);
                TextView lbl = (TextView) v.findViewById(R.id.relative_date);
                lbl.setText(DateUtils.getRelativeDateTimeString(SendMessages.this, m.getDate(), DateUtils.SECOND_IN_MILLIS,
                        DateUtils.DAY_IN_MILLIS, 0));

//                lbl = (TextView) v.findViewById(R.id.status);
//                lbl.setText(m.getStatus());

                int duration = (int) m.getDuration();

                //Toast.makeText(SendMessages.this, duration+"", Toast.LENGTH_SHORT).show();
                lbl = (TextView) v.findViewById(R.id.duration);

                int seconds = duration / 1000;
                int sec = seconds % 60;
                int min = seconds / 60;
                lbl.setText(String.format("%02d", min) + ":" + String.format("%02d", sec));

//                lbl = (TextView) v.findViewById(R.id.info);
//                lbl.setText("- " + m.getPrice());
            }
            TextView details = (TextView) v.findViewById(R.id.details);
            TextView sendAgain = (TextView) v.findViewById(R.id.send_again);
            TextView delete = (TextView) v.findViewById(R.id.delete);

            details.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SendMessages.this, MessageDetails.class);
                    i.putExtra("MESSAGE", m);
                    startActivity(i);
                }
            });

            sendAgain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(SendMessages.this, "send", Toast.LENGTH_SHORT).show();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Messages.remove(pos);
                    notifyDataSetChanged();
                    Toast.makeText(SendMessages.this, "delete", Toast.LENGTH_SHORT).show();
                }
            });


            return v;
        }

    }
}
