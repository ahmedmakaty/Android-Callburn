package callburn.app.callburn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.util.Collections;
import java.util.List;

import callburn.app.callburn.DataModels.Group;

/**
 * Created by Bloom on 15/1/2016.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.GroupViewHolder> {

    List<Group> groups = Collections.emptyList();
    LayoutInflater layoutInflater;
    Context context;
    boolean selected;
    int index;

    public DashboardAdapter(Context context, List<Group> groups) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.groups = groups;
    }


    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = layoutInflater.inflate(R.layout.groups_list_item, parent, false);
        GroupViewHolder holder = new GroupViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(final GroupViewHolder holder, final int position) {
        Group group = groups.get(position);
        holder.name.setText(group.getName());
//        holder.status.setText("status");
//        holder.date.setText("status");
//        holder.message.setText("status");
//        holder.image.setImageResource(R.drawable.oval);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, SendMessages.class);
                i.putExtra("GROUPNAME", groups.get(position).getName());
                context.startActivity(i);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                //Toast.makeText(v.getContext(), "OnLongClick Version :" + position, Toast.LENGTH_SHORT).show();
                holder.date.setVisibility(View.GONE);
                holder.delete.setVisibility(View.VISIBLE);

                Runnable mRunnable;
                Handler mHandler = new Handler();

                mRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        holder.delete.setVisibility(View.GONE);
                        holder.date.setVisibility(View.VISIBLE);

                    }
                };

                mHandler.postDelayed(mRunnable, 3 * 1000);

                return true;

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                groups.remove(position);
                notifyDataSetChanged();
                holder.delete.setVisibility(View.GONE);
                holder.date.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        private CircularImageView image;
        private TextView name, date, message, status, delete;

        public GroupViewHolder(View itemView) {
            super(itemView);

//            image = (CircularImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.my_name);
            delete = (TextView) itemView.findViewById(R.id.delete);
            Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
            name.setTypeface(myFont);
            date = (TextView) itemView.findViewById(R.id.date);
//            message = (TextView) itemView.findViewById(R.id.message);
//            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
