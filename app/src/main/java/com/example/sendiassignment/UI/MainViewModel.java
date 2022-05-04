package com.example.sendiassignment.UI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sendiassignment.data.DataManager;
import com.example.sendiassignment.data.local.FavoriteReposDAO;
import com.example.sendiassignment.data.local.RepoRepository;
import com.example.sendiassignment.data.model.ItemModel;
import com.example.sendiassignment.data.model.RepoModel;
import com.example.sendiassignment.utils.Constants;
import com.example.sendiassignment.utils.CreatedTime;
import com.example.sendiassignment.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private final RepoRepository repoRepository;
    DataManager dataManager;
    private final MutableLiveData<Boolean> error = new MutableLiveData<>();
    private final Map<String, String> queryMap = new HashMap<>();
    private final MutableLiveData<List<ItemModel>> repos = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private int currentPage;
//    private List<String> favoritesIds = new ArrayList<>();

    public MainViewModel(DataManager dataManager, RepoRepository repoRepository) {
        this.dataManager = dataManager;
        this.repoRepository = repoRepository;
    }

    public MutableLiveData<List<ItemModel>> getRepos() {
        return repos;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public void loadNextPageRepos(CreatedTime time, boolean firstPage) {
        if (firstPage) currentPage = 0;
        if (!isLastPage()) {
            initQueryMap(time);

            disposable.add(
                    dataManager.getRepos(queryMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(RepoModel::getItems)
                            .subscribe(itemModels -> {
                                if (itemModels != null && !itemModels.isEmpty()) {
                                    repos.postValue(itemModels);
                                    error.postValue(false);
                                } else
                                    error.postValue(true);
                            }, error -> this.error.postValue(true))
            );
        }
    }


    public void addFavorite(ItemModel model){
         repoRepository.insert(model);
    }

    public void removeFavorite(ItemModel model){
        repoRepository.delete(model);
    }

    public LiveData<List<ItemModel>> getAllFavorites() {
        return repoRepository.getAllFavRepos();
    }

    private void initQueryMap(CreatedTime time) {
        queryMap.put("q", "created:>" + Util.getDateFormat(time));
        queryMap.put("sort", "stars");
        queryMap.put("order", "desc");
        queryMap.put("page", String.valueOf(++currentPage));
    }

    public void onClear() {
        disposable.dispose();
    }

    public boolean isLastPage() {
        return currentPage >= Constants.MAX_PAGE_COUNT;
    }
}
