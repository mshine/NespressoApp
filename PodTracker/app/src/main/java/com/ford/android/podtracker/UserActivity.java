package com.ford.android.podtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    private EditText et_name;
    private Button btn_next;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        et_name = (EditText) findViewById(R.id.et_name);
        btn_next = (Button) findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = new UserData();

                if (!et_name.getText().toString().isEmpty()) {
                    String etName = et_name.getText().toString();
                    userData.name = etName.substring(0, 1).toUpperCase() + etName.substring(1).toLowerCase();
                } else {
                    et_name.setError("Please enter a name");
                    return;
                }

                dbHelper.insertUser(userData);

                Intent intent = new Intent(UserActivity.this, UserListActivity.class);
                startActivity(intent);

                CharSequence text = "User " + userData.name + " added.";
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
        finish();
    }
}
