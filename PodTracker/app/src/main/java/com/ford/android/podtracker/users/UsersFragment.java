package com.ford.android.podtracker.users;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ford.android.podtracker.AndroidDatabaseManager;
import com.ford.android.podtracker.R;
import com.ford.android.podtracker.addpod.AddPodActivity;
import com.ford.android.podtracker.adduser.AddUserActivity;
import com.ford.android.podtracker.data.User;
import com.ford.android.podtracker.userprofile.UserProfileActivity;
import com.ford.android.podtracker.userstats.StatsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static butterknife.ButterKnife.bind;
import static com.ford.android.podtracker.Injection.provideUsersRepository;
import static com.google.common.base.Preconditions.checkNotNull;

public class UsersFragment extends Fragment implements UsersContract.View {

    private static final int REQUEST_ADD_USER = 1;

    UsersContract.UserActionsListener mActionsListener;
//    UserItemListener mItemListener = new UserItemListener() {
//        @Override
//        public void onUserClick(User clickedUser) {
//            mActionsListener.openUserProfile(clickedUser);
//        }
//    };

    private UsersAdapter mListAdapter;

    public UsersFragment() {

    }

    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new UsersAdapter(new ArrayList<User>(0)/*, mItemListener*/);
        mActionsListener = new UsersPresenter(provideUsersRepository(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadUsers(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_ADD_USER == requestCode && Activity.RESULT_OK == resultCode) {
            if (data.hasExtra("name")) {
                CharSequence text = "User " + data.getStringExtra("name") + " added.";
                Toast toast = makeText(getContext(), text, LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_user_list);
        recyclerView.setAdapter(mListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_user);

        fab.setOnClickListener((view) -> mActionsListener.addNewUser());

        FloatingActionButton fab_db = (FloatingActionButton) getActivity().findViewById(R.id.fab_db);

        fab_db.setOnClickListener((view) -> {
            Intent dbManager = new Intent(getContext(), AndroidDatabaseManager.class);
            startActivity(dbManager);
        });

        return root;
    }

    @Override
    public void showUsers(List<User> users) {
        mListAdapter.replaceData(users);
    }

    @Override
    public void showAddUser() {
        Intent intent = new Intent(getContext(), AddUserActivity.class);
        startActivityForResult(intent, REQUEST_ADD_USER);
    }

    @Override
    public void showUserProfile(User user) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void showStats(User user) {
        Intent intent = new Intent(getContext(), StatsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void showAddPods(User user) {
        Intent intent = new Intent(getContext(), AddPodActivity.class);
        intent.putExtra("userId", user.getId());
        startActivity(intent);
    }

    public interface UserItemListener {

        void onUserClick(User clickedUser);
    }

    public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

        private List<User> mUsers;
        private UserItemListener mItemListener;

        UsersAdapter(List<User> users/*, UserItemListener itemListener*/) {
            setList(users);
            //mItemListener = itemListener;
        }

        @Override
        public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View userView = inflater.inflate(R.layout.user_list_row, parent, false);
            return new ViewHolder(userView, mItemListener);
        }

        @Override
        public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
            User selectedUser = mUsers.get(position);
            holder.name.setText(selectedUser.getName());
            holder.podCount.setText(String.valueOf(selectedUser.getPodCount()));

            holder.name.setOnClickListener(v -> mActionsListener.openUserStats(selectedUser));

            holder.ivAddPod.setOnClickListener(v -> mActionsListener.openAddPod(selectedUser));

            holder.ivStatsTemp.setOnClickListener(v -> mActionsListener.openUserProfile(selectedUser));

            holder.ivDelete.setOnClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete Confirmation");
                alert.setMessage("Are you sure you want to delete " + selectedUser.getName() + " as a user?");
                alert.setPositiveButton("Yes", (dialog, which) -> {
                    String deletedName = mActionsListener.deleteUser(selectedUser);
                    dialog.dismiss();
                    String text = "User " + deletedName + " deleted.";
                    Toast toast = makeText(getContext(), text, LENGTH_SHORT);
                    toast.show();
                });
                alert.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                alert.show();
            });
        }

        void replaceData(List<User> users) {
            setList(users);
            notifyDataSetChanged();
        }

        private void setList(List<User> users) {
            mUsers = checkNotNull(users);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        public User getItem(int position) {
            return mUsers.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */{

            @BindView(R.id.tv_name) TextView name;
            @BindView(R.id.tvPodCount) TextView podCount;
            @BindView(R.id.iv_stats_temp) ImageView ivStatsTemp;
            @BindView(R.id.iv_delete) ImageView ivDelete;
            @BindView(R.id.iv_stats) ImageView ivAddPod;

            //private UserItemListener mItemListener;

            public ViewHolder(View itemView, UserItemListener listener) {
                super(itemView);
                mItemListener = listener;
                bind(this, itemView);
                //itemView.setOnClickListener(this);
            }

//            @Override
//            public void onClick(View v) {
//                int position = getAdapterPosition();
//                User user = getItem(position);
//                mItemListener.onUserClick(user);
//            }
        }

    }
}
