package com.ford.android.podtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUserActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.btn_next)
    Button btn_next;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        btn_next.setOnClickListener(v -> {
            User user = new User();

            if (!et_name.getText().toString().isEmpty()) {
                String etName = et_name.getText().toString();
                user.setName(etName.substring(0, 1).toUpperCase() + etName.substring(1).toLowerCase());
            } else {
                et_name.setError("Please enter a name");
                return;
            }

            dbHelper.insertUser(user);

            Intent intent = new Intent(AddUserActivity.this, UserListActivity.class);
            startActivity(intent);

            CharSequence text = "User " + user.getName() + " added.";
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
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
