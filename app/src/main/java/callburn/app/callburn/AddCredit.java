package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCredit extends AppCompatActivity {

    private Toolbar toolbar;
    TextView title, recharge;
    EditText amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        amount = (EditText) findViewById(R.id.amount);
        recharge = (TextView) findViewById(R.id.recharge);

        getSupportActionBar().setTitle("");
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        title.setText("Add Credit");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        amount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    Toast.makeText(v.getContext(), amount.getText().toString(),
                            Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(v.getContext(), Verify.class);
//                    i.putExtra("mobile",phone);
//                    startActivity(i);
                }
                return false;
            }
        });

        recharge.setOnClickListener(new View.OnClickListener() {

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
