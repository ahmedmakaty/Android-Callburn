package callburn.app.callburn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    public static final String PREF_FILE_NAME = "callburn.app.callburn.preferences";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_FIRST_TIME = "first_time";
    public static final String KEY_LOGGED_USER = "logged_user";
    public static final String KEY_USER_BALANCE = "user_balance";
    public static final String KEY_TTS_MODE = "tts_mode";
    public static final String KEY_NEWS_MODE = "news_mode";
    public static final String KEY_NOTIF_MODE = "notification_mode";
    public static final String KEY_USER_COUNTRY = "user_country";
    public static final String KEY_COUNTRY_CODE = "country_code";
    public static final String KEY_MOBILE_NUMBER = "mobile_number";
    public static final String KEY_USER_EMAIL = "user_email";

    boolean firstTime;
    boolean logged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        firstTime = Boolean.valueOf(readFromPreferences(this, KEY_FIRST_TIME, "true"));

        if(firstTime){
            Intent i = new Intent(this,IntroActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
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
