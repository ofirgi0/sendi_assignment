package com.example.sendiassignment.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sendiassignment.data.model.ItemModel;

import java.util.List;

@Dao
public interface FavoriteReposDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepo (ItemModel item);

    @Delete
    void deleteRepo(ItemModel item);

    @Query("SELECT * FROM 'fav_repo_db'")
    LiveData<List<ItemModel>> getAllFavRepos();
}
