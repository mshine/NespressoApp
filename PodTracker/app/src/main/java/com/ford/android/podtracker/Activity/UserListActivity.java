package com.ford.android.podtracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ford.android.podtracker.DbHelper;
import com.ford.android.podtracker.ListAdapter;
import com.ford.android.podtracker.Listener;
import com.ford.android.podtracker.Model.User;
import com.ford.android.podtracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListActivity extends AppCompatActivity implements Listener {

    static final int ADD_POD_REQUEST = 1;
    private static final int ADD_USER_REQUEST = 2;
    @BindView(R.id.fab_add_user)
    FloatingActionButton fabAddUser;
    private RecyclerView recyclerView;
    private DbHelper dbHelper;
    private ListAdapter adapter;

    //notifyDataSetChanged()???
    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = DbHelper.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.rv_user_list);
        adapter = new ListAdapter(this, dbHelper.getUsersList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADD_POD_REQUEST) {
            if (data.hasExtra("user") && data.hasExtra("podCount")) {
                User user = (User) data.getSerializableExtra("user");
                CharSequence text = user.getName() + "'s pod count is now: " + data.getExtras().getInt("podCount");
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (resultCode == RESULT_OK && requestCode == ADD_USER_REQUEST) {
            if (data.hasExtra("user")) {
                User user = (User) data.getSerializableExtra("user");
                CharSequence text = "User " + user.getName() + " added.";
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);

        fabAddUser.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivityForResult(intent, ADD_USER_REQUEST);
        });
    }

    @Override
    public void deleteUser(User user) {

        dbHelper.deleteUserRow(user.getId());

        adapter = new ListAdapter(this, dbHelper.getUsersList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CharSequence text = "User " + user.getName() + " removed.";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
