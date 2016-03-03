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

public class Login extends AppCompatActivity {

    private Toolbar toolbar;
    TextView title, recover, register;
    EditText username, password;
    LinearLayout loginBtn, faceBtn, googleBtn, error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        recover = (TextView) findViewById(R.id.recover);
        register = (TextView) findViewById(R.id.register);
        loginBtn = (LinearLayout) findViewById(R.id.login_btn);
        faceBtn = (LinearLayout) findViewById(R.id.face_btn);
        error = (LinearLayout) findViewById(R.id.error);
        googleBtn = (LinearLayout) findViewById(R.id.google_btn);

        getSupportActionBar().setTitle("");
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        title.setText("Login");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                Toast.makeText(Login.this, name + pass, Toast.LENGTH_SHORT).show();
                if (true) {
                    error.setVisibility(View.VISIBLE);
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
