package com.ford.android.podtracker.addpod;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ford.android.podtracker.R;

public class AddPodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pod);

        int userId = getIntent().getIntExtra("userId", 0);

        initFragment(AddPodFragment.newInstance(userId));

//        dbHelper = DbHelper.getInstance(getApplicationContext());
//
//        user = (User) getIntent().getSerializableExtra("user");

//        int podCount[] = {dbHelper.getUser(user.getId()).getPodCount()};
//        tvPodCount.setText(valueOf(podCount[0]));
//
//        btnAddPod.setOnClickListener(v -> {
//            AlertDialog.Builder alert = new AlertDialog.Builder(AddPodActivity.this);
//            alert.setTitle("Add Pod Confirmation");
//            alert.setMessage("Are you sure you want to increment the pod count for " + user.getName() + "?");
//            alert.setPositiveButton("Yes", (dialog, which) -> {
//                podIncremented = true;
//                podCount[0]++;
//                tvPodCount.setText(valueOf(podCount[0]));
//
//                dbHelper.updatePodCount(user.getId(), podCount[0]);
//
//                PodTransaction podTransaction = new PodTransaction();
//
//                podTransaction.setUserId(user.getId());
//                podTransaction.setTransactionDate(getInstance().getTime());
//
//                dbHelper.insertUserPods(podTransaction);
//                dialog.dismiss();
//            });
//            alert.setNegativeButton("No", (dialog, which) -> {
//                podIncremented = false;
//                dialog.dismiss();
//            });
//            alert.show();
//        });

    }

    private void initFragment(Fragment addPodFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_placeholder_add_pod, addPodFragment);
        transaction.commit();
    }

//    @Override
//    public void finish() {
//        Intent intent = new Intent();
//        if (podIncremented) {
//            intent.putExtra("user", user);
//            intent.putExtra("podCount", user.getPodCount() + 1);
//            setResult(RESULT_OK, intent);
//        } else {
//            setResult(RESULT_CANCELED);
//        }
//        super.finish();
//    }
}