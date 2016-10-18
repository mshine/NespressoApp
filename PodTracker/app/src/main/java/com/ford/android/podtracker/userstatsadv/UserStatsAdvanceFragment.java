package com.ford.android.podtracker.userstatsadv;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.Times;
import com.ford.android.podtracker.data.User;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.ford.android.podtracker.data.PodTransaction.getHourOfPodTransactionForGivenMonth;
import static inj.Injection.provideUsersRepository;
import static java.lang.String.valueOf;

public class UserStatsAdvanceFragment extends Fragment implements UserStatsAdvanceContract.View {

    @BindViews({ R.id.tv_hour_0, R.id.tv_hour_1, R.id.tv_hour_2, R.id.tv_hour_3,R.id.tv_hour_4, R.id.tv_hour_5, R.id.tv_hour_6, R.id.tv_hour_7,R.id.tv_hour_8, R.id.tv_hour_9,R.id.tv_hour_10, R.id.tv_hour_11 })
    TextView[] tv_hours;


    public UserStatsAdvanceFragment() {
        // Required empty public constructor
    }

    public static UserStatsAdvanceFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        UserStatsAdvanceFragment fragment = new UserStatsAdvanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserStatsAdvanceContract.UserActionsListener mActionListener = new UserStatsAdvancePresenter(provideUsersRepository(), this);
        User user = (User) getArguments().getSerializable("user");
        mActionListener.loadStats(user.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_stats_advance, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void showStats(List<PodTransaction> podTransactions) {

        for (int i = 0; i < Times.values().length; i++) {
            tv_hours[i].setText(valueOf(getHourOfPodTransactionForGivenMonth(podTransactions, Times.values()[i].value)));
        }
    }
}
