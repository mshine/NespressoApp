package com.ford.android.podtracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.ford.android.podtracker.DbHelper;
import com.ford.android.podtracker.R;
import com.ford.android.podtracker.Model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUserActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.btn_next)
    Button btn_next;

    private DbHelper dbHelper;
    private User user;
    private boolean userAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ButterKnife.bind(this);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        btn_next.setOnClickListener(v -> {
            user = new User();

            if (!et_name.getText().toString().isEmpty()) {
                String etName = et_name.getText().toString();
                user.setName(etName.substring(0, 1).toUpperCase() + etName.substring(1).toLowerCase());
            } else {
                et_name.setError("Please enter a name");
                return;
            }

            dbHelper.insertUser(user);
            userAdded = true;
            finish();
        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        if (userAdded) {
            intent.putExtra("user", user);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }
        super.finish();
    }
}
