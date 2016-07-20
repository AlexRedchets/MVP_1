package com.example.azvk.mvp_1.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azvk.mvp_1.Adapters.RecycleViewAdapter;
import com.example.azvk.mvp_1.Clients.PlayerClient;
import com.example.azvk.mvp_1.EventList;
import com.example.azvk.mvp_1.Generator;
import com.example.azvk.mvp_1.Models.Player;
import com.example.azvk.mvp_1.MyPresenter;
import com.example.azvk.mvp_1.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import rx.Subscription;

public class RecycleViewFragment extends Fragment{

    private static final String TAG = RecycleViewFragment.class.getSimpleName();
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;

    private static MyPresenter presenter;

    private Subscription subscription;
    private Integer marker = 0;

    @Override
    public void onStart() {
        Log.i(TAG, "onStart called");
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop called");
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycle_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated called");
        super.onViewCreated(view, savedInstanceState);
        setupView(view);

        if (marker == 1 || presenter == null){
            presenter = new MyPresenter();
            presenter.onGetView(this);
        }

    }

    private void setupView(View view) {
        Log.i(TAG, "setupView called");
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter(getActivity()) ;
        recyclerView.setAdapter(adapter);

    }

    public void setView(List<Player> playerList){
        Log.i(TAG, "setView called");
        adapter.updateAdapter(playerList);
    }

    @Subscribe
    public void onEvent(EventList eventList){
        switch (eventList.getResultCode()){
            case 111:
                Log.i(TAG, "GET click received");
                marker = 1;

                if (presenter == null){
                    presenter = new MyPresenter();
                }
                presenter.onGetView(this);

                break;
            case 222:
                Log.i(TAG, "CLEAR click received");
                marker = 0;
                adapter.clearAll();
                break;
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy called");
        super.onDestroy();
        presenter.onGetView(null);
        if (!getActivity().isChangingConfigurations()){
            presenter = null;
        }
    }
}
