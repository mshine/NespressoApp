package com.ford.android.podtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ford.android.podtracker.Activity.AddPodActivity;
import com.ford.android.podtracker.Activity.StatsActivity;
import com.ford.android.podtracker.Activity.UserProfileActivity;
import com.ford.android.podtracker.Model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mshine7 on 02/09/2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    static final int ADD_POD_REQUEST = 1;
    public final Context context;
    private final LayoutInflater inflater;
    private final Listener listener;
    private List<User> userList = new ArrayList<>();

    public ListAdapter(Context context, List<User> userList) {

        this.context = context;
        this.userList = userList;
        this.listener = (Listener) context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = inflater.inflate(R.layout.user_list_row, parent, false);
        return new ListViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        final User user = userList.get(position);

        holder.tv_podCount.setText(String.valueOf(user.getPodCount()));

        holder.iv_stats_temp.setTag(position);
        holder.iv_stats_temp.setOnClickListener(v -> {
            //TODO TEMP STATS ACTIVITY INTENT
            Intent intent = new Intent(context, StatsActivity.class);
            intent.putExtra("user", user);
            context.startActivity(intent);
        });

        holder.iv_stats.setTag(position);
        holder.iv_stats.setOnClickListener(v -> {
            //TODO changed form STATS to ADDPOD Activity
            Intent intent = new Intent(context, AddPodActivity.class);
            intent.putExtra("user", user);
            ((Activity) context).startActivityForResult(intent, ADD_POD_REQUEST);
        });

        holder.iv_delete.setTag(position);
        holder.iv_delete.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Delete Confirmation");
            alert.setMessage("Are you sure you want to delete " + user.getName() + " as a user?");
            alert.setPositiveButton("Yes", (dialog, which) -> {
                listener.deleteUser(userList.get((Integer) v.getTag()));
                dialog.dismiss();
            });
            alert.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });

            alert.show();
        });

        holder.tv_name.setText(user.getName());
        holder.tv_name.setOnClickListener(view -> {

            // TODO Changed from AddPodActivity to UserProfileActivity temporarily - until login implemented
            Intent intent = new Intent(context, UserProfileActivity.class);
            intent.putExtra("user", user);
            //((Activity) context).startActivityForResult(intent, ADD_POD_REQUEST);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tvPodCount)
        TextView tv_podCount;
        @BindView(R.id.iv_stats_temp)
        ImageView iv_stats_temp;
        @BindView(R.id.iv_stats)
        ImageView iv_stats;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;


        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
