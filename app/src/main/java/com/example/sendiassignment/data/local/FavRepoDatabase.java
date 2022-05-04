package com.example.sendiassignment.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sendiassignment.data.model.ItemModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {ItemModel.class}, version = 4)
@TypeConverters(DBTypeConverters.class)
public abstract class FavRepoDatabase extends RoomDatabase {
    public abstract FavoriteReposDAO repositoryDAO();
    private static volatile FavRepoDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static FavRepoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavRepoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavRepoDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
