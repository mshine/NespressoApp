package com.ford.android.podtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.annimon.stream.Stream;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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

        tv_total_pods.setText(String.valueOf(user.getPodCount()));
        List<PodTransaction> podTransactions = dbHelper.getUsersPodsList(user.getId());

        Stream<Date> dates = Stream.of(podTransactions).map(PodTransaction::getTransactionDate);

        tv_current_month.setText(String.valueOf(getNoOfPodsForCurrentMonth(dates)));

        dates = Stream.of(podTransactions).map(PodTransaction::getTransactionDate);
        tv_last_month.setText(String.valueOf(getNoOfPodsForLastMonth(dates)));

        btnDbStart.setOnClickListener(v -> {
            Intent dbManager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
            startActivity(dbManager);
        });

    }

    public int getNoOfPodsForCurrentMonth(Stream<Date> transactionDates) {

        Stream dateStream = transactionDates.filter(t -> t.getMonth() == Calendar.getInstance().get(Calendar.MONTH));

        return (int) dateStream.count();
    }

    public int getNoOfPodsForLastMonth(Stream<Date> transactionDates) {

        Stream dateStream = transactionDates.filter(t -> t.getMonth() == Calendar.getInstance().get(Calendar.MONTH) - 1);

        return (int) dateStream.count();
    }
}
