package com.ford.android.podtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class UserListActivity extends AppCompatActivity implements Listener {

    private RecyclerView recyclerView;
    private DbHelper dbHelper;
    private ListAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = DbHelper.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.rv_userlist);
        adapter = new ListAdapter(this, dbHelper.getUsersList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        fab = (FloatingActionButton) findViewById(R.id.fab_addUser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dbHelper = DbHelper.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.rv_userlist);
        adapter = new ListAdapter(this, dbHelper.getUsersList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void nameToChange(UserData user) {
        int id = user.id;
        dbHelper.deleteUserRow(id);

        adapter = new ListAdapter(this, dbHelper.getUsersList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CharSequence text = "User " + user.name + " removed.";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
