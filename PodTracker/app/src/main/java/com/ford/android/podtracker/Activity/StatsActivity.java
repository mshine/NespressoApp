package com.ford.android.podtracker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.ford.android.podtracker.AndroidDatabaseManager;
import com.ford.android.podtracker.DbHelper;
import com.ford.android.podtracker.Model.PodTransaction;
import com.ford.android.podtracker.Model.User;
import com.ford.android.podtracker.R;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;


public class StatsActivity extends AppCompatActivity {

    DbHelper dbHelper;

    @BindView(R.id.tv_total_pods)
    TextView tv_total_pods;
    @BindView(R.id.tv_current_month)
    TextView tv_current_month;
    @BindView(R.id.tv_last_month)
    TextView tv_last_month;
    @BindView(R.id.dbStart)
    Button btnDbStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        User user = (User) getIntent().getSerializableExtra("user");

        tv_total_pods.setText(valueOf(user.getPodCount()));
        List<PodTransaction> podTransactions = dbHelper.getUsersPodsList(user.getId());
        PodTransaction podTransaction = new PodTransaction();

        Stream<Date> dates = Stream.of(podTransactions).map(PodTransaction::getTransactionDate);
        tv_current_month.setText(valueOf(podTransaction.getNoOfPodsForCurrentMonth(dates)));

        dates = Stream.of(podTransactions).map(PodTransaction::getTransactionDate);
        tv_last_month.setText(valueOf(podTransaction.getNoOfPodsForLastMonth(dates)));

        btnDbStart.setOnClickListener(v -> {
            Intent dbManager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
            startActivity(dbManager);
        });

    }
}
