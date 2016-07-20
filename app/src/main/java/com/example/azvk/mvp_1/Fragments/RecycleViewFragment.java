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
import com.example.azvk.mvp_1.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecycleViewFragment extends Fragment{

    private static final String TAG = RecycleViewFragment.class.getSimpleName();
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private Subscription subscription;
    private Integer marker = 0;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
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
        super.onViewCreated(view, savedInstanceState);
        setupView(view);

    }

    private void setupView(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter(getActivity()) ;
        recyclerView.setAdapter(adapter);
    }

    public void setView(List<Player> playerList){
        adapter.updateAdapter(playerList);
    }

    @Subscribe
    public void onEvent(EventList eventList){
        switch (eventList.getResultCode()){
            case 111:
                Log.i(TAG, "GET click received");
                marker = 1;
                onGetClicked();
                break;
            case 222:
                Log.i(TAG, "CLEAR click received");
                marker = 0;
                adapter.clearAll();
                break;
        }
    }

    private void onGetClicked(){
        PlayerClient client = Generator.createService(PlayerClient.class);
        Observable<List<Player>> russia_players = client.player("Russia");
        subscription = russia_players
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playerData -> {
                    adapter.updateAdapter(playerData);
                },
                        throwable -> Log.e("Error", throwable.getMessage()));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("marker", marker);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            marker = savedInstanceState.getInt("marker");}
        if (marker ==1){
            onGetClicked();
        }
    }
}
