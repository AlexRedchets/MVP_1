package com.example.azvk.mvp_1;

import android.util.Log;

import com.example.azvk.mvp_1.Clients.PlayerClient;
import com.example.azvk.mvp_1.Fragments.RecycleViewFragment;
import com.example.azvk.mvp_1.Models.Player;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyPresenter {

    private RecycleViewFragment view;
    private Throwable error;
    private List<Player> list;

    public MyPresenter(){
        PlayerClient client = Generator.createService(PlayerClient.class);
        Observable<List<Player>> russia_players = client.player("Russia");
        russia_players
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playerData -> {
                            list = playerData;
                            publish();
                        },
                        throwable -> {
                            Log.e("Error", throwable.getMessage());
                            error = throwable;
                            publish();

                        }
                );
    }

    public void onGetView(RecycleViewFragment view){
        this.view = view;
        publish();
    }

    private void publish(){
        if (view != null){
            if (list != null){
                view.setView(list);
            }
            else if (error != null){
                //function to show info
                Log.e("Error", "ERROR");
            }

        }
    }

}
