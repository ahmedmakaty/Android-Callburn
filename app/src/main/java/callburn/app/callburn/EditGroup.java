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

import callburn.app.callburn.Utils.CEditText;
import callburn.app.callburn.DataModels.Contact;
import callburn.app.callburn.Utils.DataWrapper;

public class EditGroup extends AppCompatActivity {

    DataWrapper data;
    List<Contact> members = new ArrayList<Contact>();
    Toolbar toolbar;
    TextView title, text1, text2, addPhoto;
    CEditText groupName;
    String count, users;
    CircularImageView image;
    String group_name = "Temp";
    LinearLayout retarded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        members.add(new Contact("Ahmed", "+20147852"));
        members.add(new Contact("Ziz", "+2365471"));
        members.add(new Contact("Lucie", "+69852147"));

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);

        title.setText(group_name);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface myFont1 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface myFont2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        text1 = (TextView) findViewById(R.id.textView5);
        addPhoto = (TextView) findViewById(R.id.add_photo);
        image = (CircularImageView) findViewById(R.id.image);
        groupName = (CEditText) findViewById(R.id.grp_name);
        retarded = (LinearLayout) findViewById(R.id.retarded_list);

        addPhoto.setTypeface(myFont2);
        text1.setTypeface(myFont);
        title.setTypeface(myFont);

        groupName.setHint(group_name);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        count = "Group members (" + members.size() + ")";
        text1.setText(count);

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
                    Toast.makeText(EditGroup.this, v.getId() + "", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            Intent parentIntent = NavUtils.getParentActivityIntent(this);
            parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(parentIntent);
            finish();
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
