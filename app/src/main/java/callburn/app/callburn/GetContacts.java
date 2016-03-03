package callburn.app.callburn;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import callburn.app.callburn.DataModels.Contact;
import callburn.app.callburn.DataModels.Template;
import callburn.app.callburn.Utils.TemplateDataWrapper;

public class GetContacts extends AppCompatActivity {

    ContactsTabsAdapter adapter;
    ViewPager viewPager;
    TextView title, chosen;
    List<Contact> group;
    String requestType;
    Template t;
    TemplateDataWrapper tDW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contacts);

        AppBarLayout lay = (AppBarLayout) findViewById(R.id.app_bar);
        Toolbar toolbar = (Toolbar) lay.findViewById(R.id.app_toolbar);
        title = (TextView) lay.findViewById(R.id.toolbar_title);
        chosen = (TextView) findViewById(R.id.show_selected_contacts);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            //data = (DataWrapper) extras.getSerializable("group");
            //members = data.getContacts();
            requestType = extras.getString("REQUESTTYPE");
            if (requestType.matches("sendTemplate")) {
                tDW = (TemplateDataWrapper) extras.getSerializable("Template");
                t = tDW.getT();
            }
            Toast.makeText(GetContacts.this, requestType, Toast.LENGTH_SHORT).show();
        } else {
            requestType = savedInstanceState.getString("REQUESTTYPE");
            if (requestType.matches("sendTemplate")) {
                tDW = (TemplateDataWrapper) savedInstanceState.getSerializable("Template");
                t = tDW.getT();
            }
            //data = (DataWrapper) savedInstanceState.getSerializable("group");
            //members = data.getContacts();
        }

        viewPager = (ViewPager) findViewById(R.id.vP);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) lay.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ContactsTabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public String getRequestType() {
        return requestType;
    }

    public Template getTemplate() {
        return t;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            Intent parentIntent = NavUtils.getParentActivityIntent(this);
            parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(parentIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
