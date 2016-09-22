package com.ford.android.podtracker.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.ford.android.podtracker.DbHelper;
import com.ford.android.podtracker.Model.PodTransaction;
import com.ford.android.podtracker.Model.User;
import com.ford.android.podtracker.R;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserStatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserStatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_PARAM = "user";

    // TODO: Rename and change types of parameters
    private User user;

    @BindView(R.id.tv_user_total_pods)
    TextView tvUserTotalPods;
    @BindView(R.id.tv_user_last_month)
    TextView tvUserLastMonth;
    @BindView(R.id.tv_user_current_month)
    TextView tvUserCurrentMonth;

    public UserStatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment UserStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserStatsFragment newInstance(User user) {
        UserStatsFragment fragment = new UserStatsFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_PARAM, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
            assert user != null;
            tvUserTotalPods.setText(valueOf(user.getPodCount()));
        }

        DbHelper dbHelper = DbHelper.getInstance(getActivity().getApplicationContext());
        List<PodTransaction> podTransactions = dbHelper.getUsersPodsList(user.getId());
        PodTransaction podTransaction = new PodTransaction();

        Stream<Date> dates = Stream.of(podTransactions).map(PodTransaction::getTransactionDate);
        tvUserLastMonth.setText(valueOf(podTransaction.getNoOfPodsForLastMonth(dates)));

        dates = Stream.of(podTransactions).map(PodTransaction::getTransactionDate);
        tvUserCurrentMonth.setText(valueOf(podTransaction.getNoOfPodsForCurrentMonth(dates)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_stats, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
