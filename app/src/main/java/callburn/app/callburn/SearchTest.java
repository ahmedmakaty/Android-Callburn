package callburn.app.callburn;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import callburn.app.callburn.DataModels.Contact;

public class SearchTest extends Activity {

    MyCustomAdapter dataAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_test);

        //Generate list View from ArrayList
        displayListView();

    }

    private void displayListView() {

        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(new Contact("ahmed","+201017284643"));
        contacts.add(new Contact("ziko","+201017284643"));
        contacts.add(new Contact("ohi","+201017284643"));
        contacts.add(new Contact("brazil","+201017284643"));
        contacts.add(new Contact("elo","+201017284643"));

        //create an ArrayAdaptar from the String Array
//        dataAdapter = new ArrayAdapter<String>(this,
//                R.layout.contact_list_item, countryList);
//        ListView listView = (ListView) findViewById(R.id.listView1);
//        // Assign adapter to ListView
//        listView.setAdapter(dataAdapter);

        dataAdapter = new MyCustomAdapter(contacts,this);
        ListView listView = (ListView) findViewById(R.id.listView1);

        listView.setAdapter(dataAdapter);


        //enables filtering for the contents of the given ListView
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text

                Contact c = dataAdapter.getContact(position);
                Toast.makeText(getApplicationContext(), c.getName() , Toast.LENGTH_SHORT).show();
            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });
    }
}


