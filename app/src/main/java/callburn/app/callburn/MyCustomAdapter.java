package callburn.app.callburn;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import callburn.app.callburn.DataModels.Contact;

public class MyCustomAdapter extends ArrayAdapter<Contact> implements Filterable {

    private List<Contact> contactList;
    private List<Contact> orgList;
    private Context context;

    public MyCustomAdapter(List<Contact> contactList, Context ctx) {
        super(ctx, R.layout.contact_list_item, contactList);
        this.contactList = contactList;
        sort();
        this.orgList = contactList;
        this.context = ctx;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);
        }
        // Now we can fill the layout with the right values
        TextView name = (TextView) convertView.findViewById(R.id.name);
        //TextView number = (TextView) convertView.findViewById(R.id.number);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);

        Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        name.setTypeface(myFont);

        Contact c = contactList.get(position);

        name.setText(c.getName() + "  (" + c.getNumber() + ")");
        cb.setChecked(c.isChecked());
        //number.setText(c.getNumber());


        return convertView;
    }

    public void SelectContact(Contact c){
        int position = contactList.indexOf(c);
        contactList.get(position).setChecked(true);
    }

    public void UnSelectContact(Contact c){
        int position = contactList.indexOf(c);
        contactList.get(position).setChecked(false);
    }

    public List<Contact> getSelected(){

        List<Contact> selected = new ArrayList<Contact>();
        for (Contact c : orgList){
                if(c.isChecked()){
                    selected.add(c);
                }
        }
        return selected;
    }

    public Contact getContact(int position){
        return contactList.get(position);
    }

    public void sort(){
        Collections.sort(this.contactList, new Comparator<Contact>() {
            @Override
            public int compare(final Contact object1, final Contact object2) {
                return object1.getName().compareTo(object2.getName());
            }
        });
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // We implement here the filter logic
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = orgList;
                    results.count = orgList.size();
                }
                else {
                    // We perform filtering operation
                    List<Contact> nContactList = new ArrayList<Contact>();

                    for (Contact c : contactList) {
                        if (c.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                            nContactList.add(c);
                    }

                    results.values = nContactList;
                    results.count = nContactList.size();

                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count == 0) {
                    contactList = (List<Contact>) results.values;
                    notifyDataSetInvalidated();
                    Toast.makeText(getContext(), contactList.size() + "", Toast.LENGTH_SHORT).show();

                }else {
                    contactList = (List<Contact>) results.values;
                    notifyDataSetChanged();
                    Toast.makeText(getContext(),
                            contactList.size() +"", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

}
