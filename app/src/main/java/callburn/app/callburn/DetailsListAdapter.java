package callburn.app.callburn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import callburn.app.callburn.DataModels.Contact;

/**
 * Created by Bloom on 19/1/2016.
 */
public class DetailsListAdapter extends ArrayAdapter<Contact> {
    List<Contact> list;
    Context context;

    public DetailsListAdapter(Context context, List<Contact> list) {
        super(context,R.layout.delivery_item,list);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.delivery_item, parent, false);
        return convertView;
    }
}
