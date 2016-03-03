package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import callburn.app.callburn.Utils.CEditText;

public class Verify extends AppCompatActivity {

    private Toolbar toolbar;
    TextView text1, text2, text3, text4;
    String number;
    CEditText vrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        text1 = (TextView) toolbar.findViewById(R.id.toolbar_title);
        text2 = (TextView) toolbar.findViewById(R.id.phone);
        text3 = (TextView) findViewById(R.id.textView3);
        text4 = (TextView) findViewById(R.id.textView4);
        vrCode = (CEditText) findViewById(R.id.verifyCode);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface myFont1 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        text1.setTypeface(myFont);
        text2.setTypeface(myFont);
        text3.setTypeface(myFont1);
        text4.setTypeface(myFont1);

        vrCode.setHint("Verification Code");

        getSupportActionBar().setTitle("");
        text1.setText("Sending a voice call to");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                number = null;
            } else {
                number = extras.getString("mobile");
            }
        } else {
            number = (String) savedInstanceState.getSerializable("mobile");
        }

        text2.setText(number);

        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
            }
        });

        vrCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(v.getContext(), vrCode.getText(),
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Verify.this, MainActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });
    }
}
