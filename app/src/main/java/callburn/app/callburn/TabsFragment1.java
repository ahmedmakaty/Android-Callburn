package callburn.app.callburn;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import callburn.app.callburn.DataModels.Contact;
import callburn.app.callburn.DataModels.Group;
import callburn.app.callburn.DataModels.Template;
import callburn.app.callburn.Utils.DataWrapper;
import callburn.app.callburn.Utils.TemplateDataWrapper;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabsFragment1 extends Fragment {

    MyCustomAdapter dataAdapter = null;
    View view;
    EditText filter;
    TextView showSelected;
    String users = "";
    List<Contact> group = new ArrayList<Contact>();
    Group g;

    public TabsFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_tabs_fragment1, container, false);

        setHasOptionsMenu(true);

        displayListView();

        showSelected = (TextView) view.findViewById(R.id.show_selected_contacts);

        //filter = (EditText) view.findViewById(R.id.myFilter);

        /*ImageSpan imageHint = new ImageSpan(getContext(), R.drawable.ic_action_action_search);
        SpannableString spannableString = new SpannableString(" Search Contacts");
        spannableString.setSpan(imageHint, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        filter.setHint(spannableString);*/

        return view;
    }

    public void setText() {
        users = "";
        if (group.size() > 0) {
            users += group.get(0).getName();
        }

        for (int i = 1; i < group.size(); i++) {
            users += ", " + group.get(i).getName();
        }
        showSelected.setText(users);
    }

    private List<Contact> getPhoneContacts() {

        List<Contact> phoneContacts = new ArrayList<Contact>();

        ContentResolver cr = getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {

            while (cur.moveToNext()) {
                Contact c = new Contact("", "");
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                c.setName(name);
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {

                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Toast.makeText(getContext(), phoneNo, Toast.LENGTH_SHORT).show();
                        c.setNumber(phoneNo);
                        if (!phoneContacts.contains(c)) {
                            phoneContacts.add(c);
                        }
                        //Toast.makeText(getContext(), "Name: " + c.getName() + ", Phone No: " + c.getNumber(), Toast.LENGTH_SHORT).show();
                    }
                    pCur.close();
                }
            }
        }
        Toast.makeText(getContext(), phoneContacts.size() + "", Toast.LENGTH_SHORT).show();
        return phoneContacts;

    }

    private void displayListView() {

        List<Contact> contacts = new ArrayList<Contact>();
        contacts = getPhoneContacts();
//        contacts.add(new Contact("Ahmed","+201017284643"));
//        contacts.add(new Contact("Ziko","+201017284643"));
//        contacts.add(new Contact("Ohi","+201017284643"));
//        contacts.add(new Contact("Brazil","+201017284643"));
//        contacts.add(new Contact("Elo","+201017284643"));

        //create an ArrayAdaptar from the String Array
//        dataAdapter = new ArrayAdapter<String>(this,
//                R.layout.contact_list_item, countryList);
//        ListView listView = (ListView) findViewById(R.id.listView1);
//        // Assign adapter to ListView
//        listView.setAdapter(dataAdapter);

        ListView listView = (ListView) view.findViewById(R.id.listView1);
        dataAdapter = new MyCustomAdapter(contacts, getContext());


        listView.setAdapter(dataAdapter);


        //enables filtering for the contents of the given ListView
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox1);
//                cb.setChecked(!cb.isChecked());
                Contact c = dataAdapter.getContact(position);

                if (cb.isChecked()) {
                    dataAdapter.UnSelectContact(c);
                    dataAdapter.notifyDataSetChanged();
                    group = dataAdapter.getSelected();
                    setText();
                } else {
                    dataAdapter.SelectContact(c);
                    dataAdapter.notifyDataSetChanged();
                    group = dataAdapter.getSelected();
                    setText();
                }

                Toast.makeText(getContext(), c.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        EditText myFilter = (EditText) view.findViewById(R.id.myFilter);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contacts_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_group) {

            if (group.size() > 0) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Some contacts haven’t got a valid international prefix")
                        .setMessage("Are they located in your same country? If you choose yes, we will call him by adding international phone prefix as your. Otherwise, you need to manually fix the problem by saving them in international format (for example +34 690112233)")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                GetContacts a = (GetContacts) getActivity();
                                String requestType = a.getRequestType();

                                if(requestType.matches("new")) {
                                    Intent i = new Intent(getContext(), CreateGroup.class);
                                    i.putExtra("REQUESTTYPE", requestType);
                                    i.putExtra("group", new DataWrapper((ArrayList<Contact>) group));
                                    startActivity(i);
                                }else{
                                    Template t = a.getTemplate();
                                    Intent i = new Intent(getContext(), CreateGroup.class);
                                    i.putExtra("REQUESTTYPE", requestType);
                                    i.putExtra("group", new DataWrapper((ArrayList<Contact>) group));
                                    i.putExtra("Template",new TemplateDataWrapper(t));
                                    startActivity(i);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Empty group")
                        .setMessage("You should choose contacts to add to your group")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}

