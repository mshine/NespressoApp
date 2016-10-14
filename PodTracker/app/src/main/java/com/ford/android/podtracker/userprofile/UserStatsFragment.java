package com.ford.android.podtracker.userprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.User;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ford.android.podtracker.Injection.provideUsersRepository;
import static com.ford.android.podtracker.data.PodTransaction.getNoOfPodsForCurrentMonth;
import static com.ford.android.podtracker.data.PodTransaction.getNoOfPodsForLastMonth;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.valueOf;

public class UserStatsFragment extends Fragment implements UserProfileContract.View {

    private User mUser;

    @BindView(R.id.tv_user_total_pods)
    TextView tv_user_total_pods;
    @BindView(R.id.tv_user_last_month)
    TextView tv_user_last_month;
    @BindView(R.id.tv_user_current_month)
    TextView tv_user_current_month;

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
        mUser = (User) getArguments().getSerializable("user");
        mActionListener.loadStats(mUser.getId());
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
        List<PodTransaction> mPodTransactions = checkNotNull(podTransactions);
        tv_user_total_pods.setText(valueOf(mUser.getPodCount()));

        Stream<Date> dates = Stream.of(mPodTransactions).map(PodTransaction::getTransactionDate);
        tv_user_last_month.setText(valueOf(getNoOfPodsForLastMonth(dates)));

        dates = Stream.of(mPodTransactions).map(PodTransaction::getTransactionDate);
        tv_user_current_month.setText(valueOf(getNoOfPodsForCurrentMonth(dates)));
    }

    @Override
    public void showDetails(int userId) {

    }
}
