package callburn.app.callburn;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends android.support.v4.app.Fragment {

    public static final String PREF_FILE_NAME = "callburn.app.callburn.preferences";
    public static final String KEY_LOGGED_USER = "logged_user";
    public static final String KEY_USER_BALANCE = "user_balance";
    public static final String KEY_TTS_MODE = "tts_mode";
    public static final String KEY_NEWS_MODE = "news_mode";
    public static final String KEY_NOTIF_MODE = "notification_mode";
    Spinner spinner, spinner2, spinner3;
    String[] ttsOpts, newsOpts, notifOpts;
    LinearLayout signed, notSigned, export;
    TextView logout, link, delete, balanceTV;
    String ttsEngine, news, notificationMode, balance;
    boolean logged;

    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        logged = Boolean.valueOf(readFromPreferences(getContext(), KEY_LOGGED_USER, "false"));
        ttsEngine = readFromPreferences(getContext(), KEY_TTS_MODE, "Diana - US Engine");
        news = readFromPreferences(getContext(), KEY_NEWS_MODE, "No");
        notificationMode = readFromPreferences(getContext(), KEY_NOTIF_MODE, "Only push");
        balance = readFromPreferences(getContext(), KEY_USER_BALANCE, "00.00 $");

        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        spinner3 = (Spinner) view.findViewById(R.id.spinner3);
        signed = (LinearLayout) view.findViewById(R.id.signed);
        notSigned = (LinearLayout) view.findViewById(R.id.notsigned);
        export = (LinearLayout) view.findViewById(R.id.export);
        logout = (TextView) view.findViewById(R.id.logout);
        link = (TextView) view.findViewById(R.id.link);
        delete = (TextView) view.findViewById(R.id.delete);
        balanceTV = (TextView) view.findViewById(R.id.balance);

        ttsOpts = new String[]{"Diana - US Engine", "Janna - US Engine", "Syndra - US Engine", "Tristana - UK Engine"};
        newsOpts = new String[]{"Yes", "No"};
        notifOpts = new String[]{"Only push", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ttsOpts);
        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, newsOpts);
        spinner2.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, notifOpts);
        spinner3.setAdapter(adapter3);

        int index = Arrays.asList(ttsOpts).indexOf(ttsEngine);

        spinner.setSelection(index);

        int index1 = Arrays.asList(newsOpts).indexOf(news);

        spinner2.setSelection(index1);

        int index2 = Arrays.asList(notifOpts).indexOf(notificationMode);

        spinner3.setSelection(index2);

        balanceTV.setText(balance);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mode = ttsOpts[position];
                saveToPreferences(getContext(), KEY_TTS_MODE, mode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String option = newsOpts[position];
                saveToPreferences(getContext(), KEY_NEWS_MODE, option);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opt = notifOpts[position];
                saveToPreferences(getContext(), KEY_NOTIF_MODE, opt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        export.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        if (logged) {
            notSigned.setVisibility(View.GONE);
            signed.setVisibility(View.VISIBLE);
        } else {
            notSigned.setVisibility(View.VISIBLE);
            signed.setVisibility(View.GONE);
        }

        link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Do you want to export your device phonebook into Callburn account?")
                        .setMessage("They will be available in every device logged in with your Callburn account")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                notSigned.setVisibility(View.GONE);
                                signed.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Are you sure?")
                        .setMessage("Yaaaaaaaad")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                notSigned.setVisibility(View.GONE);
                                signed.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //txt.setTypeface(txt.getTypeface(), Typeface.BOLD);
        super.onViewCreated(view, savedInstanceState);
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
