package com.ford.android.podtracker.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ford.android.podtracker.Model.User;
import com.ford.android.podtracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_PARAM = "user";

    // TODO: Rename and change types of parameters
    private User user;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_pod_count)
    TextView tvPodCount;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment UserDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDetailFragment newInstance(User user) {
        UserDetailFragment fragment = new UserDetailFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.getSerializable("user");
            assert user != null;
            tvUserName.setText(user.getName());
            tvPodCount.setText(valueOf(user.getPodCount()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
