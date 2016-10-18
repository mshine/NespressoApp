package com.ford.android.podtracker.addpod;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ford.android.podtracker.R;
import com.ford.android.podtracker.data.PodTransaction;
import com.ford.android.podtracker.data.PodType;
import com.ford.android.podtracker.data.User;

import java.text.DecimalFormat;

import butterknife.BindView;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static butterknife.ButterKnife.bind;
import static inj.Injection.provideUsersRepository;
import static java.lang.String.valueOf;
import static java.util.Calendar.getInstance;


public class AddPodFragment extends Fragment implements AddPodContract.View {

    @BindView(R.id.tv_add_pod_count)
    TextView tv_add_pod_count;
    @BindView(R.id.btn_add_pod_caramelito)
    ImageView btn_caramelito;
    @BindView(R.id.btn_add_pod_kazaar)
    ImageView btn_kazaar;

    private AddPodContract.UserActionsListener mActionListener;

    private int podCount;
    private User user;

    public AddPodFragment() {
    }

    public static AddPodFragment newInstance(int userId) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("userId", userId);
        AddPodFragment fragment = new AddPodFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionListener = new AddPodPresenter(provideUsersRepository(), this);

        int userId = getArguments().getInt("userId");
        user = mActionListener.loadUser(userId);
        podCount = user.getPodCount();
        tv_add_pod_count.setText(valueOf(podCount));

        btn_caramelito.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(AddPodFragment.this.getContext());
            alert.setTitle("Add Pod Confirmation");
            String podName = btn_caramelito.getContentDescription().toString();
            showAlertDialog(userId, alert, podName);
        });

        btn_kazaar.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(AddPodFragment.this.getContext());
            alert.setTitle("Add Pod Confirmation");
            String podName = btn_kazaar.getContentDescription().toString();
            showAlertDialog(userId, alert, podName);
        });
    }

    private void showAlertDialog(int userId, AlertDialog.Builder alert, String podName) {
        alert.setMessage("Are you sure you want to consume a " + podName + " pod, " + user.getName() + "?");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            consumePod(userId, podName);
            dialog.dismiss();
        });
        alert.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    private void consumePod(int userId, String consumedPodName) {
        user = mActionListener.loadUser(userId);
        podCount = user.getPodCount();

        incrementPodCount();
        PodType podType = addPodPriceToTotal(consumedPodName);
        createPodTransaction(podType);
        showToast(user.getName(), consumedPodName, podType.getPrice());
    }

    private void showToast(String name, String consumedPodName, double price) {
        DecimalFormat df = new DecimalFormat("'£'0.00");
        makeText(this.getContext(), "Enjoy your " + consumedPodName + " " + name + "! " + df.format(price) + " has been added to your total", LENGTH_LONG).show();
    }

    private void incrementPodCount() {
        podCount++;
        tv_add_pod_count.setText(valueOf(podCount));
        mActionListener.addPod(user.getId(), podCount);
    }

    @NonNull
    private PodType addPodPriceToTotal(String consumedPodName) {
        PodType podType = mActionListener.getPodType(consumedPodName);

        double currentTotal = user.getTotalOwed();
        double podPrice = podType.getPrice();
        double newPrice = currentTotal + podPrice;

        mActionListener.addToTotal(user.getId(), newPrice);
        return podType;
    }

    private void createPodTransaction(PodType podType) {
        PodTransaction podTransaction = new PodTransaction();

        podTransaction.setUserId(user.getId());
        podTransaction.setUserPodTypeId(podType.getId());
        podTransaction.setTransactionDate(getInstance().getTime());
        mActionListener.insertUserPods(podTransaction);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_pod, container, false);
        bind(this, root);
        setRetainInstance(true);
        return root;
    }
}
