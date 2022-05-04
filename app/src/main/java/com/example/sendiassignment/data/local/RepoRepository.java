package com.example.sendiassignment.data.local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sendiassignment.data.model.ItemModel;

import java.util.List;

import io.reactivex.Observable;

public class RepoRepository {
    private final FavoriteReposDAO dao;
    private final LiveData<List<ItemModel>> allFavRepos;
    public RepoRepository(Application application) {
        FavRepoDatabase db = FavRepoDatabase.getDatabase(application);
        dao = db.repositoryDAO();
        allFavRepos = dao.getAllFavRepos();
    }
    public LiveData<List<ItemModel>> getAllFavRepos(){
        return allFavRepos;
    }
    public void insert(ItemModel item){
        FavRepoDatabase.databaseWriteExecutor.execute(()->{
            dao.insertRepo(item);
        });
    }

    public void delete(ItemModel item){
        FavRepoDatabase.databaseWriteExecutor.execute(()->{
            dao.deleteRepo(item);
        });
    }
}
