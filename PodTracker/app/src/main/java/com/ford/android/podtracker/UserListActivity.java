package com.ford.android.podtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListActivity extends AppCompatActivity implements Listener {

    private RecyclerView recyclerView;
    private DbHelper dbHelper;
    private ListAdapter adapter;

    @BindView(R.id.fab_add_user) FloatingActionButton fabAddUser;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);

        fabAddUser.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
            startActivity(intent);
            finish();
        });

        dbHelper = DbHelper.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.rv_user_list);
        adapter = new ListAdapter(this, dbHelper.getUsersList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
