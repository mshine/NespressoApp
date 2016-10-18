package com.ford.android.podtracker.userprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.User;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static inj.Injection.provideUsersRepository;
import static java.lang.String.valueOf;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends Fragment implements UserProfileContract.View {

    private User mUser;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_pod_count)
    TextView tvPodCount;
    @BindView(R.id.tv_total_owed_2)
    TextView tvTotalOwed2;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        UserDetailFragment fragment = new UserDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserProfileContract.UserActionsListener mActionListener = new UserProfilePresenter(provideUsersRepository(), this);
        mUser = (User) getArguments().getSerializable("user");
        mActionListener.loadDetails(mUser.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void showStats(List<PodTransaction> podTransactions) {

    }

    @Override
    public void showDetails(int userId) {
        tvUserName.setText(mUser.getName());
        tvPodCount.setText(valueOf(mUser.getPodCount()));
        DecimalFormat df = new DecimalFormat("'£'0.00");
        tvTotalOwed2.setText(valueOf(df.format(mUser.getTotalOwed())));
    }
}
