package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private Toolbar toolbar;
    TextView title, errorText;
    EditText email, password, confirmPass;
    LinearLayout error, registerBtn, faceBtn, googleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPass = (EditText) findViewById(R.id.password_repeat);
        error = (LinearLayout) findViewById(R.id.error);
        registerBtn = (LinearLayout) findViewById(R.id.register_btn);
        faceBtn = (LinearLayout) findViewById(R.id.face_btn);
        googleBtn = (LinearLayout) findViewById(R.id.google_btn);
        errorText = (TextView) findViewById(R.id.error_text);

        getSupportActionBar().setTitle("");
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        title.setText("Register");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String conf = confirmPass.getText().toString();
                if (mail.matches("") || pass.matches("") || conf.matches("")) {
                    error.setVisibility(View.VISIBLE);
                    errorText.setText("Empty fields");
                } else {
                    Toast.makeText(Register.this, mail + pass + conf, Toast.LENGTH_SHORT).show();
                }

            }
        });

        faceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
