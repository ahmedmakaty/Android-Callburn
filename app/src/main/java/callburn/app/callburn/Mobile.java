package callburn.app.callburn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import callburn.app.callburn.Utils.CEditText;

public class Mobile extends AppCompatActivity {

    public static final String PREF_FILE_NAME = "callburn.app.callburn.preferences";
    public static final String KEY_USER_COUNTRY = "user_country";
    public static final String KEY_COUNTRY_CODE = "country_code";
    public static final String KEY_MOBILE_NUMBER = "mobile_number";
    private Toolbar toolbar;
    CEditText code, number;
    String[] countryCode, countries;
    String phone, cod, country;
    TextView title, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        text = (TextView) findViewById(R.id.textView2);

        getSupportActionBar().setTitle("");
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        title.setTypeface(myFont);

        Typeface myFont1 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface myFont2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        text.setTypeface(myFont2);

        title.setText("Verify your Caller ID");

        Spinner dropdown = (Spinner) findViewById(R.id.spinner);

        countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",

                "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",

                "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",

                "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",

                "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei", "Bulgaria",

                "Burkina Faso", "Burma (Myanmar)", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",

                "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island",

                "Cocos (Keeling) Islands", "Colombia", "Comoros", "Cook Islands", "Costa Rica",

                "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo",

                "Denmark", "Djibouti", "Dominica", "Dominican Republic",

                "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",

                "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia",

                "Gabon", "Gambia", "Gaza Strip", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",

                "Greenland", "Grenada", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",

                "Haiti", "Holy See (Vatican City)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",

                "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica",

                "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait",

                "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",

                "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia",

                "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mayotte", "Mexico",

                "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco",

                "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",

                "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea",

                "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama",

                "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",

                "Portugal", "Puerto Rico", "Qatar", "Republic of the Congo", "Romania", "Russia", "Rwanda",

                "Saint Barthelemy", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin",

                "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino",

                "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",

                "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea",

                "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland",

                "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau",

                "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",

                "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "US Virgin Islands", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",

                "Wallis and Futuna", "West Bank", "Yemen", "Zambia", "Zimbabwe"};

        countryCode = new String[]{"+93", "+355", "+213", "+1 684", "+376", "+244", "+1 264", "+672", "+1 268", "+54", "+374",

                "+297", "+61", "+43", "+994", "+1 242", "+973", "+880", "+1 246", "+375", "+32", "+501",

                "+229", "+1 441", "+975", "+591", "+387", "+267", "+55", "+246", "+1 284", "+673", "+359",

                "+226", "+95", "+257", "+855", "+237", "+1", "+238", "+1 345", "+236", "+235", "+56", "+86",

                "+61", "+891", "+57", "+269", "+682", "+506", "+385", "+53", "+357", "+420", "+243", "+45",

                "+253", "+1 767", "+1 809", "+593", "+20", "+503", "+240", "+291", "+372",

                "+251", "+500", "+298", "+679", "+358", "+33", "+689", "+241", "+220", "+970", "+995", "+49",

                "+233", "+350", "+30", "+299", "+1 473", "+1 671", "+502", "+224", "+245", "+592", "+509",

                "+379", "+504", "+852", "+36", "+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972",

                "+39", "+225", "+1 876", "+81", "+44", "+962", "+7", "+254", "+686", "+381", "+965", "+996",

                "+856", "+371", "+961", "+266", "+231", "+218", "+423", "+370", "+352", "+853", "+389",

                "+261", "+265", "+60", "+960", "+223", "+356", "+692", "+222", "+230", "+262", "+52", "+691",

                "+373", "+377", "+976", "+382", "+1 664", "+212", "+258", "+264", "+674", "+977", "+31",

                "+599", "+687", "+64", "+505", "+227", "+234", "+683", "+672", "+850", "+1 670", "+47",

                "+968", "+92", "+680", "+507", "+675", "+595", "+51", "+63", "+870", "+48", "+351", "+1",

                "+974", "+242", "+40", "+7", "+250", "+590", "+290", "+1 869", "+1 758", "+1 599", "+508",

                "+1 784", "+685", "+378", "+239", "+966", "+221", "+381", "+248", "+232", "+65", "+421",

                "+386", "+677", "+252", "+27", "+82", "+34", "+94", "+249", "+597", "+268", "+46", "+41",

                "+963", "+886", "+992", "+255", "+66", "+670", "+228", "+690", "+676", "+1 868", "+216",

                "+90", "+993", "+1 649", "+688", "+256", "+380", "+971", "+44", "+1", "+598", "+1 340",

                "+998", "+678", "+58", "+84", "+681", "+970", "+967", "+260", "+263"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        dropdown.setAdapter(adapter);

        country = dropdown.getSelectedItem().toString();

        int index = Arrays.asList(countries).indexOf(country);

        code = (CEditText) findViewById(R.id.code);
        number = (CEditText) findViewById(R.id.mobile);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                code.setText(countryCode[position]);
                cod = countryCode[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                code.setText(countryCode[0]);
            }
        });

        number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    phone = cod + number.getText();
                    Toast.makeText(v.getContext(), phone,
                            Toast.LENGTH_SHORT).show();

                    saveToPreferences(Mobile.this, KEY_USER_COUNTRY, country);
                    saveToPreferences(Mobile.this, KEY_COUNTRY_CODE, cod);
                    saveToPreferences(Mobile.this, KEY_MOBILE_NUMBER, phone);

                    Intent i = new Intent(v.getContext(), Verify.class);
                    i.putExtra("mobile", phone);
                    startActivity(i);
                }
                return false;
            }
        });
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
