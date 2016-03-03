package callburn.app.callburn;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import callburn.app.callburn.DataModels.Contact;
import callburn.app.callburn.DataModels.Message;

public class MessageDetails extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;
    TextView textmsg;
    TextView duration;
    TextView date;
    LinearLayout tmsg;
    LinearLayout amsg;
    LinearLayout msglay;
    Message m;
    boolean type;
    MediaPlayer mp;
    ListView deli;
    ListView undeli;
    DetailsListAdapter adapter1;
    DetailsListAdapter adapter2;
    List<Contact> list1 = new ArrayList<Contact>();
    List<Contact> list2 = new ArrayList<Contact>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        title.setText("Message details");

        textmsg = (TextView) findViewById(R.id.message);
        date = (TextView) findViewById(R.id.relative_date);
        tmsg = (LinearLayout) findViewById(R.id.text_message);
        amsg = (LinearLayout) findViewById(R.id.audio_msg);
        duration = (TextView) findViewById(R.id.audio_duration);
        msglay = (LinearLayout) findViewById(R.id.msg_wrapper);
        deli = (ListView) findViewById(R.id.deli_list);
        undeli = (ListView) findViewById(R.id.undeli_list);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list1.add(new Contact());
        list1.add(new Contact());

        list2.add(new Contact());
        list2.add(new Contact());
        list2.add(new Contact());

        adapter1 = new DetailsListAdapter(this,list1);
        adapter2 = new DetailsListAdapter(this,list2);

        deli.setAdapter(adapter1);
        undeli.setAdapter(adapter2);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            m = (Message) extras.getSerializable("MESSAGE");
        } else {
            m = (Message) savedInstanceState.getSerializable("MRSSAGE");
        }

        date.setText(DateUtils.getRelativeDateTimeString(MessageDetails.this, m.getDate(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.DAY_IN_MILLIS, 0));

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
