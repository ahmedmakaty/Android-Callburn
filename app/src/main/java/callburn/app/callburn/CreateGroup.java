package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import callburn.app.callburn.DataModels.Message;
import callburn.app.callburn.DataModels.Template;
import callburn.app.callburn.Utils.CEditText;
import callburn.app.callburn.DataModels.Contact;
import callburn.app.callburn.Utils.DataWrapper;
import callburn.app.callburn.Utils.TemplateDataWrapper;

public class CreateGroup extends AppCompatActivity {

    DataWrapper data;
    List<Contact> members = new ArrayList<Contact>();
    Toolbar toolbar;
    TextView title, text1, text2;
    CEditText groupName;
    TextView addPhoto;
    String count, users;
    CircularImageView image;
    String group_name;
    LinearLayout retarded;
    String requestType;
    Template t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            data = (DataWrapper) extras.getSerializable("group");
            requestType = extras.getString("REQUESTTYPE");
            if (requestType.matches("sendTemplate")) {
                TemplateDataWrapper tdw = (TemplateDataWrapper) extras.getSerializable("Template");
                t = tdw.getT();
            }
            members = data.getContacts();
        } else {
            data = (DataWrapper) savedInstanceState.getSerializable("group");
            requestType = savedInstanceState.getString("REQUESTTYPE");
            members = data.getContacts();
            if (requestType.matches("sendTemplate")) {
                TemplateDataWrapper tdw = (TemplateDataWrapper) savedInstanceState.getSerializable("Template");
                t = tdw.getT();
            }
        }

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);

        title.setText("Choose group name");

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface myFont1 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface myFont2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        text1 = (TextView) findViewById(R.id.textView5);
        text2 = (TextView) findViewById(R.id.textView6);
        addPhoto = (TextView) findViewById(R.id.add_photo);
        image = (CircularImageView) findViewById(R.id.image);
        groupName = (CEditText) findViewById(R.id.grp_name);
        retarded = (LinearLayout) findViewById(R.id.retarded_list);

        addPhoto.setTypeface(myFont2);
        text1.setTypeface(myFont);
        text2.setTypeface(myFont1);
        title.setTypeface(myFont);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        count = "Group members (" + members.size() + ")";
        text1.setText(count);

        users = "";
        if (members.size() > 0) {
            users += members.get(0).getName();
        }

        for (int i = 1; i < members.size(); i++) {
            users += ", " + members.get(i).getName();
        }
        text2.setText(users);


        for (Contact c : members) {

            TextView mTextView = new TextView(this);
            mTextView.setText(c.getName());
            mTextView.setId(members.indexOf(c));
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(15, 2, 0, 10);
            mTextView.setPadding(10, 10, 10, 10);
            mTextView.setBackgroundResource(R.drawable.border);
            mTextView.setLayoutParams(llp);
            retarded.addView(mTextView);

            mTextView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(CreateGroup.this, v.getId() + "", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                    return false;
                }
            });
        }

        addPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_group, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            Intent parentIntent = NavUtils.getParentActivityIntent(this);
            parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(parentIntent);
            finish();
        }
        if (item.getItemId() == R.id.save_group) {

            if (requestType.matches("new")) {

                group_name = groupName.getText() + "";
                Intent i = new Intent(this, SendMessages.class);
                i.putExtra("GROUPNAME", group_name);
                startActivity(i);
            } else {
                group_name = groupName.getText() + "";
                Intent i = new Intent(this, ReviewMessage.class);
                i.putExtra("GROUPNAME", group_name);
                Message m = null;
                try {
                    m = new Message("Hello", true, "", 0, group_name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //m.setMessage(t.getMessage());
                i.putExtra("MESSAGE", m);
                startActivity(i);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri chosenImageUri = data.getData();

            Bitmap mBitmap = null;
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                image.setImageBitmap(mBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
