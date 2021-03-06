package com.ford.android.podtracker.userprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.Months;
import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.User;

import java.util.Date;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.ford.android.podtracker.data.PodTransaction.getNoOfPodsForGivenMonth;
import static inj.Injection.provideUsersRepository;
import static java.lang.String.valueOf;

public class UserStatsFragment extends Fragment implements UserProfileContract.View {

    @BindViews({ R.id.tv_pods_0, R.id.tv_pods_1, R.id.tv_pods_2, R.id.tv_pods_3,R.id.tv_pods_4, R.id.tv_pods_5, R.id.tv_pods_6, R.id.tv_pods_7,R.id.tv_pods_8, R.id.tv_pods_9,R.id.tv_pods_10, R.id.tv_pods_11 })
    TextView[] tv_pods;


    public UserStatsFragment() {
        // Required empty public constructor
    }

    public static UserStatsFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        UserStatsFragment fragment = new UserStatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserProfileContract.UserActionsListener mActionListener = new UserProfilePresenter(provideUsersRepository(), this);
        User user = (User) getArguments().getSerializable("user");
        mActionListener.loadStats(user.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_stats, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void showStats(List<PodTransaction> podTransactions) {
        Stream<Date> dates;
        for (int i = 0; i < Months.values().length; i++) {
            dates = Stream.of(podTransactions).map(PodTransaction::getTransactionDate);
            tv_pods[i].setText(valueOf(getNoOfPodsForGivenMonth(dates, Months.values()[i].value)));
        }
    }

    @Override
    public void showDetails(int userId) {

    }
}
