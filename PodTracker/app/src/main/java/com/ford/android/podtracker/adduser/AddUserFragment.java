package com.ford.android.podtracker.adduser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ford.android.podtracker.R;

import static inj.Injection.provideUsersRepository;

public class AddUserFragment extends Fragment implements AddUserContract.View {

    private AddUserContract.UserActionsListener mActionListener;
    private Button btnAddUser;
    private EditText etName;

    public AddUserFragment() {
        // Required empty public constructor
    }

    public static AddUserFragment newInstance() {
        return new AddUserFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new AddUserPresenter(provideUsersRepository(), this);
        btnAddUser.setOnClickListener((view) -> mActionListener.saveUser(etName.getText().toString()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_user, container, false);
        btnAddUser = (Button) root.findViewById(R.id.btn_next);
        etName = (EditText) root.findViewById(R.id.et_name);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void showUsersList(String name) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
