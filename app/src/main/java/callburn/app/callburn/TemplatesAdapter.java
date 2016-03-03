package callburn.app.callburn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;

import java.util.Collections;
import java.util.List;

import callburn.app.callburn.DataModels.Template;
import callburn.app.callburn.Utils.TemplateDataWrapper;

/**
 * Created by Bloom on 16/1/2016.
 */
public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.TemplateViewHolder> {

    List<Template> templates = Collections.emptyList();
    LayoutInflater layoutInflater;
    Context context;

    public TemplatesAdapter(Context context, List<Template> templates) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.templates = templates;
    }

    @Override
    public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = layoutInflater.inflate(R.layout.test_item, parent, false);
        TemplateViewHolder holder = new TemplateViewHolder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(final TemplateViewHolder holder, final int position) {
        final Template template = templates.get(position);
        holder.name.setText(template.getName());
//        holder.status.setText("status");
//        holder.date.setText("status");
//        holder.message.setText("status");
//        holder.image.setImageResource(R.drawable.oval);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Intent i = new Intent(context, SendMessages.class);
//                i.putExtra("GROUPNAME", groups.get(position).getName());
//                context.startActivity(i);
                holder.overlay.setVisibility(View.VISIBLE);

                Runnable mRunnable;
                Handler mHandler = new Handler();

                mRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        holder.overlay.setVisibility(View.GONE);

                    }
                };

                mHandler.postDelayed(mRunnable, 5 * 1000);

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

                mHandler.postDelayed(mRunnable, 5 * 1000);

                return true;

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                templates.remove(position);
                notifyDataSetChanged();
                holder.delete.setVisibility(View.GONE);
                holder.date.setVisibility(View.VISIBLE);
                holder.overlay.setVisibility(View.GONE);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, SaveTemplate.class);
                i.putExtra("REQUESTTYPE","edit");
                i.putExtra("Template", template);
                context.startActivity(i);
                Toast.makeText(context, "send", Toast.LENGTH_SHORT).show();
            }
        });

        holder.send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, GetContacts.class);
                i.putExtra("REQUESTTYPE","sendTemplate");
                i.putExtra("Template", new TemplateDataWrapper(template));
                context.startActivity(i);
                Toast.makeText(context, "send", Toast.LENGTH_SHORT).show();
            }
        });
        holder.duplicate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "duplicate", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, SaveTemplate.class);
                i.putExtra("REQUESTTYPE","duplicate");
                i.putExtra("Template", template);
                context.startActivity(i);
                Toast.makeText(context, "send", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return templates.size();
    }


    public class TemplateViewHolder extends RecyclerView.ViewHolder {

        private CircularImageView image;
        private TextView name;
        private TextView date;
        private TextView message;
        private TextView status;
        private TextView delete;
        private TextView edit;
        private TextView send;
        private TextView duplicate;
        LinearLayout overlay;

        public TemplateViewHolder(View itemView) {
            super(itemView);

//            image = (CircularImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.my_name);
            delete = (TextView) itemView.findViewById(R.id.delete);
            edit = (TextView) itemView.findViewById(R.id.edit);
            send = (TextView) itemView.findViewById(R.id.send);
            duplicate = (TextView) itemView.findViewById(R.id.duplicate);
            Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
            name.setTypeface(myFont);
            date = (TextView) itemView.findViewById(R.id.date);
            overlay = (LinearLayout) itemView.findViewById(R.id.overlay);
//            message = (TextView) itemView.findViewById(R.id.message);
//            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
