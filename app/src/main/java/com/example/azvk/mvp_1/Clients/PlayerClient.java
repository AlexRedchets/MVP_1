package com.example.azvk.mvp_1.Clients;

import com.example.azvk.mvp_1.Models.Player;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface PlayerClient {
    @GET("api/player/{team}")
    Observable<List<Player>> player(
            @Path("team") String team
    );
}

