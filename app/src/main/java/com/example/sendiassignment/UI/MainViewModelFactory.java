package com.example.sendiassignment.UI;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sendiassignment.data.DataManager;
import com.example.sendiassignment.data.local.FavoriteReposDAO;
import com.example.sendiassignment.data.local.RepoRepository;

import java.lang.reflect.InvocationTargetException;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final DataManager dataManager;
    private final RepoRepository repoRepository;

    public MainViewModelFactory(DataManager dataManager, RepoRepository repoRepository) {
        this.dataManager = dataManager;
        this.repoRepository = repoRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            if (modelClass == MainViewModel.class){
                return modelClass.getConstructor(DataManager.class, RepoRepository.class).newInstance(dataManager, repoRepository);
            }
            throw new NoSuchMethodException();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
