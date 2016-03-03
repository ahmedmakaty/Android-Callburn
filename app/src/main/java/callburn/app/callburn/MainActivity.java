package callburn.app.callburn;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREF_FILE_NAME = "callburn.app.callburn.preferences";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_FIRST_TIME = "first_time";
    public static final String KEY_LOGGED_USER = "logged_user";
    public static final String KEY_USER_BALANCE = "user_balance";
    public static final String KEY_TTS_MODE = "tts_mode";
    public static final String KEY_NEWS_MODE = "news_mode";
    public static final String KEY_NOTIF_MODE = "notification_mode";
    public static final String KEY_USER_EMAIL = "user_email";
    private Toolbar toolbar;
    private NavigationView navDrawer;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedItem;

    TextView mTitle, not1, login, in1, in2, balanceText, logout;
    boolean first, logged;
    String balance, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveToPreferences(this, KEY_FIRST_TIME, "false");
        balance = readFromPreferences(this, KEY_USER_BALANCE, "0$");
        logged = Boolean.valueOf(readFromPreferences(this, KEY_LOGGED_USER, "false"));
        email = readFromPreferences(this, KEY_USER_EMAIL, "test@callburn.com");

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        mTitle.setTypeface(myFont);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        //drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        navDrawer = (NavigationView) findViewById(R.id.menu_drawer);
        View headerview = navDrawer.getHeaderView(0);
        login = (TextView) headerview.findViewById(R.id.login);
        not1 = (TextView) headerview.findViewById(R.id.header_text);
        in1 = (TextView) headerview.findViewById(R.id.header_text1);
        in2 = (TextView) headerview.findViewById(R.id.header_text2);
        balanceText = (TextView) headerview.findViewById(R.id.balance);
        logout = (TextView) headerview.findViewById(R.id.logout);

        navDrawer.setItemIconTintList(null);

        navDrawer.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        selectedItem = savedInstanceState == null ? R.id.nav_item_1 : savedInstanceState.getInt("selectedItem");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        Fragment dashboard = new DashBoard();
//        fragmentTransaction.replace(R.id.containerView, dashboard);
//        fragmentTransaction.commit();
        getSupportActionBar().setTitle("");
//        mTitle.setText("Dashboard");

        switch (selectedItem) {
            case R.id.nav_item_1:
                Fragment dashboard = new DashBoard();
                fragmentTransaction.replace(R.id.containerView, dashboard);
                fragmentTransaction.commit();
                mTitle.setText("Dashboard");
                // toolbar.inflateMenu(R.menu.dashboard_menu);
                break;
            case R.id.nav_item_2:
                Fragment templates = new Templates();
                fragmentTransaction.replace(R.id.containerView, templates);
                fragmentTransaction.commit();
                mTitle.setText("Templates");
                // toolbar.inflateMenu(R.menu.menu_main);
                break;
            case R.id.nav_item_3:
                Fragment priceCalculator = new PriceCalculator();
                fragmentTransaction.replace(R.id.containerView, priceCalculator);
                fragmentTransaction.commit();
                mTitle.setText("Price Calculator");
                break;
            case R.id.nav_item_4:
                Fragment settings = new Settings();
                fragmentTransaction.replace(R.id.containerView, settings);
                fragmentTransaction.commit();
                mTitle.setText("Settings");
                break;
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        if (logged) {
            in1.setVisibility(View.VISIBLE);
            in2.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);

            in2.setText(email);
            balanceText.setText(balance);
        } else {
            balanceText.setText(balance);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToPreferences(MainActivity.this, KEY_LOGGED_USER, "false");
                finish();
                startActivity(getIntent());
            }
        });

        //toolbar.inflateMenu(R.menu.dashboard_menu);
        //toolbar.destroyDrawingCache();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        selectedItem = menuItem.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (selectedItem) {
            case R.id.nav_item_1:
                Fragment dashboard = new DashBoard();
                fragmentTransaction.replace(R.id.containerView, dashboard);
                fragmentTransaction.commit();
                mTitle.setText("Dashboard");
                // toolbar.inflateMenu(R.menu.dashboard_menu);
                break;
            case R.id.nav_item_2:
                Fragment templates = new Templates();
                fragmentTransaction.replace(R.id.containerView, templates);
                fragmentTransaction.commit();
                mTitle.setText("Templates");
                // toolbar.inflateMenu(R.menu.menu_main);
                break;
            case R.id.nav_item_3:
                Fragment priceCalculator = new PriceCalculator();
                fragmentTransaction.replace(R.id.containerView, priceCalculator);
                fragmentTransaction.commit();
                mTitle.setText("Price Calculator");
                break;
            case R.id.nav_item_4:
                Fragment settings = new Settings();
                fragmentTransaction.replace(R.id.containerView, settings);
                fragmentTransaction.commit();
                mTitle.setText("Settings");
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//
//    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("selectedItem", selectedItem);
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
