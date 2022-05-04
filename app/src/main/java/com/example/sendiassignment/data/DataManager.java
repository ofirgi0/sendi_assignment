package com.example.sendiassignment.data;

import android.content.Context;

import com.example.sendiassignment.data.model.RepoModel;
import com.example.sendiassignment.data.remote.RemoteManager;

import java.util.Map;

import io.reactivex.Observable;

public class DataManager {
    private static DataManager mInstance = null;
    private final RemoteManager remoteManager;


    private DataManager(Context context) {
        remoteManager = RemoteManager.getInstance();
    }

    public static synchronized DataManager getInstance(Context context) {
        return mInstance == null ? mInstance = new DataManager(context) : mInstance;
    }


    public Observable<RepoModel> getRepos(Map<String, String> map) {
        return remoteManager.getRepos(map);
    }
}
