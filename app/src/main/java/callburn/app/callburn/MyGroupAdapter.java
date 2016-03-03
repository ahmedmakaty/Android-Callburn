package callburn.app.callburn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by Bloom on 15/1/2016.
 */
class MyGroupAdapter extends ArrayAdapter<String> {

    private List<String> groups;
    private Context context;

    public MyGroupAdapter(Context context, List<String> groups) {
        super(context, R.layout.item , groups);
        this.context = context;
        this.groups = groups;
    }
        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */


    @Override
    public int getCount() {
        return groups.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int pos, View v, ViewGroup parent) {


        ViewHolder holder = new ViewHolder();

        Toast.makeText(getContext(), parent.getChildCount()+"", Toast.LENGTH_SHORT).show();
        if(v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item, parent, false);
            TextView name = (TextView) v.findViewById(R.id.my_name);
//            TextView message = (TextView) v.findViewById(R.id.message);
//            TextView status = (TextView) v.findViewById(R.id.status);
//            TextView date = (TextView) v.findViewById(R.id.date);
//            CircularImageView image = (CircularImageView) v.findViewById(R.id.image);

            holder.name = name;
//            holder.message = message;
//            holder.status = status;
//            holder.date = date;
//            holder.image = image;

            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();

            String g = groups.get(pos);

            holder.name.setText(g);
        }

        return v;
    }
}

class ViewHolder {
    TextView name;
    TextView status;
    TextView date;
    TextView message;
    CircularImageView image;
}
