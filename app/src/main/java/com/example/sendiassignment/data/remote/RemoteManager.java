package com.example.sendiassignment.data.remote;

import com.example.sendiassignment.data.model.RepoModel;
import com.example.sendiassignment.data.remote.rest.RestApi;
import com.example.sendiassignment.data.remote.rest.RestClient;

import java.util.Map;


import io.reactivex.Observable;

public class RemoteManager {
    private static RemoteManager mInstance = null;

    private RemoteManager(){}

    public static RemoteManager getInstance(){
        return mInstance == null ? mInstance= new RemoteManager() : mInstance;
    }

    public Observable<RepoModel> getRepos(Map<String, String> map){
        RestApi api = RestClient.getApiService();
        return api.getRepos(map);
    }
}
