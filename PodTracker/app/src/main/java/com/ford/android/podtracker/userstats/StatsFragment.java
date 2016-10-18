package com.ford.android.podtracker.userstats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.User;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import static com.ford.android.podtracker.data.PodTransaction.getNoOfPodsForCurrentMonth;
import static com.ford.android.podtracker.data.PodTransaction.getNoOfPodsForLastMonth;
import static com.google.common.base.Preconditions.checkNotNull;
import static inj.Injection.provideUsersRepository;
import static java.lang.String.valueOf;

public class StatsFragment extends Fragment implements StatsContract.View {

    private User mUser;
    private TextView tv_total_pods;
    private TextView tv_total_owed;
    private TextView tv_current_month;
    private TextView tv_last_month;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance(User user) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("user", user);
        StatsFragment statsFragment = new StatsFragment();
        statsFragment.setArguments(arguments);
        return statsFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StatsContract.UserActionsListener mActionListener = new StatsPresenter(provideUsersRepository(), this);
        mUser = (User) getArguments().getSerializable("user");
        mActionListener.loadStats(mUser.getId());

        //List<PodTransaction> podTransactions = dbHelper.getUsersPodsList(user.getId());

        //podtransactionslist
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        tv_total_pods = (TextView) root.findViewById(R.id.tv_total_pods);
        tv_total_owed = (TextView) root.findViewById(R.id.tv_total_owed);
        tv_current_month = (TextView) root.findViewById(R.id.tv_current_month);
        tv_last_month = (TextView) root.findViewById(R.id.tv_last_month);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void showStats(List<PodTransaction> podTransactions) {
        List<PodTransaction> mPodTransactions = checkNotNull(podTransactions);
        tv_total_pods.setText(valueOf(mUser.getPodCount()));

        DecimalFormat df = new DecimalFormat("'£'0.00");
        tv_total_owed.setText(valueOf(df.format(mUser.getTotalOwed())));

        Stream<Date> dates = Stream.of(mPodTransactions).map(PodTransaction::getTransactionDate);
        tv_last_month.setText(valueOf(getNoOfPodsForLastMonth(dates)));

        dates = Stream.of(mPodTransactions).map(PodTransaction::getTransactionDate);
        tv_current_month.setText(valueOf(getNoOfPodsForCurrentMonth(dates)));

    }
}
